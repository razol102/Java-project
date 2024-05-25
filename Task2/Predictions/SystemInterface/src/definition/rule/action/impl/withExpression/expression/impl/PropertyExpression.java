package definition.rule.action.impl.withExpression.expression.impl;

import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.expression.api.ExpressionSource;
import definition.property.api.PropertyDef;
import execution.context.impl.Context;

public class PropertyExpression extends Expression {
    private final PropertyDef property;

    public PropertyExpression(PropertyDef property, String expressionName) {
        super(expressionName, ExpressionSource.PROPERTY, property.getType().name());
        this.property = property;
    }
    @Override
    public Object getValue(Context context) {
        return property.getValue().createValue();
    }
    public PropertyDef getProperty(){ return property;}
}
