package definition.rule.action.api;


import definition.entity.EntityDef;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;

public interface ActionDef {
    void invoke(Context context);
    ActionType getActionType();
    EntityDef getMainEntity();
    SecondaryEntityDef getSecondaryEntity();
}
