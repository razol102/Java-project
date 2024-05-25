package fxml.body.details.action.set;

import DTO.definition.actionDTO.types.singleExpression.SingleExpressionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SetController {

    @FXML
    private Label propertyLabel;

    @FXML
    private Label valueLabel;

    public void setData(SingleExpressionDTO singleExpressionDTO) {
        propertyLabel.setText(singleExpressionDTO.getProperty());
        valueLabel.setText(singleExpressionDTO.getBy());
    }
}