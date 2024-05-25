package definition.value.random.impl.numeric;

public class IntegerRand extends NumericRand<Integer> {

    public IntegerRand(Float from, Float to) {
        super(from, to);
    }

    @Override
    public Integer createValue() {
        return (int) (from + random.nextInt((int) (to - from + 1)));
    }
}
