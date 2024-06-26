package definition.rule.action.impl.condition.singularity;

import execution.context.impl.Context;
import java.util.List;

public class MultipleCondition extends AbstractCondition {
    private final String logical;
    private List<AbstractCondition> conditionList;

    public MultipleCondition(String logical, List<AbstractCondition> conditionList) {
        this.logical = logical;
        this.conditionList = conditionList;
    }
    @Override
    public boolean operatorResult(Context context) {
        if (logical.equals("or")) {
            for (AbstractCondition condition : conditionList)
                if (condition.operatorResult(context))
                    return true;
            return false;
        }
        else { // and
            for (AbstractCondition condition : conditionList)
                if (!(condition.operatorResult(context)))
                    return false;
            return true;
        }
    }
}

