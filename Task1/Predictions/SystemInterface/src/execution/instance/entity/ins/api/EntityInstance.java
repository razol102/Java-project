package execution.instance.entity.ins.api;

import execution.instance.property.api.PropertyInstance;

public interface EntityInstance {
    PropertyInstance getPropertyByName(String name);
    int getId();
    boolean isPropertyExist(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
    void killMe();
    boolean checkLife();
}
