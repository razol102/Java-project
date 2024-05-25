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
    private int id;
    private List<EntityInstance> instances;
    private List<EntityInstance> instancesToAdd;
    private List<String> propertyNames;

    public EntityInstanceManagerImpl(int initPopulation, Set<String> propertyNames) {
        this.initPopulation = initPopulation;
        id = 0;
        instances = new ArrayList<>();
        this.propertyNames = new ArrayList<>(propertyNames);
        instancesToAdd = new ArrayList<>();
    }

    @Override
    public EntityInstance createInstance(EntityDef entityDef) {
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDef, ++id);
        for (Map.Entry<String, PropertyDef> propertyDef : entityDef.getProps().entrySet()) {
            Object value = propertyDef.getValue().createValue();
            PropertyDef propertyToInsert = propertyDef.getValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyToInsert, value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }
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
        instances = instances.stream()
                .filter(EntityInstance::checkLife)
                .peek(instance -> instance.getCoordinate().setSet(false))
                .collect(Collectors.toList());
    }

    @Override
    public void addInstance(EntityInstance instance) {
        instances.add(instance);
    }
    @Override
    public void addInstanceToAdd(EntityInstance instance) {
        instancesToAdd.add(instance);
    }
    @Override
    public void clearInstanceReplacements() {
        instancesToAdd.clear();
    }

    @Override
    public void concatInstances() {
        instances.addAll(instancesToAdd);
        instancesToAdd.clear();
    }

}
