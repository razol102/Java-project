package definition.value.random.api;

import definition.value.api.Value;
import java.util.Random;

public abstract class RandomValueCreator<T> extends Value<T> {
    protected final Random random;

    public RandomValueCreator(Float from, Float to) {
        super(true, from, to);
        this.random = new Random();
    }
}
