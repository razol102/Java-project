package definition.rule.action.impl.withExpression.functions.impl;

import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.api.FunctionType;
import execution.context.impl.Context;

import java.util.Random;

public class RandomFunction extends Function {
    private final Integer num;
    private  Random random;

    public RandomFunction(Integer num) {
        super(FunctionType.RANDOM);
        this.random = new Random();
        this.num = num;
    }
    @Override
    public Integer execute(Context context) {
        return Integer.valueOf(random.nextInt(num + 1));
    }
}
