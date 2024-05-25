package definition.value.api;

public interface ValueDef<T> {
    T createValue();
    Boolean isRandom();
    Float getFrom();
    Float getTo();
}
