package definition.property.api;

import definition.value.api.Value;

public abstract class Property<T> implements PropertyDef {
    private final String name;
    private final PropertyType type;
    protected Value<T> value;

    protected Property(String name, PropertyType type, Value<T> value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public PropertyType getType() {
        return type;
    }
    @Override
    public T createValue() {
        return value.createValue();
    }
    @Override
    public Value<T> getValue() {
        return value;
    }
}


