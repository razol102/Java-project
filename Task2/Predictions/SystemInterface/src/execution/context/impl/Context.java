package execution.context.impl;

import execution.context.api.ContextDef;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.api.PropertyInstance;

public class Context implements ContextDef {
    private final EntityInstance primaryEntityInstance;
    private final EntityInstance secondaryEntityInstance;
    private final EntityInstanceManager entityInstanceManager;
    private final ActiveEnvironment activeEnvironment;

    public Context(EntityInstance primaryEntityInstance, EntityInstance secondaryEntityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.secondaryEntityInstance = secondaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public EntityInstance getSecondaryEntityInstance() {
        return secondaryEntityInstance;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstance.killMe();
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }

    @Override
    public EntityInstanceManager getEntityInstanceManager() {return entityInstanceManager;}

}

