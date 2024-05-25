package fxml.body.details.action;

import DTO.definition.actionDTO.ActionDTO;
import DTO.definition.actionDTO.types.calculation.CalculationDTO;
import DTO.definition.actionDTO.types.condition.MultipleConditionDTO;
import DTO.definition.actionDTO.types.condition.SingleConditionDTO;
import DTO.definition.actionDTO.types.others.ProximityDTO;
import DTO.definition.actionDTO.types.others.ReplaceDTO;
import DTO.definition.actionDTO.types.singleExpression.SingleExpressionDTO;
import fxml.body.details.action.calculation.CalculationController;
import fxml.body.details.action.condition.multiple.MultipleConditionController;
import fxml.body.details.action.condition.single.SingleConditionController;
import fxml.body.details.action.proximity.ProximityController;
import fxml.body.details.action.replace.ReplaceController;
import fxml.body.details.action.set.SetController;
import fxml.body.details.action.singleExpression.SingleExpressionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ActionController {

    @FXML
    private HBox actionHbox;

    @FXML
    private Label mainEntityLabel;
    @FXML
    private Label secondaryEntity;

    @FXML
    private Label secondaryLabel;

    @FXML
    private Label typeLabel;

    public void setData(ActionDTO actionDTO) {
        typeLabel.setText(actionDTO.getActionType());
        mainEntityLabel.setText(actionDTO.getMainEntity());
        if(actionDTO.getSecondaryEntity() != null) {
            secondaryLabel.setText(actionDTO.getSecondaryEntity());
            secondaryEntity.setVisible(true);
            secondaryLabel.setVisible(true);
        }
        else {
            secondaryEntity.setVisible(false);
            secondaryLabel.setVisible(false);
        }
        setAdditionData(actionDTO);
    }

    public void setAdditionData(ActionDTO actionDTO) {
        String actionType = actionDTO.getActionType();
        switch(actionType.toUpperCase()) {
            case "INCREASE": case "DECREASE":
                loadSingleExpression((SingleExpressionDTO) actionDTO);
                break;
            case "SET":
                loadSet((SingleExpressionDTO) actionDTO);
                break;
            case "KILL":
                // nothing to do
                break;

            case "CALCULATION":
                loadCalculation((CalculationDTO) actionDTO);
                break;

            case "SINGLE CONDITION":
                loadSingleCondition((SingleConditionDTO) actionDTO);
                break;

            case "CONDITION":
                loadMultipleCondition((MultipleConditionDTO) actionDTO);
                break;

            case "REPLACE":
                loadReplace((ReplaceDTO) actionDTO);
                break;

            case "PROXIMITY":
                loadProximity((ProximityDTO) actionDTO);
                break;
        }
    }

    public void loadSingleExpression(SingleExpressionDTO SingleExpressionDTO) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("singleExpression/SingleExpression.fxml"));
        try {
            Node singleExpressionDetails = loader.load();
            SingleExpressionController singleExpressionController = loader.getController();
            singleExpressionController.setData(SingleExpressionDTO);
            actionHbox.getChildren().clear();
            actionHbox.getChildren().add(singleExpressionDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSet(SingleExpressionDTO SingleExpressionDTO) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("set/Set.fxml"));
        try {
            Node setDetails = loader.load();
            SetController setController = loader.getController();
            setController.setData(SingleExpressionDTO);
            actionHbox.getChildren().clear();
            actionHbox.getChildren().add(setDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCalculation(CalculationDTO calculationDTO) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("calculation/Calculation.fxml"));
        try {
            Node calculationDetails = loader.load();
            CalculationController calculationController = loader.getController();
            calculationController.setData(calculationDTO);
            actionHbox.getChildren().clear();
            actionHbox.getChildren().add(calculationDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSingleCondition(SingleConditionDTO singleConditionDTO) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("condition/single/SingleCondition.fxml"));
        try {
            Node singleConditionDetails = loader.load();
            SingleConditionController singleConditionController = loader.getController();
            singleConditionController.setData(singleConditionDTO);
            actionHbox.getChildren().clear();
            actionHbox.getChildren().add(singleConditionDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMultipleCondition(MultipleConditionDTO multipleConditionDTO) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("condition/multiple/MultipleCondition.fxml"));
        try {
            Node multipleConditionDetails = loader.load();
            MultipleConditionController multipleConditionController = loader.getController();
            multipleConditionController.setData(multipleConditionDTO);
            actionHbox.getChildren().clear();
            actionHbox.getChildren().add(multipleConditionDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadReplace(ReplaceDTO replaceDTO) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("replace/Replace.fxml"));
        try {
            Node replaceDetails = loader.load();
            ReplaceController replaceController = loader.getController();
            replaceController.setData(replaceDTO);
            actionHbox.getChildren().clear();
            actionHbox.getChildren().add(replaceDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadProximity(ProximityDTO proximityDTO) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("proximity/Proximity.fxml"));
        try {
            Node proximityDetails = loader.load();
            ProximityController proximityController = loader.getController();
            proximityController.setData(proximityDTO);
            actionHbox.getChildren().clear();
            actionHbox.getChildren().add(proximityDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}