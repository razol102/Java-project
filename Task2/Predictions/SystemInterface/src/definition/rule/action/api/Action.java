package definition.rule.action.api;

import definition.entity.EntityDef;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;

public abstract class Action implements ActionDef {
    protected final EntityDef mainEntity;
    protected final SecondaryEntityDef secondaryEntity;
    protected final ActionType actionType;
    protected final boolean actionOnSecondary;

    protected Action(EntityDef mainEntity, SecondaryEntityDef secondaryEntity, ActionType actionType, boolean actionOnSecondary) {
        this.mainEntity = mainEntity;
        this.secondaryEntity = secondaryEntity;
        this.actionType = actionType;
        this.actionOnSecondary = actionOnSecondary;
    }
    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDef getMainEntity() {
        return mainEntity;
    }

    @Override
    public SecondaryEntityDef getSecondaryEntity() {
        return secondaryEntity;
    }
    @Override
    public abstract void invoke(Context context);
}
