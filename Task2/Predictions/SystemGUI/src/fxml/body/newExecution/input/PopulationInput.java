package fxml.body.newExecution.input;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PopulationInput {
    private Label entityName;
    private Label currentPopulationLabel;
    private TextField inputField;
    private Button submitButton;
    private int currentPopulation = 0;

    public PopulationInput(String entityName) {
        this.entityName = new Label(entityName);
        this.entityName.setStyle("-fx-font-weight: bold;");
        inputField = new TextField();
        inputField.setPromptText("Enter amount");
        inputField.setPrefWidth(130);
        submitButton = new Button("Submit");
        currentPopulationLabel = new Label("Current population: 0");
        currentPopulationLabel.setStyle("-fx-font-weight: bold;");

    }
    public Button getSubmitButton() {
        return submitButton;
    }

    public Label getEntityName() {
        return entityName;
    }

    public TextField getInputField() {
        return inputField;
    }

    public int getCurrentPopulation() {
        return currentPopulation;
    }

    public Label getCurrentPopulationLabel() {
        return currentPopulationLabel;
    }

    public void setTextField(String populationAmount) {
        inputField.setText(populationAmount);
    }

    public void setCurrentPopulation(String populationInput) {
        currentPopulation = Integer.parseInt(populationInput);
        currentPopulationLabel.setText("Current population: " + populationInput);
        currentPopulationLabel.setStyle("-fx-font-weight: bold;");

    }

}
