package DTO.simulated;

import javafx.beans.property.*;

import java.util.List;
import java.util.Map;

public class SimulationDetailsDTO {
    private final int id;
    private final String timeFormat;
    private final ActiveEnvironmentsDTO activeEnvironmentsDTO;
    private final Map<String, SimulatedEntityDTO> entities;
    private final List<Map<String, Integer>> populationPerTick;
    private final int tickStartGraph;
    private final IntegerProperty currentTicks;
    private final LongProperty currentSeconds;
    private final StringProperty status;
    private final boolean bySeconds;


    public SimulationDetailsDTO(String timeFormat, int id, boolean bySeconds, Map<String, SimulatedEntityDTO> entities,
                                ActiveEnvironmentsDTO activeEnvironmentsDTO, int currentTicks, long currentSeconds,
                                String status, List<Map<String, Integer>> populationPerTick, int tickStartGraph) {
        this.id = id;
        this.timeFormat = timeFormat;
        this.activeEnvironmentsDTO = activeEnvironmentsDTO;
        this.entities = entities;
        this.populationPerTick = populationPerTick;
        this.tickStartGraph = tickStartGraph;
        this.currentTicks = new SimpleIntegerProperty(currentTicks);
        this.currentSeconds = new SimpleLongProperty(currentSeconds);
        this.status = new SimpleStringProperty(status);
        this.bySeconds = bySeconds;
    }

    public String getTimeFormat() {
        return timeFormat;
    }
    public Map<String, SimulatedEntityDTO> getEntities() {
        return entities;
    }
    public ActiveEnvironmentsDTO getActiveEnvironmentsDTO() {
        return activeEnvironmentsDTO;
    }
    public int getId() {
        return id;
    }
    public long getCurrentSeconds() {
        return currentSeconds.get();
    }
    public int getCurrentTicks() {
        return currentTicks.get();
    }
    public String getStatus() {
        return status.get();
    }
    public List<Map<String, Integer>> getPopulationPerTick() {
        return populationPerTick;
    }
    public int getTickStartGraph() {
        return tickStartGraph;
    }
    public boolean isFinished(){
        return status.get().equalsIgnoreCase("Finished");
    }
}
