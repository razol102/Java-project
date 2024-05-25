package definition.rule.action.impl.withExpression;

import definition.property.api.PropertyDef;
import execution.instance.property.api.PropertyInstance;

public interface WithExpressionDef {
    boolean verifyNumericPropertyTYpe(PropertyDef propertyValue);
    boolean isFloatNumber(PropertyDef propertyValue);
    boolean isDecimalNumber(PropertyDef propertyValue);
}
