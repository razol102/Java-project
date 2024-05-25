package definition.rule.action.impl.withExpression.singleExpression.impl;

import definition.entity.EntityDef;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.singleExpression.SingleExpression;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class IncreaseAction extends SingleExpression {

    public IncreaseAction(EntityDef entityDef, SecondaryEntityDef secondaryEntity, String propertyName, Expression expression, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, propertyName, ActionType.INCREASE, expression, actionOnSecondary);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = null;
        if(actionOnSecondary)
            propertyInstance = context.getSecondaryEntityInstance().getPropertyByName(property.getName());
        else
            propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());

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
