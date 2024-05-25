package fxml.body.details.action.calculation;

import DTO.definition.actionDTO.types.calculation.CalculationDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CalculationController {


    @FXML
    private Label calculationTypeLabel;

    @FXML
    private Label resultPropLabel;

    @FXML
    private Label arg1Label;

    @FXML
    private Label arg2Label;


    public void setData(CalculationDTO calculationDTO) {
        calculationTypeLabel.setText(calculationDTO.getCalculationType());
        resultPropLabel.setText(calculationDTO.getResultProp());
        arg1Label.setText(calculationDTO.getArg1());
        arg2Label.setText(calculationDTO.getArg2());
    }
}
