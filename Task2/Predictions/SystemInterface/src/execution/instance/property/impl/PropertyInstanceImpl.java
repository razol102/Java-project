package execution.instance.property.impl;

import definition.property.api.PropertyDef;
import execution.instance.property.api.PropertyInstance;

public class PropertyInstanceImpl implements PropertyInstance {

    private final PropertyDef propertyDef;
    private Object value;
    private int propTicks;
    private boolean justUpdated = false;

    public PropertyInstanceImpl(PropertyDef propertyDef, Object value) {
        this.propertyDef = propertyDef;
        this.value = value;
        propTicks = 0;
    }

    @Override
    public PropertyDef getPropertyDef() {
        return propertyDef;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void updateValue(Object val) {
        if(value instanceof Integer) {
            if(val instanceof Integer)
                value = (Integer) val;
            else
                value = ((Float)val).intValue();
        }
        else if(value instanceof Float) {
            if(val instanceof Integer)
                value = val;
            else
                value = (Float) val;
        }
        else
            value = val;
        propTicks = 0;
        justUpdated = true;
    }
    @Override
    public int getTicks() {
        return propTicks;
    }
    @Override
    public void tick() {
        if (justUpdated)
            justUpdated = false;
        else
            propTicks++;
    }
}