package fxml.body.details.action.condition.multiple;

import DTO.definition.actionDTO.types.condition.MultipleConditionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MultipleConditionController {

    @FXML
    private Label singularityLabel;

    @FXML
    private Label conditionsLabel;

    @FXML
    private Label logicLabel;

    @FXML
    private Label numOfThenLabel;

    @FXML
    private Label numOfElseLabel;

    public void setData(MultipleConditionDTO multipleConditionDTO) {
        singularityLabel.setText(multipleConditionDTO.getSingularity());
        logicLabel.setText(multipleConditionDTO.getLogical());
        conditionsLabel.setText(multipleConditionDTO.getNumOfConditions());
        numOfThenLabel.setText(multipleConditionDTO.getNumOfThen());
        numOfElseLabel.setText(multipleConditionDTO.getNumOfElse());
    }
}
