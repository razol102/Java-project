package definition.rule.action.impl.condition.singularity;

import definition.rule.action.impl.condition.operator.Operator;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.entity.EntityDef;
import execution.context.impl.Context;

public class SingleCondition extends AbstractCondition {
    private final EntityDef entityOp;
    private final Operator operator;
    private final Expression expression;

    protected final String property;


    public SingleCondition(EntityDef entityOp, String property, String operatorName, Expression expression) {
        this.entityOp = entityOp;
        this.expression = expression;
        this.operator = new Operator(operatorName);
        this.property = property;
    }

    @Override
    public boolean operatorResult(Context context) {
        return operator.comparisonExecute(context.getPrimaryEntityInstance().getPropertyByName(property).getValue(), expression.getValue(context));
    }
}
