package definition.rule.action.impl.others;

import definition.entity.EntityDef;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import execution.context.impl.Context;

public class KillAction extends Action {

    public KillAction(EntityDef entityDef, SecondaryEntityDef secondaryEntity, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, ActionType.KILL, actionOnSecondary);
    }
    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }
}