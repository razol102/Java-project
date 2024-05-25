package definition.rule.action.impl.condition.operator;

public class Operator implements OperatorDef {
    private OperatorType operatorType;

    public Operator(String operatorName) {
        switch (operatorName){
            case "=":
                operatorType = OperatorType.EQUALS;
                break;
            case "!=":
                operatorType = OperatorType.DIFFERENT;
                break;
            case "bt":
                operatorType = OperatorType.BIGGER_THAN;
                break;
            case "lt":
                operatorType = OperatorType.LOWER_THAN;
                break;
            default:

                break;
        }
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    @Override
    public boolean comparisonExecute(Object arg1, Object arg2) {

        switch (operatorType) {
            case EQUALS:
                return areObjectsEqual(arg1, arg2);
            case DIFFERENT:
                return areObjectsEqual(arg1, arg2);
            case BIGGER_THAN:
                return areObjectsNumericAndBigger(arg1, arg2);
            case LOWER_THAN:
                return areObjectsNumericAndLower(arg1, arg2);
            default:
                return false;
        }
    }

    @Override
    public boolean areObjectsEqual(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }

        if (obj1 instanceof Float && obj2 instanceof Float) {
            return ((Float) obj1).equals((Float) obj2);
        }
        if (obj1 instanceof Integer && obj2 instanceof Integer) {
            return ((Integer) obj1).equals((Integer) obj2);
        }
        if (obj1 instanceof Boolean && obj2 instanceof Boolean) {
            return ((Boolean) obj1).equals((Boolean) obj2);
        }
        if (obj1 instanceof String && obj2 instanceof String) {
            return ((String) obj1).equals((String) obj2);
        }

        return false; // Objects are not of compatible types
    }

    @Override
    public boolean areObjectsNumericAndBigger(Object obj1, Object obj2) {
        if (obj1 instanceof Number && obj2 instanceof Number) {
            float val1 = ((Number) obj1).floatValue();
            float val2 = ((Number) obj2).floatValue();
            return val1 > val2;
        }
        return false; // Objects are not numeric
    }

    @Override
    public boolean areObjectsNumericAndLower(Object obj1, Object obj2) {
        if (obj1 instanceof Number && obj2 instanceof Number) {
            float val1 = ((Number) obj1).floatValue();
            float val2 = ((Number) obj2).floatValue();
            return val1 < val2;
        }
        return false;
    }
}