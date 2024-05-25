package simulation.impl;

import definition.entity.secondaryEntity.SecondaryEntityDef;
import definition.rule.Rule;
import definition.rule.action.api.Action;
import execution.context.impl.Context;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.ins.impl.Coordinate;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.api.PropertyInstance;
import javafx.beans.property.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class SimulationRunner implements Runnable{
    private SimulationExecutionDetails simulationExecutionDetails;
    private final IntegerProperty ticksSinceStart;
    private final LongProperty secondsSinceStart;
    private final StringProperty status;
    private Instant startTime, currentTime;
    private boolean pleaseWait = false; // Flag to control sleeping
    private boolean userTermination = false;
    private final Object synchronizedLock;
    public SimulationRunner(SimulationExecutionDetails simulationExecutionDetails){
        this.simulationExecutionDetails = simulationExecutionDetails;
        simulationExecutionDetails.setSimulationRunner(this);
        ticksSinceStart = new SimpleIntegerProperty();
        secondsSinceStart = new SimpleLongProperty();
        status = new SimpleStringProperty(StatusEnum.IN_QUEUE.getStatusString());
        simulationExecutionDetails.getTicksSinceStartProperty().bind(ticksSinceStart);
        simulationExecutionDetails.getSecondsSinceStartProperty().bind(secondsSinceStart);
        simulationExecutionDetails.getStatusProperty().bind(status);
        synchronizedLock = new Object();
    }

    @Override
    public void run() {
        startTime = Instant.now();
        status.set(StatusEnum.RUNNING.getStatusString());
        setEntitiesOnGrid();
        Map<String, EntityInstanceManager> entityNameToInstanceManager = simulationExecutionDetails.getEntityNameToInstanceManager();
        ActiveEnvironment activeEnvironment = simulationExecutionDetails.getActiveEnvironment();

        while (!(simulationExecutionDetails.getTermination().checkTermination(ticksSinceStart.get(), startTime.getEpochSecond(), userTermination))) {
            addPopulationForCurrentTick();
            moveEntities(entityNameToInstanceManager);
            List<Action> activatedActions = getListOfActivatedActions();

            for(Map.Entry<String, EntityInstanceManager> entityInstanceManagerEntry: entityNameToInstanceManager.entrySet()) {
                List<EntityInstance> entityInstances = entityInstanceManagerEntry.getValue().getInstances();
                for(EntityInstance entityInstance: entityInstances) {
                    for (Action action : activatedActions) {
                        if (action.getMainEntity().getName().equals(entityInstanceManagerEntry.getKey())) {
                            SecondaryEntityDef secondaryEntity = action.getSecondaryEntity();
                            if (secondaryEntity == null) {
                                Context context = new Context(entityInstance, null, entityInstanceManagerEntry.getValue(), activeEnvironment);
                                action.invoke(context);
                            }
                            else{ // secondaryEntity exits
                                List<EntityInstance> secondaryInstances = getSecondaryInstances(entityNameToInstanceManager.get(secondaryEntity.getName()), secondaryEntity);
                                if(secondaryInstances.isEmpty()){
                                    Context context = new Context(entityInstance, null, entityInstanceManagerEntry.getValue(), activeEnvironment);
                                    action.invoke(context);
                                }
                                for(EntityInstance secondaryInstance : secondaryInstances) {
                                    Context context = new Context(entityInstance, secondaryInstance, entityInstanceManagerEntry.getValue(), activeEnvironment);
                                    action.invoke(context);
                                }
                            }
                        }
                    }
                }
            }

            // tick properties
            for(Map.Entry<String, EntityInstanceManager> entityInstanceManagerEntry: entityNameToInstanceManager.entrySet())
                for(EntityInstance entityInstance: entityInstanceManagerEntry.getValue().getInstances())
                    for (PropertyInstance propertyInstance : entityInstance.getProperties().values())
                        propertyInstance.tick();

            synchronized (synchronizedLock) {
                // removes the dead instances and updating new entities from replce action
                updateInstances();
                currentTime = Instant.now();
                secondsSinceStart.set(Duration.between(startTime, currentTime).getSeconds()); // currentTime - startTime (Delta)
                ticksSinceStart.set(ticksSinceStart.get() + 1);

                // if user pauses the simulation
                if(pleaseWait){
                    try {
                        pleaseWait = false;
                        status.set(StatusEnum.PAUSED.getStatusString());
                        synchronizedLock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        status.set(StatusEnum.FINISHED.getStatusString());
    }

    public void setEntitiesOnGrid() {
        for(EntityInstanceManager entityInstanceManager: simulationExecutionDetails.getEntityNameToInstanceManager().values())
            for(EntityInstance entityInstance: entityInstanceManager.getInstances())
                entityInstance.setCoordinate(getAvailableCoordinate());
    }

    public List<Action> getListOfActivatedActions() {
        List<Action> res = new ArrayList<>();
        for (Map.Entry<String, Rule> rule : simulationExecutionDetails.getRules().entrySet())
            if (rule.getValue().isActivated(ticksSinceStart.get()))
                for (Action action : rule.getValue().getActionsToPerform())
                    res.add(action);
        return res;
    }

    public SimulationExecutionDetails getSimulationExecutionDetails() {
        return simulationExecutionDetails;
    }

    public void setSimulationExecutionDetails(SimulationExecutionDetails simulationExecutionDetails) {
        this.simulationExecutionDetails = simulationExecutionDetails;
    }

    public void moveEntities(Map<String, EntityInstanceManager> entityNameToInstanceManager) {
        for (EntityInstanceManager entityInstanceManager: entityNameToInstanceManager.values())
            for (EntityInstance entityInstance: entityInstanceManager.getInstances())
                entityInstance.changeCoordinate(simulationExecutionDetails.getGrid());
    }

    public List<EntityInstance> getSecondaryInstances(EntityInstanceManager entityInstanceManager, SecondaryEntityDef secondaryEntity){
        Random random = new Random();
        List<Context> contextsByCondition = new ArrayList<>();
        List<EntityInstance> res = new ArrayList<>();

        // ALL contexts
        if(secondaryEntity.getCount() == 0)
            return entityInstanceManager.getInstances();

        // inserts all the contexts that pass the condition
        for(EntityInstance entityInstance: entityInstanceManager.getInstances()) {
            Context context = new Context(entityInstance, null, entityInstanceManager, simulationExecutionDetails.getActiveEnvironment());
            if(secondaryEntity.getCondition().operatorResult(context))
                contextsByCondition.add(context);
        }
        if(!(contextsByCondition.isEmpty())) {
            for (int i = 0; i < secondaryEntity.getCount(); i++) {
                int randomIndex = random.nextInt(contextsByCondition.size()); // Generate a random index
                EntityInstance entityToAdd = contextsByCondition.get(randomIndex).getPrimaryEntityInstance();
                res.add(entityToAdd);
            }
        }
        return res;
    }

    public Coordinate getAvailableCoordinate() {
        Random random = new Random();
        Coordinate[][] grid = simulationExecutionDetails.getGrid();
        while (true) {
            int randomRow = random.nextInt(grid.length);
            int randomColumn = random.nextInt(grid[0].length);
            if (!grid[randomRow][randomColumn].isSet()) {
                grid[randomRow][randomColumn].setSet(true);
                return grid[randomRow][randomColumn];
            }
        }
    }

    public void setPleaseWait() {
        pleaseWait = true;
    }

    public void setUserTermination() {
        userTermination = true;
    }

    public void setStatus(String newStatus) {
        status.set(newStatus);
    }

    public void updateRunningTime(){
        Instant realTime = Instant.now();
        Duration duration = Duration.between(currentTime, realTime);
        startTime = startTime.plus(duration);
        currentTime = realTime;
    }

    public void updateInstances() {
        for(Map.Entry<String, EntityInstanceManager> entityInstanceManagerEntry: simulationExecutionDetails.getEntityNameToInstanceManager().entrySet()) {
            entityInstanceManagerEntry.getValue().killingSpree();
            entityInstanceManagerEntry.getValue().concatInstances();
        }
    }
    public void addPopulationForCurrentTick() {
        int tenThousand = 10000;
        if(ticksSinceStart.get() % tenThousand == 0) {
            Map<String, Integer> populationForCurrentTick = new HashMap<>();
            for(Map.Entry<String, EntityInstanceManager> entityInstanceManagerEntry: simulationExecutionDetails.getEntityNameToInstanceManager().entrySet())
                populationForCurrentTick.put(entityInstanceManagerEntry.getKey(), entityInstanceManagerEntry.getValue().getInstances().size());
            simulationExecutionDetails.addToPopulationPerTick(populationForCurrentTick);
        }

    }

    public void continueSimulation() {
        synchronized (synchronizedLock) {
            synchronizedLock.notifyAll();
        }
    }
}
