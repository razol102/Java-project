package definition.rule.action.impl.withExpression.functions.impl;

import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.api.FunctionType;
import execution.context.impl.Context;

public class PercentFunction extends Function {

    private final float whole;
    private final float part;
    private final int HUNDRED = 100;

    public PercentFunction(float whole, float part) {
        super(FunctionType.PERCENT);
        this.whole = whole;
        this.part = part;
    }
    @Override

    public Object execute(Context context) {
        return whole * part/HUNDRED;
    }
}
