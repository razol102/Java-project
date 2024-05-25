package definition.rule.action.impl.withExpression.expression.impl.Fixed.impl;

import definition.rule.action.impl.withExpression.expression.api.ExpressionType;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.FixedExpression;

public class BooleanFixed extends FixedExpression {
    public BooleanFixed(Boolean value, String expressionName) {
        super(value, ExpressionType.BOOLEAN.name(), expressionName);
    }
}
