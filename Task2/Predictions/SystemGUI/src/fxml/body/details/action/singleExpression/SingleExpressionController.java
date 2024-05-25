package fxml.body.details.action.singleExpression;

import DTO.definition.actionDTO.types.singleExpression.SingleExpressionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleExpressionController {

    @FXML
    private Label byLabel;

    @FXML
    private Label propertyLabel;

    public void setData(SingleExpressionDTO singleExpressionDTO) {
        byLabel.setText(singleExpressionDTO.getBy());
        propertyLabel.setText(singleExpressionDTO.getProperty());
    }
}
