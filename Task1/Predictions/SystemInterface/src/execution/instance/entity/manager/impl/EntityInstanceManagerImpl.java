package execution.instance.entity.manager.impl;

import definition.entity.EntityDef;
import definition.property.api.PropertyDef;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.ins.impl.EntityInstanceImpl;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.property.api.PropertyInstance;
import execution.instance.property.impl.PropertyInstanceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityInstanceManagerImpl implements EntityInstanceManager {

    private final int initPopulation;
    private int count;
    private List<EntityInstance> instances;
    private List<String> propertyNames;
    public EntityInstanceManagerImpl(int initPopulation, Set<String> propertyNames) {
        this.initPopulation = initPopulation;
        count = 0;
        instances = new ArrayList<>();
        this.propertyNames = new ArrayList<>(propertyNames);
    }

    @Override
    public EntityInstance createInstance(EntityDef entityDef) {

        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDef, count);
        for (Map.Entry<String, PropertyDef> propertyDef : entityDef.getProps().entrySet()) {
            Object value = propertyDef.getValue().createValue();
            PropertyDef propertyToInsert = propertyDef.getValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyToInsert, value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }
        instances.add(newEntityInstance);

        return newEntityInstance;
    }

    @Override
    public List<EntityInstance> getInstances() {
        return instances;
    }

    @Override
    public List<String> getPropertyNames() {
        return propertyNames;
    }

    @Override
    public int getInitPopulation() {
        return initPopulation;
    }

    @Override
    public void killingSpree() {
        List<EntityInstance> aliveEntities = instances.stream()
                .filter(EntityInstance::checkLife)
                .collect(Collectors.toList());

        instances = aliveEntities;
        count = aliveEntities.size();
    }
}
