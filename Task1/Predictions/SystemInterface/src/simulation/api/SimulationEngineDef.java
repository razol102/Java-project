package simulation.api;

import DTO.EntityDTO;
import DTO.EnvironmentDTO;
import DTO.RuleDTO;
import DTO.WorldDTO;
import DTO.simulated.ActiveEnvironmentsDTO;
import DTO.simulated.SimulatedEntityDTO;
import DTO.simulated.SimulatedPropertyDTO;
import DTO.simulated.SimulationsDTO;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.manager.api.EntityInstanceManager;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;

public interface SimulationEngineDef {



    // ----XML FILE CHECK----
    boolean isXML(String filePath);
    String getFileExtension(String fileName);
    // ----XML FILE CHECK----


    void loadSystemDetails(String path) throws JAXBException;

    void runSimulation();

    void setActiveEnvironmentValues(Map<String, Object> envValueInputs);

    boolean isLoaded();



    // ----------------------------------DTO-----------------------------------------------

    WorldDTO getWorldDTO();

    List<EntityDTO> getEntityDTO();

    List<RuleDTO> getRulesDTO();

    List<EnvironmentDTO> getEnvironmentsDTO();

    ActiveEnvironmentsDTO getActivatedEnvDTO();

    SimulationsDTO getSimulationsDTO();
    SimulatedEntityDTO getSimulatedEntityDTO(Map.Entry<String, EntityInstanceManager> entityEntry);

    SimulatedPropertyDTO getSimulatedPropertyDTO(String propertyName, List<EntityInstance> entityInstances);

    // ----------------------------------DTO-----------------------------------------------

}
