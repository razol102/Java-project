package execution.context.api;

import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.ins.impl.Coordinate;
import execution.instance.entity.manager.api.EntityInstanceManager;
import execution.instance.property.api.PropertyInstance;

import java.util.List;

public interface ContextDef {
    EntityInstance getPrimaryEntityInstance();
    EntityInstance getSecondaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
    EntityInstanceManager getEntityInstanceManager();
}