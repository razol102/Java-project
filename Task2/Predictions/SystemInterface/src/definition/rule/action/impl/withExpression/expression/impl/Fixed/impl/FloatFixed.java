package definition.rule.action.impl.withExpression.expression.impl.Fixed.impl;

import definition.rule.action.impl.withExpression.expression.api.ExpressionType;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.FixedExpression;

public class FloatFixed extends FixedExpression {

    public FloatFixed(Float value, String expressionName) {
        super(value, ExpressionType.FLOAT.name(), expressionName);
    }
}
