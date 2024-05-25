package fxml.body.results.dataFetch;

import DTO.simulated.SimulationDetailsDTO;
import DTO.simulated.SimulationsDTO;
import fxml.body.results.ResultsController;
import fxml.body.results.execution.executionDetails.SimulationDetailsController;
import javafx.application.Platform;
import simulation.impl.SimulationExecutionManager;

public class DataFetch implements Runnable {
    private volatile boolean stopThread = false;
    private final ResultsController resultsController;
    private final SimulationDetailsController simulationDetailsController;
    private int counter = 0;

    public DataFetch(ResultsController resultsController){
        this.resultsController = resultsController;
        this.simulationDetailsController = resultsController.getSimulationDetailsController();
    }

    @Override
    public void run() {
        SimulationExecutionManager simulationExecutionManager = resultsController.getSimulationExecutionManager();
        SimulationsDTO simulationsDTO = simulationExecutionManager.getSimulationsDTO();
        int id = resultsController.getSelectedSimulationID();
        SimulationDetailsDTO selectedSimulationDetailsDTO = simulationsDTO.getSimulations().get(id - 1);

        while(!stopThread) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(selectedSimulationDetailsDTO.isFinished()){
                if(!stopThread) {
                    if(counter == 0) {
                        Platform.runLater(() -> {
                            updateScreens(selectedSimulationDetailsDTO);
                        });
                    }
                }
            }
            else {
                final SimulationDetailsDTO updatedSelectedSimulationDetailsDTO = simulationExecutionManager.getSimulationDetailsDTO(selectedSimulationDetailsDTO.getId());
                if (updatedSelectedSimulationDetailsDTO != null) {
                    simulationsDTO.getSimulations().set(id - 1, updatedSelectedSimulationDetailsDTO);
                    if (!stopThread) {
                        if (counter == 0) {
                            Platform.runLater(() -> {
                                updateScreens(updatedSelectedSimulationDetailsDTO);
                            });
                        }
                    }
                }
            }
        }
    }

    public void updateScreens(SimulationDetailsDTO selectedSimulationDetailsDTO){
        if(counter == 0) {
            resultsController.getSimulationDetailsController().setSelectedSimulationDetails(selectedSimulationDetailsDTO);
            counter++;
        }
        if(counter == 1) {
            resultsController.setSelectedSimulationResults(selectedSimulationDetailsDTO);
            counter--;
        }
    }

    public void stopThread() {
        stopThread = true;
    }
}


