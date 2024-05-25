package definition.rule.action.impl.withExpression.functions.impl;

import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.api.FunctionType;
import execution.context.impl.Context;

public class TicksFunction extends Function {
    private final String propertyName;

    public TicksFunction(String propertyName) {
        super(FunctionType.TICKS);
        this.propertyName = propertyName;
    }
    @Override
    public Object execute(Context context) {
        return context.getPrimaryEntityInstance().getPropertyByName(propertyName).getTicks();
    }
}