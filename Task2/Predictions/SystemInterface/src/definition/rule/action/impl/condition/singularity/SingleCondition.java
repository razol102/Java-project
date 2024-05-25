package definition.rule.action.impl.condition.singularity;

import definition.entity.EntityDef;
import definition.property.api.PropertyDef;
import definition.rule.action.impl.condition.operator.Operator;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.expression.impl.PropertyExpression;
import execution.context.impl.Context;

public class SingleCondition extends AbstractCondition {
    private final EntityDef entityOp;
    private final Operator operator;
    private final Expression valueExpression;
    protected final Expression operand;


    public SingleCondition(EntityDef entityOp, Expression operand, String operatorName, Expression valueExpression) {
        this.entityOp = entityOp;
        this.valueExpression = valueExpression;
        this.operator = new Operator(operatorName);
        this.operand = operand;
    }

    public boolean isOperandProperty(){
        return operand.getExpressionSource().name().equalsIgnoreCase("property");

    }
    public Expression getOperand() {
        return operand;
    }
    public Operator getOperator() {
        return operator;
    }
    public Expression getValueExpression() {
        return valueExpression;
    }

    @Override
    public boolean operatorResult(Context context) {
        if(isOperandProperty()){
            PropertyDef propertyExpression = ((PropertyExpression)operand).getProperty();
            return operator.comparisonExecute(context.getPrimaryEntityInstance().getPropertyByName(propertyExpression.getName()).getValue(), valueExpression.getValue(context));
        }
        else
            return operator.comparisonExecute(operand.getValue(context), valueExpression.getValue(context));
    }

    @Override
    public boolean isSingle(){
        return true;
    }
}
