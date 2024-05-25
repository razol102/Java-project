package definition.rule.action.impl.condition;

import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.condition.singularity.AbstractCondition;
import definition.entity.EntityDef;
import execution.context.impl.Context;

import java.util.ArrayList;
import java.util.List;

public class ConditionAction extends Action {
    private List<Action> thenActions;
    private List<Action> elseActions;
    private AbstractCondition condtion;

    public ConditionAction(EntityDef entityDef, AbstractCondition condition) {
        super(entityDef, ActionType.CONDITION);
        this.condtion = condition;
        thenActions = new ArrayList<>();
        elseActions = new ArrayList<>();
    }

    public void setThenActions(List<Action> actions) {
        thenActions.addAll(actions);
    }

    public void setElseActions(List<Action> actions) {
        elseActions.addAll(actions);
    }


    public void invoke(Context context) {
        if(condtion.operatorResult(context))
            thenActions.forEach(action -> action.invoke(context));

        else if (!(elseActions.isEmpty()))
            elseActions.forEach(action -> action.invoke(context));

    }
}
