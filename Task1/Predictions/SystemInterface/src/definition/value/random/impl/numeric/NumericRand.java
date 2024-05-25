package definition.value.random.impl.numeric;


import definition.value.random.api.RandomValueCreator;

public abstract class NumericRand<T> extends RandomValueCreator<T> {
    protected final Float from;
    protected final Float to;

    public NumericRand(Float from, Float to) {
        super(from, to);
        this.from = from;
        this.to = to;
    }
}
