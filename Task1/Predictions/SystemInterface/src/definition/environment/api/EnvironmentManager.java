package definition.environment.api;

import definition.property.api.PropertyDef;
import execution.instance.environment.api.ActiveEnvironment;
import java.util.Collection;
import java.util.Map;

public interface EnvironmentManager {
    void addEnvironmentVariable(PropertyDef propertyDef);
    ActiveEnvironment createActiveEnvironment();
    Collection<PropertyDef> getEnvVariables();
    Map<String, PropertyDef> getProps();
}
