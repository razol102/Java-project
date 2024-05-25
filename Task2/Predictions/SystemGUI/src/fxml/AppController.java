package fxml;

import DTO.definition.WorldDTO;
import fxml.body.BodyController;
import fxml.header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import simulation.impl.SimulationExecutionManager;

public class AppController {
    private SimulationExecutionManager simulationExecutionManager;
    @FXML private VBox headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private GridPane bodyComponent;
    @FXML private BodyController bodyComponentController;

    @FXML
    public void initialize(){
        simulationExecutionManager = new SimulationExecutionManager();
        if(headerComponentController != null && bodyComponentController != null){
            headerComponentController.setAppController(this);
            bodyComponentController.setMainController(this);
            bodyComponentController.setSimulationExecutionManager(simulationExecutionManager);
        }
    }

    public SimulationExecutionManager getSimulationExecutionManager() {
        return simulationExecutionManager;
    }
    public void setBodyDetailsTree(WorldDTO worldDTO) {
        bodyComponentController.getDetailsComponentController().setDetailsTree(worldDTO);
    }

    public BodyController getBodyComponentController() {
        return bodyComponentController;
    }

    public HeaderController getHeaderComponentController() {
        return headerComponentController;
    }
}
