package definition.rule.action.impl.withExpression.functions.impl;

import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.api.FunctionType;
import execution.context.impl.Context;

public class EvaluateFunction extends Function {
    public EvaluateFunction() {
        super(FunctionType.EVALUATE);
    }
    @Override

    public Object execute(Context context){
        return null;
    }
}
