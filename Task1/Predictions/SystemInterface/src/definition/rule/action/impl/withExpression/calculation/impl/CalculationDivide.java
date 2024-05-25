package definition.rule.action.impl.withExpression.calculation.impl;

import definition.rule.action.impl.withExpression.calculation.api.CalculationAction;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.entity.EntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class CalculationDivide extends CalculationAction {
    public CalculationDivide(EntityDef entityDef, String propertyName, Expression arg1, Expression arg2) {
        super(entityDef, propertyName, arg1, arg2);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());

        Float expressionValue1 = (Float) arg1.getValue(context);
        Float expressionValue2 = (Float) arg2.getValue(context);

        if (expressionValue2 != 0) {
            Float newValue = expressionValue1 / expressionValue2;

            if (!(fromRangeValidation(newValue)) && !(toRangeValidation(newValue)))
                propertyInstance.updateValue(newValue);
        }
         else { // exception
            throw new ArithmeticException ("Division by zero is not allowed.");
        }
    }
}
