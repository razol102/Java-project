package definition.property.impl;

import definition.value.api.Value;
import definition.property.api.Property;
import definition.property.api.PropertyType;

public class StringPropertyDefinition extends Property<String> {
    public StringPropertyDefinition(String name, Value<String> value) {
        super(name, PropertyType.STRING, value);
    }

}
