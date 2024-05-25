package definition.rule.action.impl.withExpression.functions.impl;

import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.api.FunctionType;
import execution.context.impl.Context;

public class EnvironmentFunction extends Function {
    private final String environmentName;

    public EnvironmentFunction(String environmentName) {
        super(FunctionType.ENVIRONMENT);
        this.environmentName = environmentName;
    }

    @Override
    public Object execute(Context context) {
        return context.getEnvironmentVariable(environmentName).getValue();
    }
}
