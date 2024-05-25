package execution.instance.entity.ins.impl;

import definition.entity.EntityDef;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.property.api.PropertyInstance;

import java.util.HashMap;
import java.util.Map;

public class EntityInstanceImpl implements EntityInstance {
    private final int id;
    private final EntityDef entityDef;
    private Map<String, PropertyInstance> properties;
    private Coordinate coordinate;
    private boolean isAlive = true;

    public EntityInstanceImpl(EntityDef entityDef, int id) {
        this.entityDef = entityDef;
        this.id = id;
        properties = new HashMap<>();
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public boolean isPropertyExist(String name){
        if(properties.containsKey(name))
            return true;
        return false;
    }

    @Override
    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        return properties.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        properties.put(propertyInstance.getPropertyDef().getName(), propertyInstance);
    }
    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }
    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    @Override
    public void killMe() {
        isAlive = false;
    }
    @Override
    public boolean checkLife() {
        return isAlive;
    }
    @Override
    public void changeCoordinate(Coordinate[][] grid){
        int[][] offsets = {
                {-1, -1}, // Upper-left
                {-1, 0},  // Above
                {-1, 1},  // Upper-right
                {0, -1},  // Left
                {0, 1},   // Right
                {1, -1},  // Lower-left
                {1, 0},   // Below
                {1, 1}    // Lower-right
        };

        int rows = grid.length; // Number of rows
        int columns = grid[0].length; // Number of columns (assuming all rows have the same number of columns)


        // Calculate and set the coordinates of neighbors
        for (int[] offset: offsets) {
            int neighborX = coordinate.getX() + offset[0];
            int neighborY = coordinate.getY() + offset[1];

            // Handle wrap-around if the neighbor coordinates are out of bounds
            if (neighborX < 0) {
                neighborX = rows - 1;
            } else if (neighborX >= rows) {
                neighborX = 0;
            }

            if (neighborY < 0) {
                neighborY = columns - 1;
            } else if (neighborY >= columns) {
                neighborY = 0;
            }

            if(!grid[neighborX][neighborY].isSet()) {
                coordinate.setSet(false);
                coordinate = grid[neighborX][neighborY];
                coordinate.setSet(true);
                return;
            }
        }
    }
}