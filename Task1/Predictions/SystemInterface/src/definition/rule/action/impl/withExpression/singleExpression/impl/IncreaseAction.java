package definition.rule.action.impl.withExpression.singleExpression.impl;

import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.singleExpression.SingleExpression;
import definition.entity.EntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class IncreaseAction extends SingleExpression {

    public IncreaseAction(EntityDef entityDef, String property, Expression expression) {
        super(entityDef, property, ActionType.INCREASE, expression);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());
        Object propertyValue = propertyInstance.getValue();
        Object expressionValue = expression.getValue(context);

        if (propertyValue instanceof Float) {
            Float propValue = (Float) propertyValue;
            Float incrementValue = null;
            if(expressionValue instanceof Float)
                incrementValue = (Float) expressionValue;
            else
                incrementValue = ((Integer) expressionValue).floatValue();
            Float newValue = propValue + incrementValue;
            if(!(toRangeValidation(newValue)))
                propertyInstance.updateValue(newValue);
        }
        else if (propertyValue instanceof Integer) {
            Integer propValue = (Integer) propertyValue;
            Integer incrementValue = (Integer) expressionValue;
            Integer newValue = propValue + incrementValue;
            if(!(toRangeValidation(newValue.floatValue())))
                propertyInstance.updateValue(newValue);
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + propertyValue.getClass());
        }

    }
}
