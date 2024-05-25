package simulation.impl;

import execution.instance.entity.manager.api.EntityInstanceManager;
import simulation.api.SimulationDef;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Simulation implements SimulationDef {
    private int id;
    private LocalDateTime simulationDateTime ;
    Map<String, EntityInstanceManager> entityNameToInstanceManager;
    public Simulation(Map<String, EntityInstanceManager> entityNameToInstanceManager, int id, LocalDateTime simulationDateTime) {
        this.entityNameToInstanceManager = entityNameToInstanceManager;
        this.id = id;
        this.simulationDateTime = simulationDateTime;
    }

    @Override
    public String getTimeFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH.mm.ss");
        return simulationDateTime.format(formatter);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Map<String, EntityInstanceManager> getEntityNameToInstanceManager() {
        return entityNameToInstanceManager;
    }

}
