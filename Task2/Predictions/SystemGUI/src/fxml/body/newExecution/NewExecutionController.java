package fxml.body.newExecution;

import DTO.definition.EntityDTO;
import DTO.definition.EnvironmentDTO;
import DTO.definition.WorldDTO;
import DTO.simulated.ActiveEnvironmentDTO;
import DTO.simulated.ActiveEnvironmentsDTO;
import DTO.simulated.SimulatedEntityDTO;
import DTO.simulated.SimulationDetailsDTO;
import fxml.body.BodyController;
import fxml.body.newExecution.input.EnvironmentInput;
import fxml.body.newExecution.input.PopulationInput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simulation.impl.SimulationExecutionManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NewExecutionController {

    @FXML
    private BodyController bodyController;

    @FXML
    private HBox newExecutionComponent;

    @FXML
    private VBox populationInputVbox;

    @FXML
    private VBox environmentsInputVbox;

    @FXML
    private Button clearButton;

    @FXML
    private Button startButton;

    @FXML
    private Label worldPopulationLeftLabel;

    private int worldPopulationLeft;
    private int worldPopulationCapacity;

    private SimulationExecutionManager simulationExecutionManager;
    private Map<String, PopulationInput> populationInputs;
    private Map<String, EnvironmentInput> environmentInputs;
    private Map<String, Object> environmentsValuesInput;

    @FXML
    public void initialize(){
        clearButton.setDisable(true);
        startButton.setDisable(true);
    }

    public void setNewExecutionScreen() {
        WorldDTO worldDTO = simulationExecutionManager.getWorldDTO();
        setPopulationInputVbox(worldDTO.getEntites().values());
        setEnvironmentsInputVbox(worldDTO.getEnvironments().values());
        worldPopulationLeft = worldPopulationCapacity = worldDTO.getWorldPopulation();
        worldPopulationLeftLabel.setText(worldPopulationLeft + " population can be added");
        worldPopulationLeftLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #37609d; -fx-underline: true;");
        clearButton.setDisable(false);
        startButton.setDisable(false);
    }

    public void setPopulationInputVbox(Collection<EntityDTO> entityDTOS) {

        populationInputVbox.getChildren().clear();
        populationInputs = new HashMap<>();

        for (EntityDTO entityDTO : entityDTOS) {
            PopulationInput toAdd = new PopulationInput(entityDTO.getName());
            // inserts an action to the TextField
            toAdd.getInputField().setOnAction(event -> {
                handlePopulationInput(toAdd);
            });
            HBox submitField = new HBox(toAdd.getInputField(), toAdd.getSubmitButton());
            submitField.setAlignment(Pos.TOP_CENTER);
            VBox singlePopulation = new VBox(toAdd.getEntityName(), submitField, toAdd.getCurrentPopulationLabel());
            singlePopulation.setPadding(new Insets(2));
            singlePopulation.setAlignment(Pos.CENTER);
            singlePopulation.setPadding(new Insets(5));
            singlePopulation.setStyle("-fx-border-color: #9d3737; -fx-border-width: 1px;");
            populationInputVbox.getChildren().add(singlePopulation);
            populationInputVbox.setAlignment(Pos.TOP_CENTER);
            populationInputVbox.setSpacing(3);
            populationInputs.put(entityDTO.getName(), toAdd);
            toAdd.getSubmitButton().setOnAction(event -> {
                handlePopulationInput(toAdd);
            });
        }
    }

    public void setEnvironmentsInputVbox(Collection<EnvironmentDTO> environmentDTOS) {
        environmentsInputVbox.getChildren().clear();
        environmentsValuesInput = new HashMap<>();
        environmentInputs = new HashMap<>();
        for (EnvironmentDTO environmentDTO : environmentDTOS) {
            environmentsValuesInput.put(environmentDTO.getName(), null);
            EnvironmentInput toAdd = new EnvironmentInput(environmentDTO);
            VBox environmentVBox;
            if (toAdd.getType().getText().equalsIgnoreCase("Boolean")) {
                environmentVBox = new VBox(toAdd.getName(), toAdd.getType(), toAdd.getBooleanChoice());
                toAdd.getBooleanChoice().setOnAction(event -> {
                    environmentsValuesInput.put(toAdd.getName().getText(), toAdd.getBooleanChoice().getValue());
                });
            }
            else {
                HBox inputFieldAndButton = new HBox(toAdd.getInputField(), toAdd.getSubmitButton());
                inputFieldAndButton.setAlignment(Pos.CENTER);
                if (toAdd.getType().getText().equalsIgnoreCase("String"))
                    environmentVBox = new VBox(toAdd.getName(), toAdd.getType(), inputFieldAndButton, toAdd.getCurrentValue());
                else
                    environmentVBox = new VBox(toAdd.getName(), toAdd.getType(), toAdd.getRange(), inputFieldAndButton, toAdd.getCurrentValue());
                // inserts an action to the TextField
                toAdd.getSubmitButton().setOnAction(event -> {
                    handleEnvironmentInput(toAdd);
                });
            }
            environmentVBox.setPadding(new Insets(5));
            environmentVBox.setStyle("-fx-border-color: #9d3737; -fx-border-width: 1px;");
            environmentVBox.setAlignment(Pos.CENTER);
            environmentsInputVbox.getChildren().add(environmentVBox);
            environmentsInputVbox.setSpacing(3);
            environmentInputs.put(environmentDTO.getName(), toAdd);
        }
    }

    public void setSimulationExecutionManager(SimulationExecutionManager simulationExecutionManager) {
        this.simulationExecutionManager = simulationExecutionManager;
    }

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }

    public void handlePopulationInput(PopulationInput populationInput) {
        try {
            String entityName = populationInput.getEntityName().getText();
            String userPopulationInput = populationInput.getInputField().getText();
            int newPopulation = Integer.parseInt(userPopulationInput);
            int prevPopulation = populationInput.getCurrentPopulation();
            int prevWorldPopulation = worldPopulationLeft;  // saves population before updating

            worldPopulationLeft = worldPopulationLeft + prevPopulation - newPopulation;
            if(worldPopulationLeft < 0) {
                worldPopulationLeft = prevWorldPopulation;
                throw new IllegalArgumentException("You have exceeded the population capacity" +
                        "\n You have " + worldPopulationLeft + " left to add");
            }
            worldPopulationLeftLabel.setText(worldPopulationLeft + " population can be added");

            populationInput.setCurrentPopulation(userPopulationInput);
            populationInput.getInputField().setStyle("-fx-border-color: #22ff00; -fx-border-width: 1px;");
            simulationExecutionManager.setEntityPopulation(entityName, newPopulation);

        } catch (NumberFormatException e) {
            showErrorBox(populationInput, "Population input must be an integer!");
        } catch (IllegalArgumentException e) {
            showErrorBox(populationInput, e.getMessage());
        }
    }
    public void showErrorBox(PopulationInput populationInput, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong input");
        alert.setContentText(text);
        alert.show();
        populationInput.getInputField().clear();
        populationInput.getInputField().setStyle("-fx-border-color: #ff0000; -fx-border-width: 1px;");
    }

    public void handleEnvironmentInput(EnvironmentInput environmentInput) {
       try {
           String environmentName = environmentInput.getName().getText();
           Object valueInput = getEnvValue(environmentInput);
           environmentsValuesInput.put(environmentName, valueInput);
           environmentInput.getCurrentValue().setText("Current value: " + environmentInput.getInputField().getText());
           environmentInput.getCurrentValue().setStyle("-fx-font-weight: bold;");

           environmentInput.getInputField().setStyle("-fx-border-color: #22ff00; -fx-border-width: 1px;");
       } catch (NumberFormatException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Wrong input");
           alert.setContentText("Invalid input! " + environmentInput.getName().getText() + "'s input must be numeric");
           alert.show();
           environmentInput.getInputField().clear();
           environmentInput.getInputField().setStyle("-fx-border-color: #ff0000; -fx-border-width: 1px;");
       } catch(IllegalArgumentException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Wrong input");
           alert.setContentText(e.getMessage());
           alert.show();
           environmentInput.getInputField().clear();
           environmentInput.getInputField().setStyle("-fx-border-color: #ff0000; -fx-border-width: 1px;");
       }
    }
    @FXML
    public void handleClearButton(ActionEvent event) {
        // population input clear
        worldPopulationLeft = worldPopulationCapacity;
        worldPopulationLeftLabel.setText(worldPopulationLeft + " population can be added");
        populationInputs.values().forEach(populationInput -> {
            populationInput.getInputField().clear();
            populationInput.getInputField().setStyle("");
            populationInput.setCurrentPopulation("0");
        });

        // environments input clear
        environmentInputs.forEach((key, environmentInput) -> {
            environmentInput.getInputField().clear();
            environmentInput.getBooleanChoice().setValue("Random");
            environmentInput.setCurrentValue("Randomized");
            environmentInput.getInputField().setStyle("");

        });
        environmentsValuesInput.replaceAll((key, value) -> null);
    }

    @FXML
    public void handleStartButton(ActionEvent event) {
        Map<String, Integer> newSimulationPopulations = populationInputs.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getCurrentPopulation()
                ));
        // creates simulation before run
        simulationExecutionManager.createNewSimulation(newSimulationPopulations, environmentsValuesInput);
        SimulationDetailsDTO simulationDTOToExecute = simulationExecutionManager.getSimulationsDTO().getLastSimulatedDTO();
        bodyController.getResultComponentController().addNewExecution(simulationDTOToExecute);
        simulationExecutionManager.runSimulation(simulationDTOToExecute);

        clearButton.fire();
        bodyController.getTabPane().getSelectionModel().select(bodyController.getTabResults());

    }
    public Object getEnvValue(EnvironmentInput environmentInput) {
        String environmentName = environmentInput.getName().getText();
        String userEnvInput = environmentInput.getInputField().getText();
        EnvironmentDTO environmentDTO = simulationExecutionManager.getWorldDTO().getEnvironments().get(environmentName);
        switch (environmentInput.getType().getText().toUpperCase()) {
            case "DECIMAL":
                Integer integerValue = Integer.parseInt(userEnvInput);
                if (integerValue < environmentDTO.getFrom() || integerValue > environmentDTO.getTo())
                    throw new IllegalArgumentException("The value you entered for " + environmentName + " is out of range!");
                else
                    return integerValue;

            case "FLOAT":
                Float floatValue = Float.parseFloat(userEnvInput);
                if (floatValue < environmentDTO.getFrom() || floatValue > environmentDTO.getTo())
                    throw new IllegalArgumentException("The value you entered for " + environmentName + " is out of range!");
                else
                    return floatValue;

            case "BOOLEAN":
                Boolean boolValue = Boolean.parseBoolean(userEnvInput);
                return boolValue;

            case "STRING":
                return userEnvInput;
        }
        return null;
    }

    public void setRerun(SimulationDetailsDTO simulationDetailsDTO){
        setRerunPopulations(simulationDetailsDTO.getEntities());
        setRerunEnvironments(simulationDetailsDTO.getActiveEnvironmentsDTO());
    }

    public void setRerunPopulations(Map<String, SimulatedEntityDTO> simulatedEntityDTOS) {
        worldPopulationLeft = worldPopulationCapacity;
        for(SimulatedEntityDTO simulatedEntityDTO: simulatedEntityDTOS.values()) {
            PopulationInput populationInput = populationInputs.get(simulatedEntityDTO.getEntityName());
            populationInput.setCurrentPopulation(Integer.toString(simulatedEntityDTO.getInitPopulation()));
            populationInput.getInputField().setStyle("-fx-border-color: #22ff00; -fx-border-width: 1px;");
            simulationExecutionManager.setRerunPopulation(simulatedEntityDTO);
            worldPopulationLeft -= simulatedEntityDTO.getInitPopulation();
        }
        worldPopulationLeftLabel.setText(worldPopulationLeft + " population can be added");
    }

    public void setRerunEnvironments(ActiveEnvironmentsDTO activeEnvironmentsDTO) {
        for(Map.Entry<String, ActiveEnvironmentDTO> environmentDTOEntry: activeEnvironmentsDTO.getActiveEnvironments().entrySet()) {
            EnvironmentInput environmentInput = environmentInputs.get(environmentDTOEntry.getKey());
            if (environmentDTOEntry.getValue().getType().equalsIgnoreCase("Boolean"))
                environmentInput.setBooleanChoice(environmentDTOEntry.getValue().getValue().toString());
            else
                environmentInput.setCurrentValue(environmentDTOEntry.getValue().getValue());
            environmentInputs.get(environmentDTOEntry.getKey()).getInputField().setStyle("-fx-border-color: #22ff00; -fx-border-width: 1px;");
            environmentsValuesInput.put(environmentDTOEntry.getKey(), convertValue(environmentDTOEntry.getValue()));
        }
    }

    public Object convertValue(ActiveEnvironmentDTO environmentDTO){
        switch (environmentDTO.getType()) {
            case "Integer":
                return Integer.parseInt(environmentDTO.getValue());
            case "Float":
                return Float.parseFloat(environmentDTO.getValue());
            case "Boolean":
                return Boolean.parseBoolean(environmentDTO.getValue());
            case "String":
                return environmentDTO.getValue();
        }
        return null;
    }
}
