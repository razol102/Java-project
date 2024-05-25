package definition.rule.action.impl.withExpression.expression.api;

import execution.context.impl.Context;

public interface ExpressionDef {
    Object getValue(Context context);
    ExpressionType getExpressionType();
    ExpressionSource getExpressionSource();
    String getExpressionName();
}
