package definition.property.impl;

import definition.value.api.Value;
import definition.property.api.Property;
import definition.property.api.PropertyType;

public class BooleanPropertyDefinition extends Property<Boolean> {
    public BooleanPropertyDefinition(String name, Value<Boolean> value) {
        super(name, PropertyType.BOOLEAN, value);
    }
}
