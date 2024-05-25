package definition.rule.action.impl.withExpression.multiExpression;

import definition.entity.EntityDef;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.WithExpression;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;

public abstract class MultiExpression extends WithExpression {
    protected Expression expression1, expression2;

    public MultiExpression(EntityDef entityDef, SecondaryEntityDef secondaryEntity, String propertyName, ActionType actionType, Expression expression1, Expression expression2, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, actionType, propertyName, actionOnSecondary);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }
    @Override
    public abstract void invoke(Context context);
}
