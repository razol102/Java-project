package fxml.body.newExecution.input;

import DTO.definition.EnvironmentDTO;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnvironmentInput {
    private Label name;
    private Label type;
    private Label range;
    private TextField inputField;
    private ChoiceBox<String> booleanChoice;
    private Button submitButton;
    private Label currentValue;

    public EnvironmentInput(EnvironmentDTO environmentDTO) {
        name = new Label(environmentDTO.getName());
        name.setStyle("-fx-font-weight: bold;");
        type = new Label(environmentDTO.getType());
        range = new Label(environmentDTO.getRange());
        booleanChoice = new ChoiceBox<>();
        inputField = new TextField("");
        inputField.setPromptText("Set value");
        if(environmentDTO.getType().equalsIgnoreCase("boolean")) {
            booleanChoice.getItems().addAll("Random", "True", "False");
            booleanChoice.setValue("Random");
        }

        submitButton = new Button("Submit");
        currentValue = new Label("Current value: Randomized");
        currentValue.setStyle("-fx-font-weight: bold;");
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public ChoiceBox<String> getBooleanChoice() {
        return booleanChoice;
    }
    public TextField getInputField() {
        return inputField;
    }

    public Label getName() {
        return name;
    }
    public Label getRange() {
        return range;
    }
    public Label getType() {
        return type;
    }

    public void setInputField(String value) {
        inputField.setText(value);
    }

    public Label getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Object value) {
        currentValue.setText("Current value: " + value);
        currentValue.setStyle("-fx-font-weight: bold;");
    }

    public void setBooleanChoice(String booleanChoice) {
        this.booleanChoice.setValue(booleanChoice);
    }
}
