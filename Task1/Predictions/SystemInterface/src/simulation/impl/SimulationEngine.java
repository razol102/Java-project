package simulation.impl;

import DTO.*;
import DTO.simulated.*;
import definition.entity.EntityDef;
import definition.property.api.PropertyDef;
import definition.rule.Rule;
import definition.rule.action.api.Action;
import execution.context.impl.Context;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.entity.manager.impl.EntityInstanceManagerImpl;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.api.PropertyInstance;
import execution.instance.property.impl.PropertyInstanceImpl;
import generated.PRDWorld;
import simulation.api.SimulationEngineDef;
import world.World;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationEngine implements SimulationEngineDef {
    private int simulationsCounter = 0;
    private List<Simulation> simulationList;
    private World world;
    private ActiveEnvironment currentActiveEnvironments;
    private boolean loaded = false;
    public SimulationEngine() {
        world = new World();
        simulationList = new ArrayList<>();
    }

    // command #1
    @Override
    public void loadSystemDetails(String path) throws JAXBException {
        if(!isXML(path))
            throw new IllegalArgumentException("The file you were trying to load is not XML. It must be XML!\n");

        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(PRDWorld.class);
        Unmarshaller jaxbUnmarshaller = null;
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PRDWorld prdWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
        World newWorld = new World();
        newWorld.transferDataFromXMLToEngine(prdWorld);
        loaded = true;
        this.world = newWorld;
    }

    // ----XML FILE CHECK----
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
    public void runSimulation() {
        // Creating instances
        Map<String, EntityInstanceManager> entityNameToInstanceManager = new HashMap<>();
        for(Map.Entry<String, EntityDef> entityEntry: world.getEntities().entrySet()) {
            EntityInstanceManager entityInstanceManagerToAdd = new EntityInstanceManagerImpl(entityEntry.getValue().getPopulation(), entityEntry.getValue().getProps().keySet());

            for(int i = 0; i< entityEntry.getValue().getPopulation(); i++) {
                entityInstanceManagerToAdd.createInstance(entityEntry.getValue());
            }
            entityNameToInstanceManager.put(entityEntry.getKey(), entityInstanceManagerToAdd);
        }

        long ticksSinceStart = 0;
        LocalDateTime simulationDateTime = LocalDateTime.now();
        simulationsCounter++;
        Instant currentInstant = Instant.now();
        long secondsSinceStart = currentInstant.getEpochSecond();


        // list that contains for each entity definition a list of contexts(context for each instance)
        while (!(world.getTermination().checkTermination(ticksSinceStart, secondsSinceStart))) {
            for (Map.Entry<String, Rule> rule : world.getRules().entrySet()) {
                if (rule.getValue().isActivated(ticksSinceStart)) {
                    for (Action action : rule.getValue().getActionsToPerform()) {
                        EntityInstanceManager managerInAction = entityNameToInstanceManager.get(action.getContextEntity().getName());
                        for (EntityInstance entityInstance : managerInAction.getInstances()) {
                            Context context = new Context(entityInstance, managerInAction, currentActiveEnvironments);
                            action.invoke(context);
                        }
                        managerInAction.killingSpree();
                    }
                }
            }
            ticksSinceStart++;
        }

        Simulation newSimulation = new Simulation(entityNameToInstanceManager, simulationsCounter, simulationDateTime);
        simulationList.add(newSimulation);
    }

    @Override
    public void setActiveEnvironmentValues(Map<String, Object> envValueInputs) {
        ActiveEnvironment activeEnvironment = world.getEnvironments().createActiveEnvironment();
        Map<String, PropertyDef> worldProps = world.getEnvironments().getProps();
        PropertyInstanceImpl propertyInstanceToAdd;
        Object value;

        for(Map.Entry<String,Object> entry: envValueInputs.entrySet()) {
            if (entry.getValue() != null)
                value = entry.getValue();
            else
                value = worldProps.get(entry.getKey()).createValue();
            propertyInstanceToAdd = new PropertyInstanceImpl(worldProps.get(entry.getKey()), value);
            activeEnvironment.addPropertyInstance(propertyInstanceToAdd);
        }
        currentActiveEnvironments = activeEnvironment;          // saving the active environments for future simulation
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }


    // ----------------------------------DTO-----------------------------------------------
    @Override
    public WorldDTO getWorldDTO() {

        List<EntityDTO> entitiesDTO = getEntityDTO();
        List<RuleDTO> rulesDTO = getRulesDTO();
        TerminationDTO terminationDTO = new TerminationDTO(world.getTermination().getTicks(), world.getTermination().getSeconds());

        return new WorldDTO(entitiesDTO, rulesDTO, terminationDTO);
    }

    @Override
    public List<EntityDTO> getEntityDTO(){
        List<EntityDTO> entitiesDTO = world.getEntities().entrySet().stream()
                .map(entityEntry -> {
                    List<PropertyDTO> propertiesDTO = entityEntry.getValue().getProps().entrySet().stream()
                            .map(propertyEntry -> new PropertyDTO(
                                    propertyEntry.getValue().getName(),
                                    propertyEntry.getValue().getType().name(),
                                    propertyEntry.getValue().getValue().getFrom(),
                                    propertyEntry.getValue().getValue().getTo(),
                                    propertyEntry.getValue().getValue().isRandom()
                            ))
                            .collect(Collectors.toList());

                    return new EntityDTO(
                            entityEntry.getKey(),
                            entityEntry.getValue().getPopulation(),
                            propertiesDTO
                    );
                })
                .collect(Collectors.toList());
        return entitiesDTO;
    }
    @Override
    public List<RuleDTO> getRulesDTO(){
        List<RuleDTO> rulesDTO = world.getRules().entrySet().stream()
                .map(entry -> {
                    List<String> actionNames = entry.getValue().getActionsToPerform().stream()
                            .map(action -> action.getActionType().name())
                            .collect(Collectors.toList());

                    return new RuleDTO(entry.getValue().getName(), entry.getValue().getTicks(),
                            entry.getValue().getProbability(), actionNames);
                })
                .collect(Collectors.toList());
        return rulesDTO;
    }
    @Override
    public List<EnvironmentDTO> getEnvironmentsDTO() {
        List<EnvironmentDTO> res = new ArrayList<>();
        Map<String, PropertyDef> environments = world.getEnvironments().getProps();
        String from, to;
        EnvironmentDTO envDTO;

        for (Map.Entry<String, PropertyDef> environment : environments.entrySet()) {
            if(environment.getValue().getValue().getFrom() == null)
            {
                from = null;
                to = null;
            }
            else {
                from = Float.toString(environment.getValue().getValue().getFrom());
                to = Float.toString(environment.getValue().getValue().getTo());
            }
            envDTO = new EnvironmentDTO(environment.getKey(), environment.getValue().getType().name(), from, to);
            res.add(envDTO);
        }
        return res;
    }
    @Override
    public ActiveEnvironmentsDTO getActivatedEnvDTO() {
        Map<String, Object> environmentsValues = new HashMap<>();
        for(Map.Entry<String, PropertyInstance> envVariable: currentActiveEnvironments.getEnvVariables().entrySet()) {
            environmentsValues.put(envVariable.getKey(), envVariable.getValue().getValue());
        }
        return new ActiveEnvironmentsDTO(environmentsValues);
    }
    @Override
    public SimulationsDTO getSimulationsDTO() {
        List<SimulationDetailsDTO> simulationDTOList = new ArrayList<>();
        SimulationDetailsDTO simulationDTO;

        // gets details for each simulation and adds it to the list
        for(Simulation simulation: simulationList) {
            String timeFormat = simulation.getTimeFormat();
            int id = simulation.getId();
            // gets details for each entity in the current simulation
            List<SimulatedEntityDTO> simulatedEntities = new ArrayList<>();
            for(Map.Entry<String, EntityInstanceManager> entityEntry: simulation.getEntityNameToInstanceManager().entrySet()) {
                SimulatedEntityDTO simulatedEntityDTO = getSimulatedEntityDTO(entityEntry);
                simulatedEntities.add(simulatedEntityDTO);
            }
            simulationDTO = new SimulationDetailsDTO(timeFormat, id, simulatedEntities);
            simulationDTOList.add(simulationDTO);
        }
        return new SimulationsDTO(simulationDTOList);
    }


    //  public SimulatedEntityDTO(List<SimulatedPropertyDTO> simulatedPropertyDTOList, String entityName, int initPopulation, int finalPopulation)
    @Override
    public SimulatedEntityDTO getSimulatedEntityDTO(Map.Entry<String, EntityInstanceManager> entityEntry) {
        String name = entityEntry.getKey();
        int initPopulation = entityEntry.getValue().getInitPopulation();
        int finalPopulation = entityEntry.getValue().getInstances().size();
        List<SimulatedPropertyDTO> simulatedPropertyDTOList = new ArrayList<>();
        // gets list of each property which holds all the values of the instances
        for(String propertyName: entityEntry.getValue().getPropertyNames()) {
            SimulatedPropertyDTO simulatedPropertyDTO = getSimulatedPropertyDTO(propertyName, entityEntry.getValue().getInstances());
            simulatedPropertyDTOList.add(simulatedPropertyDTO);
        }
        return new SimulatedEntityDTO(simulatedPropertyDTOList, name, initPopulation, finalPopulation);
    }

    @Override
    public SimulatedPropertyDTO getSimulatedPropertyDTO(String propertyName, List<EntityInstance> entityInstances) {
        List<String> values = new ArrayList<>();
        // gets all values of the instances for the current property
        for(EntityInstance entityInstance: entityInstances) {
            values.add(String.valueOf(entityInstance.getPropertyByName(propertyName).getValue()));
        }
        return new SimulatedPropertyDTO(propertyName, values);
    }
}
