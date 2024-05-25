package execution.instance.environment.api;

import execution.instance.property.api.PropertyInstance;

import java.util.Map;

public interface ActiveEnvironment {
    PropertyInstance getProperty(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
    Map<String, PropertyInstance> getEnvVariables();

}
