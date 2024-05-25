package definition.rule.action.impl.withExpression.functions.impl;

import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.api.FunctionType;
import execution.context.impl.Context;

public class PercentFunction extends Function {
    private final float HUNDRED = 100;
    private final Expression whole, part;

    public PercentFunction(Expression whole, Expression part) {
        super(FunctionType.PERCENT);
        this.whole = whole;
        this.part = part;
    }
    @Override
    public Object execute(Context context) {
        Object wholeValue = whole.getValue(context);
        Object partValue = part.getValue(context);
        float arg1, arg2;
        if(wholeValue instanceof Integer)
            arg1 = ((Integer) wholeValue).floatValue();
        else
            arg1 = (Float) wholeValue;

        if(partValue instanceof Integer)
            arg2 = ((Integer) partValue).floatValue();
        else
            arg2 = (Float) partValue;

        return arg1 * arg2/HUNDRED;
    }
}
