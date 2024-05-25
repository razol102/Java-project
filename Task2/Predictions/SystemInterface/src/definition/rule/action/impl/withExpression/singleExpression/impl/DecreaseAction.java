package definition.rule.action.impl.withExpression.singleExpression.impl;

import definition.entity.EntityDef;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.singleExpression.SingleExpression;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class DecreaseAction extends SingleExpression {

    public DecreaseAction(EntityDef entityDef, SecondaryEntityDef secondaryEntity, String propertyName, Expression expression, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, propertyName, ActionType.DECREASE, expression, actionOnSecondary);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = null;
        if(actionOnSecondary) {
            if(context.getSecondaryEntityInstance() != null)
                propertyInstance = context.getSecondaryEntityInstance().getPropertyByName(property.getName());
            else
                return;
        }
        else
            propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());

        Object propertyValue = propertyInstance.getValue();
        Object expressionValue = expression.getValue(context);
        if(expressionValue instanceof String) {
            System.out.println(expressionValue.toString());
            System.out.println(expressionValue.getClass());
            System.out.println(actionOnSecondary);
            System.out.println(propertyInstance);
            System.out.println(expression.getExpressionName());
            System.out.println(expression.getExpressionType());
        }

        if (propertyValue instanceof Float) {
            Float propValue = (Float) propertyValue;
            Float incrementValue = null;
            if(expressionValue instanceof Float)
                incrementValue = (Float) expressionValue;
            else
                incrementValue = ((Integer) expressionValue).floatValue();
            Float newValue = propValue - incrementValue;
            if(!(toRangeValidation(newValue)))
                propertyInstance.updateValue(newValue);
        }
        else if (propertyValue instanceof Integer) {
            Integer propValue = (Integer) propertyValue;
            Integer incrementValue = (Integer) expressionValue;
            Integer newValue = propValue - incrementValue;
            if(!(toRangeValidation(newValue.floatValue())))
                propertyInstance.updateValue(newValue);
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + propertyValue.getClass());
        }
    }
}
