package definition.rule.action.impl.withExpression.functions.impl;

import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.api.FunctionType;
import execution.context.impl.Context;

public class TicksFunction extends Function {
    public TicksFunction() {
        super(FunctionType.TICKS);
    }
    @Override
    public Object execute(Context context) {
        return null;
    }

}
