package definition.value.random.impl.bool;

import definition.value.random.api.RandomValueCreator;

public class BoolRand extends RandomValueCreator<Boolean> {
    public BoolRand() {
        super(null, null);
    }


    @Override
    public Boolean createValue() {
        return random.nextBoolean();
    }
}
