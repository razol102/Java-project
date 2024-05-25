package fxml.body;

import fxml.AppController;
import fxml.body.details.DetailsController;
import fxml.body.newExecution.NewExecutionController;
import fxml.body.results.ResultsController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import simulation.impl.SimulationExecutionManager;

public class BodyController {
    @FXML private TabPane tabManager;
    @FXML private Tab tabDetails;
    @FXML private Tab tabNewExecution;
    @FXML private Tab tabResults;

    @FXML private AppController appController;
    @FXML private AnchorPane detailsComponent;
    @FXML private DetailsController detailsComponentController;

    @FXML private AnchorPane newExecutionComponent;
    @FXML private NewExecutionController newExecutionComponentController;

    @FXML private AnchorPane resultComponent;
    @FXML private ResultsController resultComponentController;
    private SimulationExecutionManager simulationExecutionManager;

    @FXML
    public void initialize() {
        if (detailsComponentController != null)
            detailsComponentController.setBodyController(this);
        if (newExecutionComponentController != null)
            newExecutionComponentController.setBodyController(this);
        if (resultComponentController != null)
            resultComponentController.setBodyController(this);
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public void setSimulationExecutionManager(SimulationExecutionManager simulationEngine){
        this.simulationExecutionManager = simulationEngine;
        newExecutionComponentController.setSimulationExecutionManager(simulationEngine);
        resultComponentController.setSimulationExecutionManager(simulationEngine);
    }

    public DetailsController getDetailsComponentController() {
        return detailsComponentController;
    }

    public NewExecutionController getNewExecutionComponentController() {
        return newExecutionComponentController;
    }

    public ResultsController getResultComponentController() {
        return resultComponentController;
    }

    public AppController getAppController() {
        return appController;
    }

    public TabPane getTabPane() {
        return tabManager;
    }
    public Tab getTabResults() {
        return tabResults;
    }
    public Tab getTabNewExecution(){ return tabNewExecution;}

}

