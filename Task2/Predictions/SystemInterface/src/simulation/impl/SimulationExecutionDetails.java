package simulation.impl;

import definition.rule.Rule;
import definition.terminatoin.Termination;
import execution.instance.entity.ins.impl.Coordinate;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.environment.api.ActiveEnvironment;
import javafx.beans.property.*;
import simulation.api.SimulationExecutionDetailsDef;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulationExecutionDetails implements SimulationExecutionDetailsDef {
    private final int id;
    private final IntegerProperty ticksSinceStart;
    private final LongProperty secondsSinceStart;
    private final StringProperty status;
    private final LocalDateTime simulationDateTime;
    private final Coordinate[][] grid;
    private final Map<String, EntityInstanceManager> entityNameToInstanceManager;
    private final List<Map<String, Integer>> populationPerTick;
    private final ActiveEnvironment activeEnvironment;
    private final Termination termination;
    private final Map<String, Rule> rules;
    private SimulationRunner simulationRunner;

    public SimulationExecutionDetails(Map<String, EntityInstanceManager> entityNameToInstanceManager, ActiveEnvironment activeEnvironment, Map<String, Rule> rules, Termination termination, int rows, int columns, int id) {
        this.entityNameToInstanceManager = entityNameToInstanceManager;
        this.activeEnvironment = activeEnvironment;
        this.id = id;
        this.termination = termination;
        this.rules = rules;
        this.grid = new Coordinate[rows][columns];
        for(int i = 0; i<rows; i++)
            for(int j=0; j< columns; j++)
                grid[i][j] = new Coordinate(i,j);
        simulationDateTime = LocalDateTime.now();
        populationPerTick = new ArrayList<>();

        // init properties:
        ticksSinceStart = new SimpleIntegerProperty(0);
        secondsSinceStart = new SimpleLongProperty(0);
        status = new SimpleStringProperty(StatusEnum.IN_QUEUE.getStatusString());
    }

    public Map<String, Rule> getRules() {
        return rules;
    }

    @Override
    public String getTimeFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
        return simulationDateTime.format(formatter);
    }

    // getters:
    @Override
    public int getId() {
        return id;
    }
    @Override
    public Map<String, EntityInstanceManager> getEntityNameToInstanceManager() {
        return entityNameToInstanceManager;
    }
    public Termination getTermination() {
        return termination;
    }
    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }
    public Coordinate[][] getGrid() {
        return grid;
    }
    public IntegerProperty getTicksSinceStartProperty() {
        return ticksSinceStart;
    }
    public LongProperty getSecondsSinceStartProperty() {
        return secondsSinceStart;
    }
    public StringProperty getStatusProperty() {
        return status;
    }
    public String getStatus() {
        return status.get();
    }
    public List<Map<String, Integer>> getPopulationPerTick() {
        return populationPerTick;
    }

    public int getTicksSinceStart() {
        return ticksSinceStart.get();
    }

    public void addToPopulationPerTick(Map<String, Integer> populationForCurrentTick) {
        int maxSize = 100;
        if(populationPerTick.size() == maxSize) {
            populationPerTick.remove(0);
        }
        populationPerTick.add(populationForCurrentTick);
    }


    // setters:
    public void setSimulationRunner(SimulationRunner simulationRunner) {
        this.simulationRunner = simulationRunner;
    }
    public void setStatus(String newStatus) {
        simulationRunner.setStatus(newStatus);
    }


    // thread actions:
    public void pauseThread() {
        simulationRunner.setPleaseWait();
    }
    public void stopThread() {
        simulationRunner.setUserTermination();
        simulationRunner.continueSimulation();
    }
    public void resumeThread() {
        synchronized (simulationRunner) {
            simulationRunner.updateRunningTime();
            simulationRunner.continueSimulation();
        }
    }
}
