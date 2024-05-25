package definition.rule.action.api;

import definition.entity.EntityDef;
import execution.context.impl.Context;

public abstract class Action implements ActionDef {
    protected final EntityDef entityDef;
    protected final ActionType actionType;

    protected Action(EntityDef entityDef, ActionType actionType) {
        this.entityDef = entityDef;
        this.actionType = actionType;
    }
    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDef getContextEntity() {
        return entityDef;
    }

    @Override
    public abstract void invoke(Context context);

}