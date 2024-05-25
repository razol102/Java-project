package definition.property.impl;

import definition.value.api.Value;
import definition.property.api.Property;
import definition.property.api.PropertyType;

public class IntegerPropertyDefinition extends Property<Integer> {

    public IntegerPropertyDefinition(String name, Value<Integer> value) {
        super(name, PropertyType.DECIMAL, value);
    }

}
