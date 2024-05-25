package definition.environment.api;

import definition.property.api.PropertyDef;
import execution.instance.environment.api.ActiveEnvironment;
import java.util.Collection;
import java.util.Map;

public interface EnvironmentManager {
    void addEnvironmentVariable(PropertyDef propertyDef);
    ActiveEnvironment createActiveEnvironment();
    Map<String, PropertyDef> getProps();
}
