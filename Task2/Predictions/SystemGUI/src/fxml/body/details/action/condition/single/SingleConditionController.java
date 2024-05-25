package fxml.body.details.action.condition.single;

import DTO.definition.actionDTO.types.condition.SingleConditionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleConditionController {

    @FXML
    private Label singularityLabel;

    @FXML
    private Label elseLabel;

    @FXML
    private Label operatorLabel;

    @FXML
    private Label operandLabel;

    @FXML
    private Label thenLabel;

    @FXML
    private Label valueLabel;

    public void setData(SingleConditionDTO singleConditionDTO) {
        singularityLabel.setText(singleConditionDTO.getSingularity());
        operandLabel.setText(singleConditionDTO.getOperand());
        valueLabel.setText(singleConditionDTO.getValue());
        operatorLabel.setText(singleConditionDTO.getOperator());
        thenLabel.setText(singleConditionDTO.getNumOfThen());
        elseLabel.setText(singleConditionDTO.getNumOfElse());
    }
}
