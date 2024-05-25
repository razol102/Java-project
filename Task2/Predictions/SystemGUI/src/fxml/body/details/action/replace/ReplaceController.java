package fxml.body.details.action.replace;

import DTO.definition.actionDTO.types.others.ReplaceDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReplaceController {

    @FXML
    private Label createLabel;

    @FXML
    private Label modeLabel;

    public void setData(ReplaceDTO replaceDTO) {
        createLabel.setText(replaceDTO.getCreate());
        modeLabel.setText(replaceDTO.getMode());
    }
}
