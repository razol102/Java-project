package definition.rule.action.api;


import execution.context.impl.Context;
import definition.entity.EntityDef;

public interface ActionDef {
    void invoke(Context context);
    ActionType getActionType();
    EntityDef getContextEntity();
}
