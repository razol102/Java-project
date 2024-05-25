package DTO.simulated;

import java.util.List;

public class SimulatedEntityDTO {
    private final List<SimulatedEntityInstanceDTO> entityInstances;
    private final String entityName;
    private final int initPopulation;
    private final int currentPopulation;

    public SimulatedEntityDTO(List<SimulatedEntityInstanceDTO> entityInstances, String entityName, int initPopulation) {
        this.entityInstances = entityInstances;
        this.entityName = entityName;
        this.initPopulation = initPopulation;
        currentPopulation = entityInstances.size();
    }

    public String getEntityName() {
        return entityName;
    }

    public int getInitPopulation() {
        return initPopulation;
    }
    public int getCurrentPopulation() {
        return currentPopulation;
    }

    public List<SimulatedEntityInstanceDTO> getEntityInstances() {
        return entityInstances;
    }
}
