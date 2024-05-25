package fxml.body.details;

import DTO.definition.*;
import DTO.definition.actionDTO.ActionDTO;
import fxml.body.BodyController;
import fxml.body.details.action.ActionController;
import fxml.body.details.activation.ActivationController;
import fxml.body.details.environment.EnvironmentController;
import fxml.body.details.general.GeneralController;
import fxml.body.details.property.PropertyController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Map;


public class DetailsController {

    private WorldDTO worldDTO;
    @FXML
    private BodyController bodyController;

    @FXML
    private TreeView<String> worldDetailsTree;

    @FXML
    private VBox detailsVbox;

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }
    public void setDetailsTree(WorldDTO worldDTO) {
        TreeItem<String> rootWorld = new TreeItem<>("World");
        worldDetailsTree.setRoot(rootWorld);
        this.worldDTO = worldDTO;
        worldDetailsTree.setVisible(false);
        worldDetailsTree.getRoot().getChildren().clear();
        worldDetailsTree.getStyleClass().add("treeView_vbox");
        // Create root items
        TreeItem<String> entitiesNode = new TreeItem<>("Entities");
        TreeItem<String> environmentsNode = new TreeItem<>("Environments");
        TreeItem<String> rulesNode = new TreeItem<>("Rules");
        TreeItem<String> generalNode = new TreeItem<>("General");

        // entities + properties
        for (EntityDTO entityDTO: worldDTO.getEntites().values()) {
            TreeItem<String> entityNode = new TreeItem<>(entityDTO.getName());
            // properties
            for(Map.Entry<String,PropertyDTO> propertyDTO: entityDTO.getProperties().entrySet()) {
                TreeItem<String> propertyNode = new TreeItem<>(propertyDTO.getKey());
                entityNode.getChildren().add(propertyNode);
            }
            entitiesNode.getChildren().add(entityNode);
        }

        // environments
        for (Map.Entry<String, EnvironmentDTO> environmentDTO: worldDTO.getEnvironments().entrySet()){
            TreeItem<String> environmentNode = new TreeItem<>(environmentDTO.getKey());
            environmentsNode.getChildren().add(environmentNode);
        }

        // rules
        for(RuleDTO ruleDTO: worldDTO.getRules().values()) {
            TreeItem<String> ruleNode = new TreeItem<>(ruleDTO.getName());
            TreeItem<String> activationNode = new TreeItem<>("Activation");
            TreeItem<String> actionsNode = new TreeItem<>("Actions");
            // actions
            for (ActionDTO actionDTO : ruleDTO.getActions()) {
                TreeItem<String> actionNode = new TreeItem<>(actionDTO.getActionType());
                actionsNode.getChildren().add(actionNode);
            }
            ruleNode.getChildren().addAll(activationNode, actionsNode);
            rulesNode.getChildren().add(ruleNode);
        }

        worldDetailsTree.getRoot().getChildren().addAll(entitiesNode, environmentsNode, rulesNode, generalNode);
        worldDetailsTree.setVisible(true);
    }
    @FXML
    public void selectNode(MouseEvent event) {
        TreeItem<String> selectedItem = worldDetailsTree.getSelectionModel().getSelectedItem();
        if (selectedItem != null)
            showNodeData(selectedItem);
    }
    public void showNodeData(TreeItem<String> selectedItem) {
        if(selectedItem.getParent() == null)
            return;
        TreeItem<String> parent = selectedItem.getParent();
        TreeItem<String> ancient = parent.getParent();

        if(parent.getValue().equalsIgnoreCase("Environments"))
            loadEnvironmentDetails(selectedItem.getValue());
        else if(ancient != null) {
            if (parent.getParent().getValue().equalsIgnoreCase("Entities"))         // if the selected item is a property
                loadPropertyDetails(selectedItem);
            else if(selectedItem.getValue().equalsIgnoreCase("Activation"))
                loadActivationDetails(selectedItem);
        else if(parent.getValue().equalsIgnoreCase("Actions"))
            loadActionDetails(selectedItem);
        }
        else if(selectedItem.getValue().equalsIgnoreCase("General"))// General
            loadGeneralDetails();
        else
            detailsVbox.getChildren().clear();
    }
    public void loadEnvironmentDetails(String environmentName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("environment/Environment.fxml"));
        try {
            Node environmentDetails = loader.load();
            EnvironmentController environmentController = loader.getController();
            EnvironmentDTO environmentDTO = worldDTO.getEnvironments().get(environmentName);
            environmentController.setData(environmentDTO);
            detailsVbox.getChildren().clear();
            detailsVbox.getChildren().add(environmentDetails);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadPropertyDetails(TreeItem<String> selectedItem) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("property/Property.fxml"));
        try {
            Node propertyDetails = loader.load();
            PropertyController propertyController = loader.getController();
            String entity =  selectedItem.getParent().getValue();
            PropertyDTO propertyDTO = worldDTO.getEntites().get(entity).getProperties().get(selectedItem.getValue());
            propertyController.setData(propertyDTO);
            detailsVbox.getChildren().clear();
            detailsVbox.getChildren().add(propertyDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadActionDetails(TreeItem<String> selectedItem) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("action/Action.fxml"));
        try {
            Node actionDetails = loader.load();
            ActionController actionController = loader.getController();
            TreeItem<String> ruleItem = selectedItem.getParent().getParent();
            int actionIndex = getItemIndex(selectedItem);
            ActionDTO actionDTO = worldDTO.getRules().get(ruleItem.getValue()).getActions().get(actionIndex);
            actionController.setData(actionDTO);
            detailsVbox.getChildren().clear();
            detailsVbox.getChildren().add(actionDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadGeneralDetails() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("general/General.fxml"));
        try {
            Node generalDetails = loader.load();
            GeneralController generalController = loader.getController();
            String secondsTermination = "None", ticksTermination = "None", userTermination = "None";
            if(worldDTO.getTermination().getSeconds() != 0)
                secondsTermination = String.valueOf(worldDTO.getTermination().getSeconds());
            if(worldDTO.getTermination().getTicks() != 0)
                ticksTermination = String.valueOf(worldDTO.getTermination().getTicks());

            generalController.setData(worldDTO.getGrid().getGrid(), secondsTermination, ticksTermination, userTermination);
            detailsVbox.getChildren().clear();
            detailsVbox.getChildren().add(generalDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadActivationDetails(TreeItem<String> selectedItem) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("activation/Activation.fxml"));
        try {
            Node activationDetails = loader.load();
            ActivationController activationController = loader.getController();
            activationController.setData(worldDTO.getRules().get(selectedItem.getParent().getValue()));
            detailsVbox.getChildren().clear();
            detailsVbox.getChildren().add(activationDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int getItemIndex(TreeItem<String> selectedItem) {
        TreeItem<String> parent = selectedItem.getParent();
        int index = 0;

        for (TreeItem<String> child : parent.getChildren()) {
            if(child != selectedItem)
                index++;
            else
                break;
        }
        return index;
    }

}


