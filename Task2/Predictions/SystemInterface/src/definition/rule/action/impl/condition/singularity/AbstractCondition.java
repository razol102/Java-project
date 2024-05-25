package definition.rule.action.impl.condition.singularity;

import execution.context.impl.Context;

public abstract class AbstractCondition implements ConditionDef {
    @Override
    public abstract boolean operatorResult(Context context);
    @Override
    public abstract boolean isSingle();

}
