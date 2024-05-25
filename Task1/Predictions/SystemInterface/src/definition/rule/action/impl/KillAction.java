package definition.rule.action.impl;

import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import definition.entity.EntityDef;
import execution.context.impl.Context;

public class KillAction extends Action {

    public KillAction(EntityDef entityDef) {
        super(entityDef, ActionType.KILL);
    }
    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }
}