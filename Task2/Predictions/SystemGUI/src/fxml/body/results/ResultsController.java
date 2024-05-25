package fxml.body.results;

import DTO.simulated.SimulationDetailsDTO;
import DTO.simulated.SimulationsDTO;
import fxml.body.BodyController;
import fxml.body.results.dataFetch.DataFetch;
import fxml.body.results.execution.executionDetails.SimulationDetailsController;
import fxml.body.results.execution.executionResult.SimulationResultController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import simulation.impl.SimulationExecutionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultsController {

    private SimulationExecutionManager simulationExecutionManager;
    @FXML
    private BodyController bodyController;
    @FXML
    private SimulationDetailsController simulationDetailsComponentController;
    @FXML private AnchorPane simulationDetailsComponent;
    @FXML private AnchorPane simulationResultComponent;
    @FXML
    private Label selectedSimulationLabel;
    @FXML
    private Button rerunButton;
    @FXML
    private VBox executionVbox;
    @FXML
    private VBox rightVBox;
    private List<VBox> executionsVBoxList = new ArrayList<>();
    private Boolean isFinishedLoaded = false;
    private Thread dataFetchingThread = null;
    private DataFetch dataFetch;
    private int selectedSimulationID;
    private SimulationsDTO simulationsDTO;
    private boolean animationActivated = false;

    @FXML
    public void initialize() {
        rerunButton.setDisable(true);
        rightVBox.setVisible(false);
        if(simulationDetailsComponentController != null)
            simulationDetailsComponentController.setResultsController(this);
    }

    public void setResultsScreen() {
        selectedSimulationID = -1;
        simulationDetailsComponent.setVisible(false);
        simulationResultComponent.setVisible(false);
        simulationDetailsComponentController.setSimulationDetailsScreen();
        rerunButton.setDisable(true);
        setExecutionVbox();
        simulationsDTO = simulationExecutionManager.getSimulationsDTO();
    }

    // handlers:
    @FXML
    public void handleRerunButton(ActionEvent event) {
        VBox prevSelected = executionsVBoxList.get(selectedSimulationID - 1);
        prevSelected.setStyle("-fx-background-color: default; -fx-border-color: #9d3737; -fx-border-width: 1px;");
        rerunButton.setDisable(true);
        rightVBox.setVisible(false);
        bodyController.getTabPane().getSelectionModel().select(bodyController.getTabNewExecution());
        bodyController.getNewExecutionComponentController().setRerun(simulationsDTO.getSimulations().get(selectedSimulationID - 1));
    }

    public void handleExecutionButtonClick(int newSelectedSimulationID) {
        if(selectedSimulationID == -1) {
            simulationResultComponent.setVisible(true);
            simulationDetailsComponent.setVisible(true);
        }
        else {
            dataFetchingThread.stop();
            VBox prevSelected = executionsVBoxList.get(selectedSimulationID - 1);
            prevSelected.setStyle("-fx-background-color: default; -fx-border-color: #9d3737; -fx-border-width: 1px;");
        }
        // saves the selected simulation's id
        selectedSimulationID = newSelectedSimulationID;

        // Apply the InnerShadow effect to the newSelected node
        VBox newSelected = executionsVBoxList.get(newSelectedSimulationID - 1);
        newSelected.setStyle("-fx-background-color: #7affd9; -fx-border-color: #9d3737; -fx-border-width: 1px;");

        rightVBox.setVisible(true);
        selectedSimulationLabel.setVisible(true);

        if(dataFetchingThread != null) {
            dataFetch.stopThread();
            try {
                dataFetchingThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isFinishedLoaded = false;
        dataFetch = new DataFetch(this);
        dataFetchingThread = new Thread(dataFetch);
        dataFetchingThread.start();
    }


    // setters:
    public void setExecutionVbox() {
        executionVbox.getChildren().clear();
        executionsVBoxList = new ArrayList<>();
    }
    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }
    public void setSimulationExecutionManager(SimulationExecutionManager simulationExecutionManager) {
        this.simulationExecutionManager = simulationExecutionManager;
    }

    // getters:
    public SimulationExecutionManager getSimulationExecutionManager() {
        return simulationExecutionManager;
    }
    public SimulationDetailsController getSimulationDetailsController() {
        return simulationDetailsComponentController;
    }

    public Label getSelectedSimulationLabel() {
        return selectedSimulationLabel;
    }
    public Button getRerunButton() {
        return rerunButton;
    }
    public int getSelectedSimulationID() {
        return selectedSimulationID;
    }

    public void addNewExecution(SimulationDetailsDTO simulationDetailsDTO) {
        Label executionDateLabel = new Label(simulationDetailsDTO.getTimeFormat());
        Button executionIdButton = new Button("Simulation #" + simulationDetailsDTO.getId());
        executionIdButton.setOnAction(event -> handleExecutionButtonClick(simulationDetailsDTO.getId()));
        VBox executionVboxToAdd = new VBox(executionIdButton, executionDateLabel);
        executionVboxToAdd.setAlignment(Pos.CENTER);
        executionVboxToAdd.setStyle("-fx-border-color: #9d3737; -fx-border-width: 1px;");
        executionVboxToAdd.setSpacing(3);
        executionVbox.getChildren().add(executionVboxToAdd);
        executionsVBoxList.add(executionVboxToAdd);
    }

    public void setSelectedSimulationResults(SimulationDetailsDTO selectedSimulationDTO) {
        if(!(selectedSimulationDTO.isFinished())) {
            simulationResultComponent.getChildren().clear();
            loadWaitingScreenForFinishedSimulation();
            isFinishedLoaded = false;
        }
        else if (!isFinishedLoaded){
            simulationResultComponent.getChildren().clear();
            loadResultScreen(selectedSimulationDTO);
            isFinishedLoaded = true;
        }
    }

    public void loadResultScreen(SimulationDetailsDTO selectedSimulationDTO){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("execution/executionResult/SimulationResult.fxml"));
        try {
            Node simulationResult = loader.load();
            SimulationResultController simulationResultController = loader.getController();
            simulationResultController.setSimulationDetailsDTO(selectedSimulationDTO);
            simulationResultController.setSimulationResultScreen();
            simulationResultComponent.getChildren().add(simulationResult);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadWaitingScreenForFinishedSimulation() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("execution/executionResult/waitingToFinish/WaitingToFinish.fxml"));
        try {
            Node waitingToFinishScreen = loader.load();
            simulationResultComponent.getChildren().add(waitingToFinishScreen);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
