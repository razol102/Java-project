package definition.rule.action.impl.condition.operator;

public interface OperatorDef {
    boolean comparisonExecute(Object arg1, Object arg2);
    boolean areObjectsEqual(Object obj1, Object obj2);
    boolean areObjectsNumericAndBigger(Object obj1, Object obj2);
    boolean areObjectsNumericAndLower(Object obj1, Object obj2);
}
