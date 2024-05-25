package execution.context.api;

import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.property.api.PropertyInstance;

public interface ContextDef {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
}