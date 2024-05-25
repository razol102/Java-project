package definition.rule.action.impl.withExpression;

import definition.entity.EntityDef;
import definition.property.api.PropertyDef;
import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;

public abstract class WithExpression extends Action implements WithExpressionDef {
    protected PropertyDef property;

    protected WithExpression(EntityDef entityDef, SecondaryEntityDef secondaryEntity, ActionType actionType, String propertyName, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, actionType, actionOnSecondary);

        if(!actionType.name().equalsIgnoreCase("proximity"))
            if(!actionType.name().equalsIgnoreCase("replace"))
                if (entityDef.getProps().get(propertyName) == null)
                  throw new IllegalArgumentException("Action can't operate on a none number property: [" + property.getName() + "]");

        this.property = entityDef.getProps().get(propertyName);
    }

    @Override
    public boolean fromRangeValidation(Object newValue) {
        if(newValue instanceof Integer)
            return (((Integer) newValue).floatValue() < property.getValue().getFrom());
        return (((Float) newValue) < property.getValue().getFrom());
    }

    @Override
    public boolean toRangeValidation(Object newValue) {
        if(newValue instanceof Integer)
            return (((Integer) newValue).floatValue() > property.getValue().getTo());
        return (((Float) newValue) < property.getValue().getTo());
    }

    public String getProperty() {
        return property.getName();
    }

    @Override
    public abstract void invoke(Context context);
}


