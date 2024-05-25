package definition.rule.action.impl.withExpression.singleExpression;

import definition.entity.EntityDef;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.WithExpression;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;

public abstract class SingleExpression extends WithExpression {
    protected Expression expression;

    public SingleExpression(EntityDef entityDef, SecondaryEntityDef secondaryEntity, String propertyName, ActionType actionType, Expression expression, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, actionType, propertyName, actionOnSecondary);
        this.expression = expression;
    }
    @Override
    public abstract void invoke(Context context);

    public Expression getExpression() {
        return expression;
    }
}
