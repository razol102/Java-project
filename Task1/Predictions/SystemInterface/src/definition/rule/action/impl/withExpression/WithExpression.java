package definition.rule.action.impl.withExpression;

import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import definition.entity.EntityDef;
import definition.property.api.PropertyDef;
import definition.property.api.PropertyType;
import execution.context.impl.Context;

public abstract class WithExpression extends Action implements WithExpressionDef {
    protected PropertyDef property;

    protected WithExpression(EntityDef entityDef, ActionType actionType, String propertyName) {
        super(entityDef, actionType);

        if (entityDef.getProps().get(propertyName) == null)
            throw new IllegalArgumentException("Action can't operate on a none number property: [" + property.getName() + "]");

        this.property = entityDef.getProps().get(propertyName);
    }

    public boolean fromRangeValidation(Object newValue) {
        if(newValue instanceof Integer)
            return (((Integer) newValue).floatValue() < property.getValue().getFrom());
        return (((Float) newValue) < property.getValue().getFrom());
    }

    public boolean toRangeValidation(Object newValue) {
        if(newValue instanceof Integer)
            return (((Integer) newValue).floatValue() > property.getValue().getTo());
        return (((Float) newValue) < property.getValue().getTo());    }

    @Override
    public boolean verifyNumericPropertyTYpe(PropertyDef propertyValue) {
        return isFloatNumber(propertyValue) || isDecimalNumber(propertyValue);
    }
    @Override
    public boolean isFloatNumber(PropertyDef propertyValue) {
        return PropertyType.FLOAT.equals(propertyValue.getType());
    }
    @Override
    public boolean isDecimalNumber(PropertyDef propertyValue) {
        return PropertyType.DECIMAL.equals(propertyValue.getType());
    }
    @Override
    public abstract void invoke(Context context);
}


