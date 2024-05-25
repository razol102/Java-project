package definition.rule.action.impl.withExpression.multiExpression.impl;

import definition.entity.EntityDef;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.multiExpression.calculation.CalculationAction;
import definition.rule.action.impl.withExpression.multiExpression.calculation.CalculationType;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class CalculationDivide extends CalculationAction {
    public CalculationDivide(EntityDef entityDef, SecondaryEntityDef secondaryEntity, String propertyName, Expression arg1, Expression arg2, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, CalculationType.DIVIDE, propertyName, arg1, arg2, actionOnSecondary);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = null;
        if(actionOnSecondary)
            propertyInstance = context.getSecondaryEntityInstance().getPropertyByName(property.getName());
        else
            propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());

        Float expressionValue1 = (Float) expression1.getValue(context);
        Float expressionValue2 = (Float) expression2.getValue(context);

        if (expressionValue2 != 0) {
            Float newValue = expressionValue1 / expressionValue2;

            if (!(fromRangeValidation(newValue)) && !(toRangeValidation(newValue)))
                propertyInstance.updateValue(newValue);
        }
         else {
            throw new ArithmeticException ("Division by zero is not allowed.");
        }
    }

}
