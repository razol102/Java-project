package execution.instance.property.api;

import definition.property.api.PropertyDef;

public interface PropertyInstance {
    PropertyDef getPropertyDef();
    Object getValue();
    void updateValue(Object val);
    int getTicks();
    void tick();
}
