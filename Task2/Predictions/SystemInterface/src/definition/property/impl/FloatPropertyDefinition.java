package definition.property.impl;

import definition.value.api.Value;
import definition.property.api.Property;
import definition.property.api.PropertyType;

public class FloatPropertyDefinition extends Property<Float> {
    public FloatPropertyDefinition(String name, Value<Float> value) {
        super(name, PropertyType.FLOAT, value);
    }

}
