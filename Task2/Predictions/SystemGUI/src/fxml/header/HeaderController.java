package fxml.header;

import DTO.definition.WorldDTO;
import fxml.AppController;
import fxml.header.numbersFetch.NumbersFetch;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HeaderController {
    private AppController appController;

    @FXML
    private Label filePath;

    @FXML
    private Button loadButton;

    @FXML
    private Label finishedSimulationsLabel;

    @FXML
    private Label inQueueSimulationsLabel;

    @FXML
    private Label runningSimulationsLabel;

    private Thread simulationsNumbersUpdater = null;
    private NumbersFetch numberFetch;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }

    @FXML public void initialize() {
        resetHeaderLabels();
    }

    @FXML
    public void loadFileDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");
        Stage stage = (Stage) loadButton.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                appController.getSimulationExecutionManager().loadSystemDetails(selectedFile.getPath());
                filePath.setText(selectedFile.getAbsolutePath());
                filePath.setStyle("-fx-background-color: #22ff00;");
                appController.getSimulationExecutionManager().setNewSimulationExecutionDetailsList();
                WorldDTO worldDTO = appController.getSimulationExecutionManager().getWorldDTO();
                resetHeaderLabels();
                startSimulationsNumbersUpdater();
                appController.setBodyDetailsTree(worldDTO);
                appController.getBodyComponentController().getNewExecutionComponentController().setNewExecutionScreen();
                appController.getBodyComponentController().getResultComponentController().setResultsScreen();
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong file");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    public void resetHeaderLabels(){
        inQueueSimulationsLabel.setText("0");
        runningSimulationsLabel.setText("0");
        finishedSimulationsLabel.setText("0");
    }

    public void startSimulationsNumbersUpdater() {
        if(simulationsNumbersUpdater != null) {
            numberFetch.stopThread();
            try {
                simulationsNumbersUpdater.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        numberFetch = new NumbersFetch(this);
        simulationsNumbersUpdater = new Thread(numberFetch);
        simulationsNumbersUpdater.start();
    }
    public void setInQueueSimulations(int inQueueSimulations) {
        inQueueSimulationsLabel.setText(Integer.toString(inQueueSimulations));
    }

    public void setRunningSimulations(int runningSimulations) {
        runningSimulationsLabel.setText(Integer.toString(runningSimulations));
    }

    public void setFinishedSimulations(int finishedSimulations) {
        finishedSimulationsLabel.setText(Integer.toString(finishedSimulations));
    }
}