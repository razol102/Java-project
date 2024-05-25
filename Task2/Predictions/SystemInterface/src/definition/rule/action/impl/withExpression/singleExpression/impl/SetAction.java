package definition.rule.action.impl.withExpression.singleExpression.impl;

import definition.entity.EntityDef;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.singleExpression.SingleExpression;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class SetAction extends SingleExpression {
    public SetAction(EntityDef entityDef, SecondaryEntityDef secondaryEntity, String propertyName, Expression expression, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, propertyName, ActionType.SET, expression, actionOnSecondary);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = null;
        if(actionOnSecondary)
            propertyInstance = context.getSecondaryEntityInstance().getPropertyByName(property.getName());
        else
            propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());

        Object newValue = null;
        Object result = expression.getValue(context);

        if(result instanceof Integer)
             newValue = (Integer) result;
        else if(result instanceof Integer)
            newValue = (Float) result;
        else if(result instanceof Boolean)
            newValue = (Boolean) result;
        else
            newValue = (String) result;

        if(newValue instanceof Number) {
            if (!(fromRangeValidation(newValue)) && !(toRangeValidation(newValue)))
                propertyInstance.updateValue(newValue);
        }
    }
}
