package definition.rule.action.impl.withExpression.expression.api;

import execution.context.impl.Context;

public abstract class Expression implements ExpressionDef {
    protected ExpressionSource expressionSource;
    protected ExpressionType expressionType;

    public Expression(ExpressionSource expressionSource, String expressionType) {
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
    public ExpressionType getExpressionType() {
        return expressionType;
    }

    @Override
    public abstract Object getValue(Context context);
}


/*
        @Override
        public boolean isInteger() {
            return expression instanceof Integer;
        }
       @Override
        public void updateExpression(Context context){
            if (function != null) {             // FunctionType
                expression = function.execute((context));
            }
            else if(propertyInstance != null) { // PropertyType
                expression = propertyInstance.getValue();
            }
            else                                // simple value
                expression = value;
        }*/