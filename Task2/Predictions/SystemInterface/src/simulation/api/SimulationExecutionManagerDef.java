package simulation.api;

import DTO.definition.EntityDTO;
import DTO.definition.EnvironmentDTO;
import DTO.definition.RuleDTO;
import DTO.definition.WorldDTO;
import DTO.definition.actionDTO.ActionDTO;
import DTO.simulated.*;
import definition.rule.action.api.Action;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.environment.api.ActiveEnvironment;
import simulation.impl.SimulationExecutionDetails;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;

public interface SimulationExecutionManagerDef {



    // ----XML FILE CHECK----
    boolean isXML(String filePath);
    String getFileExtension(String fileName);
    // ----XML FILE CHECK----


    void loadSystemDetails(String path) throws JAXBException;
    Map<String, EntityInstanceManager> createInstances(Map<String, Integer> newSimulationPopulations);
    void runSimulation(SimulationDetailsDTO simulationDetailsDTO);
    void setEntityPopulation(String entityName, int populationInput);
    void clearPopulations();
    ActiveEnvironment createActiveEnvironmentValues(Map<String, Object> envValueInputs);
    boolean isLoaded();


    // ----------------------------------DTO-----------------------------------------------

    WorldDTO getWorldDTO();
    Map<String, EntityDTO> getEntityDTO();
    Map<String, RuleDTO> getRulesDTO();
    List<ActionDTO> getActionsDTO(List<Action> actions);
    Map<String, EnvironmentDTO> getEnvironmentsDTO();
    Map<String, ActiveEnvironmentDTO> getActiveEnvironmentsDTO(ActiveEnvironment activeEnvironment);
    SimulationsDTO getSimulationsDTO();
    SimulatedEntityDTO getSimulatedEntityDTO(Map.Entry<String, EntityInstanceManager> entityEntry);
    void addSimulationDTO(SimulationExecutionDetails simulationExecutionDetailsToAdd);
    Map<String, SimulatedPropertyDTO> getSimulatedPropertiesDTO(EntityInstance entityInstance);
}
