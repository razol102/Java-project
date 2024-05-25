package definition.value.api;


public abstract class Value<T> implements ValueDef<T>{
    private final Boolean isRandom;
    private final Float from, to;

    public Value(Boolean isRandom, Float from, Float to) {
        this.isRandom = isRandom;
        this.from = from;
        this.to = to;
    }

    @Override
    public abstract T createValue();

    @Override
    public Boolean isRandom() {
        return isRandom;
    }

    @Override
    public Float getFrom() {
        return from;
    }

    @Override
    public Float getTo() {
        return to;
    }
}
