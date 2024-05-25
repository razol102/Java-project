package definition.rule.action.impl.withExpression.expression.impl.Fixed.impl;

import definition.rule.action.impl.withExpression.expression.api.ExpressionType;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.FixedExpression;

public class IntegerFixed extends FixedExpression {
    public IntegerFixed(Integer value, String expressionName) {
        super(value, ExpressionType.DECIMAL.name(), expressionName);
    }
}
