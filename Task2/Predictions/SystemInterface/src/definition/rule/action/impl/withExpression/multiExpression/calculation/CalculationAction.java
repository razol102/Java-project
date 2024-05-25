package definition.rule.action.impl.withExpression.multiExpression.calculation;

import definition.entity.EntityDef;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.multiExpression.MultiExpression;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;

public abstract class CalculationAction extends MultiExpression {
    protected final String propertyName;
    protected final CalculationType calculationType;

    public CalculationAction(EntityDef entityDef, SecondaryEntityDef secondaryEntity, CalculationType calculationType, String propertyName, Expression arg1, Expression arg2, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, propertyName, ActionType.CALCULATION, arg1, arg2, actionOnSecondary);
        this.calculationType = calculationType;
        this.propertyName = propertyName;
    }

    @Override
    public abstract void invoke(Context context);
    public CalculationType getCalculationType() {
        return calculationType;
    }
    public Expression getArg1() {
        return expression1;
    }
    public Expression getArg2() {
        return expression2;
    }
}