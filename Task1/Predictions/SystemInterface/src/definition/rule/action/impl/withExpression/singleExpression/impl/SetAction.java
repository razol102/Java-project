package definition.rule.action.impl.withExpression.singleExpression.impl;

import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.singleExpression.SingleExpression;
import definition.entity.EntityDef;
import execution.context.impl.Context;
import execution.instance.property.api.PropertyInstance;

public class SetAction extends SingleExpression {
    public SetAction(EntityDef entityDef, String property, Expression expression) {
        super(entityDef, property, ActionType.SET, expression);
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property.getName());
        Object newValue = null;
        Object result = expression.getValue(context);

        if(result instanceof Integer)
             newValue = (Integer) result;
        else
            newValue = (Float) result;

        if (!(fromRangeValidation(newValue)) && !(toRangeValidation(newValue)))
            propertyInstance.updateValue(newValue);
    }
}
