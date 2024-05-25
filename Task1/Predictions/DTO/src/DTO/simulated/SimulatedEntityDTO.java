package DTO.simulated;

import java.util.List;

public class SimulatedEntityDTO {
    private final List<SimulatedPropertyDTO> simulatedPropertyDTOList;
    private final String entityName;
    private final int initPopulation;
    private final int finalPopulation;

    public SimulatedEntityDTO(List<SimulatedPropertyDTO> simulatedPropertyDTOList, String entityName, int initPopulation, int finalPopulation) {
        this.simulatedPropertyDTOList = simulatedPropertyDTOList;
        this.entityName = entityName;
        this.initPopulation = initPopulation;
        this.finalPopulation = finalPopulation;
    }
    public List<SimulatedPropertyDTO> getSimulatedPropertyDTOList() {
        return simulatedPropertyDTOList;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getInitPopulation() {
        return initPopulation;
    }
    public int getFinalPopulation() {
        return finalPopulation;
    }

}
