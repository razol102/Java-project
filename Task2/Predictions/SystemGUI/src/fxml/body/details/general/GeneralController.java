package fxml.body.details.general;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GeneralController {

    @FXML
    private Label gridLabel;

    @FXML
    private Label secondsTermination;

    @FXML
    private Label ticksTermination;

    @FXML
    private Label userTermination;

    public void setData(String grid, String seconds, String ticks, String user) {
        gridLabel.setText(grid);
        secondsTermination.setText(seconds);
        ticksTermination.setText(ticks);
        userTermination.setText(user);
    }
}