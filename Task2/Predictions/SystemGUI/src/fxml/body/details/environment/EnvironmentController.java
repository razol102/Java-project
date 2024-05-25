package fxml.body.details.environment;

import DTO.definition.EnvironmentDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EnvironmentController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label rangeLabel;

    @FXML
    private Label typeLabel;


    public void setData(EnvironmentDTO environmentDTO) {
        nameLabel.setText(environmentDTO.getName());
        typeLabel.setText(environmentDTO.getType());
        rangeLabel.setText(environmentDTO.getRange());
    }
}
