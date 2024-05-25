package definition.rule.action.impl.withExpression.calculation.api;

import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.WithExpression;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.entity.EntityDef;
import execution.context.impl.Context;

public abstract class CalculationAction extends WithExpression {
    protected final String propertyName;
    protected Expression arg1, arg2;


    public CalculationAction(EntityDef entityDef, String propertyName, Expression arg1, Expression arg2) {
        super(entityDef, ActionType.CALCULATION, propertyName);
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.propertyName = propertyName;
    }

    @Override
    public abstract void invoke(Context context);
}