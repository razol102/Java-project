package definition.rule.action.impl.withExpression.expression.impl;

import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.expression.api.ExpressionSource;
import definition.rule.action.impl.withExpression.functions.api.Function;
import execution.context.impl.Context;

public class FunctionExpression extends Expression {
    private final Function function;

    public FunctionExpression(Function function, String type, String expressionName) {
        super(expressionName, ExpressionSource.FUNCTION, type);
        this.function = function;
    }
    @Override
    public Object getValue(Context context) {
        return function.execute(context);
    }

}
