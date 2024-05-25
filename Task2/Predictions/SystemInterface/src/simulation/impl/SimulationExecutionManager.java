package simulation.impl;

import DTO.definition.*;
import DTO.definition.actionDTO.ActionDTO;
import DTO.definition.actionDTO.types.calculation.CalculationDTO;
import DTO.definition.actionDTO.types.condition.MultipleConditionDTO;
import DTO.definition.actionDTO.types.condition.SingleConditionDTO;
import DTO.definition.actionDTO.types.others.ProximityDTO;
import DTO.definition.actionDTO.types.others.ReplaceDTO;
import DTO.definition.actionDTO.types.singleExpression.SingleExpressionDTO;
import DTO.simulated.*;
import definition.entity.EntityDef;
import definition.property.api.PropertyDef;
import definition.rule.Rule;
import definition.rule.action.api.Action;
import definition.rule.action.impl.condition.ConditionAction;
import definition.rule.action.impl.condition.singularity.AbstractCondition;
import definition.rule.action.impl.condition.singularity.MultipleCondition;
import definition.rule.action.impl.condition.singularity.SingleCondition;
import definition.rule.action.impl.others.ReplaceAction;
import definition.rule.action.impl.withExpression.multiExpression.calculation.CalculationAction;
import definition.rule.action.impl.withExpression.singleExpression.SingleExpression;
import definition.rule.action.impl.withExpression.singleExpression.impl.ProximityAction;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.entity.manager.impl.EntityInstanceManagerImpl;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.api.PropertyInstance;
import execution.instance.property.impl.PropertyInstanceImpl;
import generated.PRDWorld;
import simulation.api.SimulationExecutionManagerDef;
import world.World;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SimulationExecutionManager implements SimulationExecutionManagerDef {
    private int simulationsCounter;
    private List<SimulationExecutionDetails> simulationExecutionDetailsList;
    private SimulationsDTO simulationsDTO;
    private World world;
    private boolean loaded = false;
    private ExecutorService threadExecutor;

    public void setNewSimulationExecutionDetailsList() {
        simulationExecutionDetailsList = new ArrayList<>();
        simulationsDTO = new SimulationsDTO();
        simulationsCounter = 0;
    }

    public void createNewSimulation(Map<String, Integer> newSimulationPopulations, Map<String, Object> environmentsValuesInput) {
        ActiveEnvironment activeEnvironment = createActiveEnvironmentValues(environmentsValuesInput);
        Map<String, EntityInstanceManager> entityInstanceManagerMap = createInstances(newSimulationPopulations);
        SimulationExecutionDetails newSimulationExecutionDetails = new SimulationExecutionDetails(entityInstanceManagerMap, activeEnvironment, world.getRules(), world.getTermination(), world.getRows(), world.getColumns(), ++simulationsCounter);
        simulationExecutionDetailsList.add(newSimulationExecutionDetails);
        simulationsDTO.addNewExecutionToList(getSimulationDetailsDTO(newSimulationExecutionDetails.getId()));
    }

    @Override
    public void loadSystemDetails(String path) {
        if (!isXML(path))
            throw new IllegalArgumentException("The file you were trying to load is not XML. It must be XML!\n");

        File file = new File(path);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PRDWorld.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PRDWorld prdWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
            World newWorld = new World();
            newWorld.transferDataFromXMLToEngine(prdWorld);
            loaded = true;
            world = newWorld;
            threadExecutor = Executors.newFixedThreadPool(prdWorld.getPRDThreadCount());

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isXML(String filePath) {
        Path path = Paths.get(filePath);
        String fileExtension = getFileExtension(path.getFileName().toString());
        return "xml".equalsIgnoreCase(fileExtension);
    }

    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    @Override
    public Map<String, EntityInstanceManager> createInstances(Map<String, Integer> newSimulationPopulations) {
        Map<String, EntityInstanceManager> res = new HashMap<>();

        for (Map.Entry<String, EntityDef> entityEntry : world.getEntities().entrySet()) {
            EntityInstanceManager entityInstanceManagerToAdd = new EntityInstanceManagerImpl(newSimulationPopulations.get(entityEntry.getKey()), entityEntry.getValue().getProps().keySet());
            for (int i = 0; i < entityEntry.getValue().getPopulation(); i++) {
                EntityInstance entityInstanceToAdd = entityInstanceManagerToAdd.createInstance(entityEntry.getValue());
                entityInstanceManagerToAdd.addInstance(entityInstanceToAdd);
            }
            res.put(entityEntry.getKey(), entityInstanceManagerToAdd);
        }
        return res;
    }

    @Override
    public void runSimulation(SimulationDetailsDTO simulationDetailsDTO) {
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsList.get(simulationDetailsDTO.getId() - 1);
        SimulationRunner simulationRunner = new SimulationRunner(simulationExecutionDetails);
        threadExecutor.execute(simulationRunner);
    }

    @Override
    public void setEntityPopulation(String entityName, int populationInput) {
        world.getEntities().get(entityName).setPopulation(populationInput);
    }

    @Override
    public void clearPopulations() {
        for (EntityDef entity : world.getEntities().values())
            entity.setPopulation(0);
    }

    @Override
    public ActiveEnvironment createActiveEnvironmentValues(Map<String, Object> envValueInputs) {
        ActiveEnvironment res = world.getEnvironments().createActiveEnvironment();
        Map<String, PropertyDef> worldEnvironments = world.getEnvironments().getProps();
        PropertyInstanceImpl propertyInstanceToAdd;
        Object value;

        for (Map.Entry<String, Object> entry : envValueInputs.entrySet()) {
            if (entry.getValue() != null)
                value = entry.getValue();
            else
                value = worldEnvironments.get(entry.getKey()).createValue();
            propertyInstanceToAdd = new PropertyInstanceImpl(worldEnvironments.get(entry.getKey()), value);
            res.addPropertyInstance(propertyInstanceToAdd);
        }
        return res;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    public int[] countSimulations() {
        int[] res = {0, 0, 0};  // [inQueue, running, finished]
        for(SimulationExecutionDetails simulationExecutionDetails: simulationExecutionDetailsList) {
            String currSimulationStatus = simulationExecutionDetails.getStatus();
            if (currSimulationStatus.equalsIgnoreCase(StatusEnum.IN_QUEUE.getStatusString()))
                res[0]++;
            else if (currSimulationStatus.equalsIgnoreCase(StatusEnum.FINISHED.getStatusString()))
                res[2]++;
            else    // if simulation is paused or running
                res[1]++;
        }
        return res;
    }

    public void setRerunPopulation(SimulatedEntityDTO simulatedEntityDTO) {
        world.getEntities().get(simulatedEntityDTO.getEntityName()).setPopulation(simulatedEntityDTO.getInitPopulation());
    }

    public void pauseSimulation(int simulationID){
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsList.get(simulationID - 1);
        simulationExecutionDetails.pauseThread();
    }
    public void resumeSimulation(int simulationID){
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsList.get(simulationID - 1);
        simulationExecutionDetails.setStatus(StatusEnum.RUNNING.getStatusString());
        simulationExecutionDetails.resumeThread();
    }
    public void stopSimulation(int simulationID){
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsList.get(simulationID - 1);
        simulationExecutionDetails.stopThread();
    }


    // ----------------------------------DTO-----------------------------------------------
    @Override
    public WorldDTO getWorldDTO() {

        Map<String, EntityDTO> entitiesDTO = getEntityDTO();
        Map<String, RuleDTO> rulesDTO = getRulesDTO();
        Map<String, EnvironmentDTO> environmentsDTO = getEnvironmentsDTO();
        TerminationDTO terminationDTO = new TerminationDTO(world.getTermination().getTicks(), world.getTermination().getSeconds());
        GridDTO gridDTO = new GridDTO(Integer.toString(world.getRows()), Integer.toString(world.getColumns()));
        return new WorldDTO(entitiesDTO, rulesDTO, environmentsDTO, terminationDTO, gridDTO);
    }

    @Override
    public Map<String, EntityDTO> getEntityDTO() {
        Map<String, EntityDTO> res = world.getEntities().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entityEntry -> {
                            EntityDef entity = entityEntry.getValue();
                            Map<String, PropertyDTO> propertiesDTO = entity.getProps().entrySet().stream()
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            propertyEntry -> {
                                                PropertyDef property = propertyEntry.getValue();
                                                return new PropertyDTO(
                                                        property.getName(),
                                                        property.getType().name(),
                                                        property.getValue().getFrom(),
                                                        property.getValue().getTo(),
                                                        property.getValue().isRandom()
                                                );
                                            }
                                    ));
                            return new EntityDTO(entity.getName(), propertiesDTO);
                        }
                ));

        return res;
    }

    @Override
    public Map<String, RuleDTO> getRulesDTO() {

        Map<String, RuleDTO> res = new HashMap<>();

        for (Map.Entry<String, Rule> entry : world.getRules().entrySet()) {
            Rule rule = entry.getValue();
            List<Action> actions = rule.getActionsToPerform();
            List<ActionDTO> actionsDTO = getActionsDTO(actions);
            RuleDTO ruleDTO = new RuleDTO(rule.getName(), rule.getTicks(), rule.getProbability(), actionsDTO);
            res.put(rule.getName(), ruleDTO);
        }
        return res;
    }

    @Override
    public List<ActionDTO> getActionsDTO(List<Action> actions) {
        List<ActionDTO> res = new ArrayList<>();
        for (Action action : actions) {
            // general action
            String actionType = action.getActionType().toString();
            String mainEntity = action.getMainEntity().getName();
            String secondaryEntity = null;
            if (action.getSecondaryEntity() != null)
                secondaryEntity = action.getSecondaryEntity().getName();
            ActionDTO actionDTO = null;
            switch (actionType.toUpperCase()) {
                case "INCREASE":
                case "DECREASE":
                case "SET":
                    actionDTO = new SingleExpressionDTO(stringToShow(actionType), mainEntity, secondaryEntity,
                            ((SingleExpression) action).getProperty(), ((SingleExpression) action).getExpression().getExpressionName());
                    break;

                case "KILL":
                    actionDTO = new ActionDTO("Kill", mainEntity, secondaryEntity);
                    break;

                case "CALCULATION":
                    CalculationAction calculationAction = (CalculationAction) action;
                    actionDTO = new CalculationDTO(mainEntity, secondaryEntity,
                            calculationAction.getProperty(), calculationAction.getCalculationType().name(),
                            calculationAction.getArg1().getExpressionName(), calculationAction.getArg2().getExpressionName());
                    break;

                case "CONDITION":
                    ConditionAction conditionAction = (ConditionAction) action;
                    AbstractCondition abstractCondition = conditionAction.getCondition();
                    if (abstractCondition.isSingle()) {
                        SingleCondition singleCondition = (SingleCondition) abstractCondition;
                        actionDTO = new SingleConditionDTO(mainEntity, secondaryEntity, singleCondition.getOperand().getExpressionName(),
                                singleCondition.getOperator().getOperatorType().name(), singleCondition.getValueExpression().getExpressionName(),
                                Integer.toString(conditionAction.getThenActions().size()), Integer.toString(conditionAction.getElseActions().size()));
                    } else {
                        MultipleCondition multipleCondition = (MultipleCondition) abstractCondition;
                        actionDTO = new MultipleConditionDTO(mainEntity, secondaryEntity, multipleCondition.getLogical(),
                                Integer.toString(multipleCondition.getConditionList().size()),
                                Integer.toString(conditionAction.getThenActions().size()), Integer.toString(conditionAction.getElseActions().size()));
                    }
                    break;

                case "REPLACE":
                    ReplaceAction replaceAction = (ReplaceAction) action;
                    actionDTO = new ReplaceDTO(mainEntity, secondaryEntity, replaceAction.getEntityToCreate().getName(), replaceAction.getMode());
                    break;

                case "PROXIMITY":
                    ProximityAction proximityAction = (ProximityAction) action;
                    actionDTO = new ProximityDTO(mainEntity, secondaryEntity, proximityAction.getTargetEntity().getName(),
                            proximityAction.getDepth().getExpressionName(), Integer.toString(proximityAction.getActions().size()));
                    break;
            }
            res.add(actionDTO);
        }
        return res;
    }

    public String stringToShow(String str) {
        // Convert the entire string to lowercase
        String lowerCaseInput = str.toLowerCase();

        // Get the first character in uppercase
        String firstLetterUpperCase = lowerCaseInput.substring(0, 1).toUpperCase();

        // Get the rest of the characters
        String restOfTheString = lowerCaseInput.substring(1);

        // Concatenate the first uppercase letter and the rest of the lowercase string
        return (firstLetterUpperCase + restOfTheString);
    }

    @Override
    public Map<String, EnvironmentDTO> getEnvironmentsDTO() {
        Map<String, EnvironmentDTO> res = new HashMap<>();
        Map<String, PropertyDef> environments = world.getEnvironments().getProps();
        String from, to;
        EnvironmentDTO envDTO;

        for (Map.Entry<String, PropertyDef> environment : environments.entrySet()) {
            if (environment.getValue().getValue().getFrom() == null) {
                from = null;
                to = null;
            } else {
                from = Float.toString(environment.getValue().getValue().getFrom());
                to = Float.toString(environment.getValue().getValue().getTo());
            }
            envDTO = new EnvironmentDTO(environment.getKey(), environment.getValue().getType().name(), from, to);
            res.put(environment.getKey(), envDTO);
        }
        return res;
    }

    @Override
    public Map<String, ActiveEnvironmentDTO> getActiveEnvironmentsDTO(ActiveEnvironment activeEnvironment) {
        Map<String, ActiveEnvironmentDTO> res = new HashMap<>();
        Map<String, PropertyInstance> activeEnvVariables = activeEnvironment.getEnvVariables();

        for (Map.Entry<String, PropertyInstance> activeEnvVariable : activeEnvVariables.entrySet()) {
            ActiveEnvironmentDTO activeEnvDTO = new ActiveEnvironmentDTO(activeEnvVariable.getValue().getPropertyDef().getType().toString(), activeEnvVariable.getValue().getValue().toString());
            res.put(activeEnvVariable.getKey(), activeEnvDTO);
        }
        return res;
    }

    @Override
    public void addSimulationDTO(SimulationExecutionDetails simulationExecutionDetailsToAdd) {
    }

    @Override
    public SimulationsDTO getSimulationsDTO() {
        return simulationsDTO;
    }

    public SimulationDetailsDTO getSimulationDetailsDTO(int id){
        if(simulationExecutionDetailsList.isEmpty())
            return null;

        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsList.get(id - 1);
        String timeFormat = simulationExecutionDetails.getTimeFormat();
        Map<String, SimulatedEntityDTO> simulatedEntities = new HashMap<>();

        for (Map.Entry<String, EntityInstanceManager> entityEntry : simulationExecutionDetails.getEntityNameToInstanceManager().entrySet()) {
            SimulatedEntityDTO simulatedEntityDTO = getSimulatedEntityDTO(entityEntry);
            simulatedEntities.put(entityEntry.getKey(), simulatedEntityDTO);
        }


        ActiveEnvironmentsDTO activeEnvironmentsDTO = new ActiveEnvironmentsDTO(getActiveEnvironmentsDTO(simulationExecutionDetails.getActiveEnvironment()));


        return new SimulationDetailsDTO(timeFormat, id, world.getTermination().getSecondsDefine(), simulatedEntities, activeEnvironmentsDTO,
                simulationExecutionDetails.getTicksSinceStartProperty().get(),
                simulationExecutionDetails.getSecondsSinceStartProperty().get(),
                simulationExecutionDetails.getStatusProperty().get(), simulationExecutionDetails.getPopulationPerTick(), simulationExecutionDetails.getTicksSinceStart());
    }

    @Override
    public SimulatedEntityDTO getSimulatedEntityDTO(Map.Entry<String, EntityInstanceManager> entityEntry) {
        String name = entityEntry.getKey();
        int initPopulation = entityEntry.getValue().getInitPopulation();
        SimulatedEntityInstanceDTO simulatedEntityInstanceDTO;
        List<SimulatedEntityInstanceDTO> simulatedEntityInstanceDTOMap = new ArrayList<>();
        // gets list of each entity instance of the current entity definition
        for (EntityInstance entityInstance: entityEntry.getValue().getInstances()) {
            Map<String, SimulatedPropertyDTO> simulatedPropertiesDTO = getSimulatedPropertiesDTO(entityInstance);
            simulatedEntityInstanceDTO = new SimulatedEntityInstanceDTO(simulatedPropertiesDTO);
            simulatedEntityInstanceDTOMap.add(simulatedEntityInstanceDTO);
        }

        return new SimulatedEntityDTO(simulatedEntityInstanceDTOMap, name, initPopulation);
    }


    @Override
    public Map<String, SimulatedPropertyDTO> getSimulatedPropertiesDTO(EntityInstance entityInstance) {
        Map<String, SimulatedPropertyDTO> res = new HashMap<>();
        SimulatedPropertyDTO simulatedPropertyDTO;
        // gets all properties of current entity instance
        for (PropertyInstance propertyInstance : entityInstance.getProperties().values()) {
            simulatedPropertyDTO = new SimulatedPropertyDTO(propertyInstance.getPropertyDef().getName(),
                    propertyInstance.getPropertyDef().getType().name(),
                    propertyInstance.getValue(), propertyInstance.getTicks());
            res.put(propertyInstance.getPropertyDef().getName(), simulatedPropertyDTO);
        }

        return res;
    }

}