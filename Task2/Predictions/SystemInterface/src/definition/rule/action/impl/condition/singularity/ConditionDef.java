package definition.rule.action.impl.condition.singularity;

import execution.context.impl.Context;

public interface ConditionDef {
    boolean operatorResult(Context context);
    boolean isSingle();
}
