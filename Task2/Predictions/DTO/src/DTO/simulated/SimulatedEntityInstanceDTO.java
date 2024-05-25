package DTO.simulated;

import java.util.Map;

public class SimulatedEntityInstanceDTO {
    private final Map<String, SimulatedPropertyDTO> simulatedPropertyDTOList;

    public SimulatedEntityInstanceDTO(Map<String, SimulatedPropertyDTO> simulatedPropertyDTOList){
        this.simulatedPropertyDTOList = simulatedPropertyDTOList;
    }

    public Map<String, SimulatedPropertyDTO> getSimulatedPropertyDTOList() {
        return simulatedPropertyDTOList;
    }
}
