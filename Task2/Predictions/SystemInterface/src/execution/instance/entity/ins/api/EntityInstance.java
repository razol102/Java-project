package execution.instance.entity.ins.api;

import execution.instance.entity.ins.impl.Coordinate;
import execution.instance.property.api.PropertyInstance;

import java.util.Map;

public interface EntityInstance {
    PropertyInstance getPropertyByName(String name);

    int getId();

    boolean isPropertyExist(String name);

    Map<String, PropertyInstance> getProperties();

    void addPropertyInstance(PropertyInstance propertyInstance);

    Coordinate getCoordinate();
    void setCoordinate(Coordinate coordinate);

    void killMe();

    boolean checkLife();

    void changeCoordinate(Coordinate[][] grid);
}
