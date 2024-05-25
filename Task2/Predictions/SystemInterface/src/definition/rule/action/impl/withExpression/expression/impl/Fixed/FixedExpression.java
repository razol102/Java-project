package definition.rule.action.impl.withExpression.expression.impl.Fixed;

import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.expression.api.ExpressionSource;
import execution.context.impl.Context;

public class FixedExpression extends Expression {
    protected final Object value;

    public FixedExpression(Object value, String expressionType, String expressionName) {
        super(expressionName, ExpressionSource.FIXED_VALUE, expressionType);
        this.value = value;
    }

    @Override
    public Object getValue(Context context){
        return value;
    }
}
