package fxml.body.results.execution.executionDetails;

import DTO.simulated.SimulatedEntityDTO;
import DTO.simulated.SimulationDetailsDTO;
import fxml.body.results.ResultsController;
import fxml.body.results.StatusEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

public class SimulationDetailsController {
    @FXML
    private ResultsController resultsController;

    @FXML
    private Button resumeButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;

    @FXML
    private Label ticksPassedLabel;
    @FXML
    private Label secondsPassedLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private AnchorPane detailsList;

    private TableView<SimulatedEntityDTO> populationTableView;


    public void setSimulationDetailsScreen() {
        resetButtons();
    }
    public void resetButtons() {
        resumeButton.setDisable(true);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
    }

    // setters:
    public void setSelectedSimulationDetails(SimulationDetailsDTO selectedSimulationDTO) {
        setButtons(selectedSimulationDTO);
        setLabels(selectedSimulationDTO);
        setTableView(selectedSimulationDTO);
    }
    public void setButtons(SimulationDetailsDTO selectedSimulationDTO){
        stopButton.setDisable(selectedSimulationDTO.isFinished() || isInQueue(selectedSimulationDTO));
        pauseButton.setDisable(!(isRunning(selectedSimulationDTO)));
        resumeButton.setDisable(!(isPaused(selectedSimulationDTO)));
        resultsController.getRerunButton().setDisable(!(selectedSimulationDTO.isFinished()));
    }
    public void setLabels(SimulationDetailsDTO selectedSimulationDTO){
        resultsController.getSelectedSimulationLabel().setText("Simulation #" + selectedSimulationDTO.getId());
        statusLabel.setText(selectedSimulationDTO.getStatus());
        ticksPassedLabel.setText(Integer.toString(selectedSimulationDTO.getCurrentTicks()));
        secondsPassedLabel.setText(Long.toString(selectedSimulationDTO.getCurrentSeconds()));
    }
    public void setTableView(SimulationDetailsDTO selectedSimulationDTO) {
        if (detailsList.getChildren().contains(populationTableView))
            detailsList.getChildren().remove(populationTableView);
        populationTableView = new TableView<>();
        populationTableView.setStyle("-fx-min-width: 180px;");


        ObservableList<SimulatedEntityDTO> entities = FXCollections.observableArrayList(selectedSimulationDTO.getEntities().values());

        // Create columns
        TableColumn<SimulatedEntityDTO, String> entityNameColumn = new TableColumn<>("Entity name");
        TableColumn<SimulatedEntityDTO, String> populationColumn = new TableColumn<>("Population");

        // Set center-aligned cell factories for both columns
        setCenterAlignedCellFactory(entityNameColumn);
        setCenterAlignedCellFactory(populationColumn);

        // Set cell value factories for the columns
        entityNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEntityName()));
        populationColumn.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getCurrentPopulation())));

        // Add columns to the TableView
        populationTableView.getColumns().addAll(entityNameColumn, populationColumn);

        // Set the items of the TableView to your list of entities
        populationTableView.setItems(entities);

        // Set the column resize policy to CONSTRAINED_RESIZE_POLICY
        populationTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        detailsList.getChildren().add(populationTableView);

    }
    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }
    public void setCenterAlignedCellFactory(TableColumn<SimulatedEntityDTO, String> column) {
        column.setCellFactory(tc -> {
            TextFieldTableCell<SimulatedEntityDTO, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(javafx.geometry.Pos.CENTER);
            return cell;
        });
    }


    // buttons handlers
    @FXML
    void handlePauseButton(ActionEvent event) {
        resultsController.getSimulationExecutionManager().pauseSimulation(resultsController.getSelectedSimulationID());
    }
    @FXML
    void handleResumeButton(ActionEvent event) {
        resultsController.getSimulationExecutionManager().resumeSimulation(resultsController.getSelectedSimulationID());
    }
    @FXML
    void handleStopButton(ActionEvent event) {
        resultsController.getSimulationExecutionManager().stopSimulation(resultsController.getSelectedSimulationID());
    }

    public boolean isInQueue(SimulationDetailsDTO selectedSimulationDTO){
        return selectedSimulationDTO.getStatus().equalsIgnoreCase(StatusEnum.IN_QUEUE.getStatusString());

    }

    public boolean isPaused(SimulationDetailsDTO selectedSimulationDTO){
        return selectedSimulationDTO.getStatus().equalsIgnoreCase(StatusEnum.PAUSED.getStatusString());
    }
    public boolean isRunning(SimulationDetailsDTO selectedSimulationDTO) {
        return selectedSimulationDTO.getStatus().equalsIgnoreCase(StatusEnum.RUNNING.getStatusString());
    }

}
