package definition.rule.action.impl.withExpression.functions.api;

import execution.context.impl.Context;

public abstract class Function implements FunctionDef {
    protected final FunctionType functionType;

    public Function(FunctionType functionType) {
        this.functionType = functionType;
    }

    @Override
    public abstract Object execute(Context context);
}
