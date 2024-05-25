package fxml.body.details.activation;

import DTO.definition.RuleDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ActivationController {

    @FXML
    private Label ruleLabel;

    @FXML
    private Label probabilityLabel;

    @FXML
    private Label ticksLabel;

    public void setData(RuleDTO ruleDTO) {
        ruleLabel.setText(ruleDTO.getName() + "'s activation:");
        if (ruleDTO.getTicks() == 1)
            ticksLabel.setText("Every single tick");
        else
            ticksLabel.setText("Every " + ruleDTO.getTicks() + " ticks");
        probabilityLabel.setText("In probability of " + ruleDTO.getProbability());
    }
}


