package definition.value.fixed;


import definition.value.api.Value;

public class FixedValue<T> extends Value<T> {
    private final T fixedValue;

    public FixedValue(T fixedValue, Float from, Float to) {
        super(false, from, to);
        this.fixedValue = fixedValue;
    }

    @Override
    public T createValue() {
        return fixedValue;
    }
}
