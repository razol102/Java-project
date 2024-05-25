package definition.rule.action.impl.withExpression.multiExpression.impl;

import definition.entity.EntityDef;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.multiExpression.calculation.CalculationAction;
import definition.rule.action.impl.withExpression.multiExpression.calculation.CalculationType;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class CalculationMultiply extends CalculationAction {

    public CalculationMultiply(EntityDef entityDef, SecondaryEntityDef secondaryEntity, String propertyName, Expression arg1, Expression arg2, boolean actionOnSecondary) {

        super(entityDef, secondaryEntity, CalculationType.MULTIPLY, propertyName, arg1, arg2, actionOnSecondary);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = null;
        if(actionOnSecondary)
            propertyInstance = context.getSecondaryEntityInstance().getPropertyByName(property.getName());
        else
            propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());

        Object expressionValue1 = expression1.getValue(context);
        Object expressionValue2 = expression2.getValue(context);
        Object newValue;

        if (expressionValue1 instanceof Integer && expressionValue2 instanceof Integer){
            newValue = (Integer) expressionValue1 * (Integer) expressionValue2;
            if (!(fromRangeValidation(newValue)) && !(toRangeValidation(newValue)))
                propertyInstance.updateValue(((Integer) newValue).floatValue());
        }
        else {
            if (expressionValue1 instanceof Integer)
                newValue =  (Integer) expressionValue1 * (Float) expressionValue2;
            else if (expressionValue2 instanceof Integer)
                newValue =  (Float) expressionValue1 * (Integer) expressionValue2;
            else
                newValue = (Float) expressionValue1 * (Float) expressionValue2;

            if (!(fromRangeValidation(newValue)) && !(toRangeValidation(newValue)))
                propertyInstance.updateValue(newValue);
        }
    }
}
