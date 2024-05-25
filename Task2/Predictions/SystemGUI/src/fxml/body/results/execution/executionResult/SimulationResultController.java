package fxml.body.results.execution.executionResult;

import DTO.simulated.SimulatedEntityDTO;
import DTO.simulated.SimulatedEntityInstanceDTO;
import DTO.simulated.SimulatedPropertyDTO;
import DTO.simulated.SimulationDetailsDTO;
import fxml.body.results.ResultsController;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationResultController {
    @FXML
    private ResultsController resultsController;
    @FXML
    private VBox simulationResultVBox;

    @FXML
    private Label averageLabel;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label consistencyLabel;

    @FXML
    private TreeView<String> detailsTree;

    @FXML
    private LineChart<?, ?> lineChart;

    private SimulationDetailsDTO simulationDetailsDTO;

    public void setSimulationDetailsDTO(SimulationDetailsDTO simulationDetailsDTO) {
        this.simulationDetailsDTO = simulationDetailsDTO;
    }
    public void setSimulationResultScreen(){
        setDetailsTree();
        setLineChart();
        consistencyLabel.setVisible(false);
        averageLabel.setVisible(false);
    }
    // tree:
    public void setDetailsTree() {
        TreeItem<String> root = new TreeItem<>("World");
        detailsTree.setRoot(root);
        // Create root items
        for(SimulatedEntityDTO simulatedEntityDTO: simulationDetailsDTO.getEntities().values()) {
            TreeItem<String> entityNode = new TreeItem<>(simulatedEntityDTO.getEntityName());
            if(simulatedEntityDTO.getEntityInstances().isEmpty())
                detailsTree.getRoot().getChildren().add(new TreeItem<>(simulatedEntityDTO.getEntityName() + " has no entities"));
            else {
                for(SimulatedPropertyDTO simulatedPropertyDTO: simulatedEntityDTO.getEntityInstances().get(0).getSimulatedPropertyDTOList().values()) {
                    TreeItem<String> propertyNode = new TreeItem<>(simulatedPropertyDTO.getPropertyName());
                    entityNode.getChildren().add(propertyNode);
                }
                detailsTree.getRoot().getChildren().add(entityNode);
            }
        }
        detailsTree.setVisible(true);
    }
    @FXML
    public void selectNode(MouseEvent event) {
        TreeItem<String> selectedItem = detailsTree.getSelectionModel().getSelectedItem();
        if (selectedItem != null)
            showNodeData(selectedItem);
    }

    public void showNodeData(TreeItem<String> selectedItem) {
        String entityName = null;
        String propertyName = selectedItem.getValue();
        if(selectedItem.getParent() != null) {
            entityName = selectedItem.getParent().getValue();
            if (selectedItem.getParent().getParent() != null) {
                showHistogram(entityName, propertyName);
                showConsistency(entityName, propertyName);
                SimulatedPropertyDTO simulatedPropertyDTO = simulationDetailsDTO.getEntities().get(entityName).getEntityInstances().
                        get(0).getSimulatedPropertyDTOList().get(propertyName);
                if(simulatedPropertyDTO.getPropertyType().equalsIgnoreCase("Float") ||
                        simulatedPropertyDTO.getPropertyType().equalsIgnoreCase("Integer"))
                    showAverage(entityName, propertyName);
                else
                    averageLabel.setText(simulatedPropertyDTO.getPropertyName() + " is not numeric");
            }
        }
    }

    //line chart:

    public void setLineChart() {
        lineChart.getData().clear();

        lineChart.setTitle("Population amount per 1K ticks");

        String titleFontStyle = "-fx-font-family: 'Calibri'; -fx-font-size: 16px;";
        lineChart.lookup(".chart-title").setStyle(titleFontStyle);

        // Create X and Y axes for the chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Ticks");
        yAxis.setLabel("Population amount");

        // Set the axes to the chart
        lineChart.setCreateSymbols(true);
        lineChart.setLegendVisible(true);
        lineChart.setAnimated(true);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE); // Prevent automatic sorting

        int tick = simulationDetailsDTO.getTickStartGraph();

        // creating series for each type of entity.
        Map<String, XYChart.Series> entityNameToSeries = new HashMap<>();
        XYChart.Series seriesToAdd;
        for(Map.Entry<String, SimulatedEntityDTO> simulatedEntityDTOMap : simulationDetailsDTO.getEntities().entrySet()) {
            seriesToAdd = new XYChart.Series();
            seriesToAdd.setName(simulatedEntityDTOMap.getKey());
            entityNameToSeries.put(simulatedEntityDTOMap.getKey(), seriesToAdd);
        }

        // Loop through the list of maps
        for (Map<String, Integer> dataMap : simulationDetailsDTO.getPopulationPerTick()) {

            // Add data from the map to the series
            for (Map.Entry<String, Integer> entry : dataMap.entrySet())
                entityNameToSeries.get(entry.getKey()).getData().add(new XYChart.Data<>(Integer.toString(tick), entry.getValue()));

            tick++;
        }

        // Add the series to the chart
        for (XYChart.Series series : entityNameToSeries.values())
            lineChart.getData().add(series);

    }

    public void showConsistency(String entityName, String propertyName){
        SimulatedEntityDTO selectedSimulatedEntityDTO = simulationDetailsDTO.getEntities().get(entityName);
        int sumOfTicks = 0;
        int numOfEntities = selectedSimulatedEntityDTO.getEntityInstances().size();

        for(SimulatedEntityInstanceDTO simulatedEntityInstanceDTO: selectedSimulatedEntityDTO.getEntityInstances())
            sumOfTicks += simulatedEntityInstanceDTO.getSimulatedPropertyDTOList().get(propertyName).getTick();

        float consistency = sumOfTicks/numOfEntities;
        consistencyLabel.setText(String.valueOf(consistency));
        consistencyLabel.setVisible(true);
    }

    public void showAverage(String entityName, String propertyName){
        SimulatedEntityDTO selectedSimulatedEntityDTO = simulationDetailsDTO.getEntities().get(entityName);
        int sumOfValues = 0;
        int numOfEntities = selectedSimulatedEntityDTO.getEntityInstances().size();
        String type = selectedSimulatedEntityDTO.getEntityInstances().get(0).getSimulatedPropertyDTOList().get(propertyName).getPropertyType();
        for(SimulatedEntityInstanceDTO simulatedEntityInstanceDTO: selectedSimulatedEntityDTO.getEntityInstances()) {
            if (type.equalsIgnoreCase("Decimal"))
                sumOfValues += Integer.parseInt(simulatedEntityInstanceDTO.getSimulatedPropertyDTOList().get(propertyName).getValue().toString());
            else if (type.equalsIgnoreCase("Float"))
                sumOfValues += Float.parseFloat(simulatedEntityInstanceDTO.getSimulatedPropertyDTOList().get(propertyName).getValue().toString());
        }
        averageLabel.setText(String.valueOf(sumOfValues/numOfEntities));
        averageLabel.setVisible(true);
    }

    public void showHistogram(String entityName, String propertyName) {
        barChart.getData().clear();
        barChart.setTitle(entityName + " histogram for [" + propertyName + "] :");

        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        xAxis.setLabel("Values");
        yAxis.setLabel("Amount");

        XYChart.Series series = new XYChart.Series();
        series.setName("Values");

        List<SimulatedEntityInstanceDTO> entityInstanceDTOS = simulationDetailsDTO.getEntities().get(entityName).getEntityInstances();
        List<Object> values = new ArrayList<>();
        // gets object of chosen property from every property instance
        for(SimulatedEntityInstanceDTO simulatedEntityInstanceDTO: entityInstanceDTOS)
            values.add(simulatedEntityInstanceDTO.getSimulatedPropertyDTOList().get(propertyName).getValue());

        Map<String, Integer> histogramData = new HashMap<>();

        for (Object value : values)
            histogramData.put(value.toString(), histogramData.getOrDefault(value, 0) + 1);
        for(Map.Entry<String, Integer> histogramDataEntrySet: histogramData.entrySet())
            series.getData().add(new XYChart.Data(histogramDataEntrySet.getKey(), histogramDataEntrySet.getValue()));

        barChart.getData().add(series);
    }
}
