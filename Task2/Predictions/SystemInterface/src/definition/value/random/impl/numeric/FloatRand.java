package definition.value.random.impl.numeric;


public class FloatRand extends NumericRand<Float> {

    public FloatRand(Float from, Float to) {
        super(from, to);
    }

    @Override
    public Float createValue() {
        return from + random.nextFloat() * (to - from + 1.0f);
    }
}
