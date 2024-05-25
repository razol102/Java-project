package fxml.body.details.property;

import DTO.definition.PropertyDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PropertyController {


    @FXML
    private Label nameLabel;

    @FXML
    private Label rangeLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label isRandomLabel;

    public void setData(PropertyDTO propertyDTO) {
        nameLabel.setText(propertyDTO.getName());
        typeLabel.setText(propertyDTO.getType());
        rangeLabel.setText(propertyDTO.getRange());
        isRandomLabel.setText(propertyDTO.getIsRandom());
    }

}
