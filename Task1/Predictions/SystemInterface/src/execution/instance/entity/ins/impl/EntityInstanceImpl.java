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
    public PropertyInstance getPropertyByName(String name) {
        return properties.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        properties.put(propertyInstance.getPropertyDef().getName(), propertyInstance);
    }
    @Override
    public void killMe() {
        isAlive = false;
    }

    @Override
    public boolean checkLife() {
        return isAlive;
    }

}