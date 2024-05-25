package definition.rule.action.impl.condition;

import definition.entity.secondaryEntity.SecondaryEntityDef;
import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.condition.singularity.AbstractCondition;
import definition.entity.EntityDef;
import execution.context.impl.Context;

import java.util.ArrayList;
import java.util.List;

public class ConditionAction extends Action {
    private final List<Action> thenActions;
    private final List<Action> elseActions;
    private final AbstractCondition condition;

    public ConditionAction(EntityDef entityDef, SecondaryEntityDef secondaryEntity, AbstractCondition condition, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, ActionType.CONDITION, actionOnSecondary);
        this.condition = condition;
        thenActions = new ArrayList<>();
        elseActions = new ArrayList<>();
    }

    public void setThenActions(List<Action> actions) {
        thenActions.addAll(actions);
    }

    public void setElseActions(List<Action> actions) {
        elseActions.addAll(actions);
    }

    public List<Action> getElseActions() {
        return elseActions;
    }

    public List<Action> getThenActions() {
        return thenActions;
    }

    public AbstractCondition getCondition(){
        return condition;
    }

    @Override
    public void invoke(Context context) {
        if(condition.operatorResult(context))
            thenActions.forEach(action -> action.invoke(context));

        else if (!(elseActions.isEmpty()))
            elseActions.forEach(action -> action.invoke(context));

    }
}
