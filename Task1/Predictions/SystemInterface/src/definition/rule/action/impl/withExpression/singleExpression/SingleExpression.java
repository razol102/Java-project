package definition.rule.action.impl.withExpression.singleExpression;

import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.WithExpression;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.entity.EntityDef;
import execution.context.impl.Context;

public abstract class SingleExpression extends WithExpression {
    protected Expression expression;

    public SingleExpression(EntityDef entityDef, String propertyName, ActionType actionType, Expression expression) {
        super(entityDef, actionType, propertyName);
        this.expression = expression;
    }
    @Override
    public abstract void invoke(Context context);
}
