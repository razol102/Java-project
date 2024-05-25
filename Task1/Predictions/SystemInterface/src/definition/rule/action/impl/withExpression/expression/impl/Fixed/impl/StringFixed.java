package definition.rule.action.impl.withExpression.expression.impl.Fixed.impl;

import definition.rule.action.impl.withExpression.expression.api.ExpressionType;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.FixedExpression;

public class StringFixed extends FixedExpression {
    public StringFixed(String value) {
        super(value, ExpressionType.STRING.name());
    }
}
