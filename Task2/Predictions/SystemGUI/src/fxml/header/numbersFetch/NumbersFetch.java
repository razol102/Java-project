package fxml.header.numbersFetch;

import fxml.header.HeaderController;
import javafx.application.Platform;
import simulation.impl.SimulationExecutionManager;

public class NumbersFetch implements Runnable{

    private volatile boolean stopThread = false;
    private final HeaderController headerController;
    private int inQueueCounter;
    private int runningCounter;
    private int finishedCounter;

    public NumbersFetch(HeaderController headerController){
        this.headerController = headerController;
    }


    @Override
    public void run() {
        SimulationExecutionManager simulationExecutionManager = headerController.getAppController().getSimulationExecutionManager();

        while(!stopThread) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int[] simulationsCounter = simulationExecutionManager.countSimulations();
            inQueueCounter = simulationsCounter[0];
            runningCounter = simulationsCounter[1];
            finishedCounter = simulationsCounter[2];
            Platform.runLater(this::updateNumbers);
        }

    }

    public void updateNumbers(){
        headerController.setInQueueSimulations(inQueueCounter);
        headerController.setRunningSimulations(runningCounter);
        headerController.setFinishedSimulations(finishedCounter);
    }

    public void stopThread() {
        stopThread = true;
    }
}
