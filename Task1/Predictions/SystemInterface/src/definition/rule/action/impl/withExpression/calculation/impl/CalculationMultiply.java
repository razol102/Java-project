package definition.rule.action.impl.withExpression.calculation.impl;

import definition.rule.action.impl.withExpression.calculation.api.CalculationAction;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.entity.EntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class CalculationMultiply extends CalculationAction {

    public CalculationMultiply(EntityDef entityDef, String propertyName, Expression arg1, Expression arg2) {

        super(entityDef, propertyName, arg1, arg2);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());

        Object expressionValue1 = arg1.getValue(context);
        Object expressionValue2 = arg2.getValue(context);
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
