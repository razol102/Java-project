package DTO.simulated;

import java.util.List;

public class SimulationDetailsDTO {
    private final String timeFormat;
    private final int id;
    private List<SimulatedEntityDTO> entities;

    public SimulationDetailsDTO(String timeFormat, int id, List<SimulatedEntityDTO> entities) {
        this.timeFormat = timeFormat;
        this.id = id;
        this.entities = entities;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public List<SimulatedEntityDTO> getEntities() {
        return entities;
    }
}
