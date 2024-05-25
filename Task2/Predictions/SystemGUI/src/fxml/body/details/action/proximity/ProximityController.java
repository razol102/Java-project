package fxml.body.details.action.proximity;

import DTO.definition.actionDTO.types.others.ProximityDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProximityController {
    @FXML
    private Label depthLabel;

    @FXML
    private Label numOfActionsLabel;

    @FXML
    private Label targetLabel;

    public void setData(ProximityDTO proximityDTO) {
        depthLabel.setText(proximityDTO.getDepth());
        numOfActionsLabel.setText(proximityDTO.getNumOfActions());
        targetLabel.setText(proximityDTO.getTargetEntity());
    }
}
