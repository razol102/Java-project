package definition.rule.action.impl.withExpression.expression.api;

import execution.context.impl.Context;

public abstract class Expression implements ExpressionDef {
    protected final String expressionName;
    protected final ExpressionSource expressionSource;
    protected ExpressionType expressionType;

    public Expression(String expressionName, ExpressionSource expressionSource, String expressionType) {
        this.expressionName = expressionName;
        this.expressionSource = expressionSource;
        switch (expressionType){
            case "BOOLEAN":
                this.expressionType = ExpressionType.BOOLEAN;
                break;
            case "FLOAT":
                this.expressionType = ExpressionType.FLOAT;
                break;
            case "DECIMAL":
                this.expressionType = ExpressionType.DECIMAL;
                break;
            case "STRING":
                this.expressionType = ExpressionType.STRING;
                break;
        }
    }
    @Override
    public ExpressionSource getExpressionSource(){ return expressionSource;}
    @Override
    public ExpressionType getExpressionType() {
        return expressionType;
    }
    @Override
    public String getExpressionName(){
        return expressionName;
    }
    @Override
    public abstract Object getValue(Context context);
}
