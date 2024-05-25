package definition.property.api;

import definition.value.api.Value;

public interface PropertyDef {
    String getName();
    PropertyType getType();
    Object createValue();
    Value<?> getValue();
}
