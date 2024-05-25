package execution.instance.property.impl;

import definition.property.api.PropertyDef;
import execution.instance.property.api.PropertyInstance;

public class PropertyInstanceImpl implements PropertyInstance {

    private PropertyDef propertyDef;
    private Object value;

    public PropertyInstanceImpl(PropertyDef propertyDef, Object value) {
        this.propertyDef = propertyDef;
        this.value = value;
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
            if(val instanceof Integer) {
                this.value = (Integer) val;
            }
            else
                this.value = ((Float)val).intValue();
        }
        if(val instanceof Integer) {
            this.value = val;
        }
        else
            this.value = (Float) val;
    }
}