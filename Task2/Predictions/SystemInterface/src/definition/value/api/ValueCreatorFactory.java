package definition.value.api;

import definition.value.random.impl.bool.BoolRand;
import definition.value.random.impl.numeric.IntegerRand;
import definition.value.random.impl.numeric.FloatRand;
import definition.value.random.impl.string.StringRand;
import definition.value.fixed.FixedValue;

public interface ValueCreatorFactory {
    static <T> ValueDef<T> createFixed(T value, Float from, Float to) {
        return new FixedValue<>(value, from, to);
    }
    static ValueDef<Integer> createRandomInteger(Float from, Float to) {
        return new IntegerRand(from, to);
    }
    static ValueDef<Float> createRandomFloat(Float from, Float to) {
        return new FloatRand(from, to);
    }
    static ValueDef<String> createRandomString() {
        return new StringRand();
    }
    static ValueDef<Boolean> createRandomBoolean() {
        return new BoolRand();
    }

}