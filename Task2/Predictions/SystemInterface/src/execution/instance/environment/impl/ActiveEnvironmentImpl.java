package execution.instance.environment.impl;

import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.api.PropertyInstance;
import java.util.HashMap;
import java.util.Map;

public class ActiveEnvironmentImpl implements ActiveEnvironment {

    private final Map<String, PropertyInstance> envVariables;

    public ActiveEnvironmentImpl() {
        envVariables = new HashMap<>();
    }

    @Override
    public PropertyInstance getProperty(String name) {
        if (!envVariables.containsKey(name)) {
            throw new IllegalArgumentException("Can't find environment variable with name: " + name);
        }
        return envVariables.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        envVariables.put(propertyInstance.getPropertyDef().getName(), propertyInstance);
    }

    @Override
    public Map<String, PropertyInstance> getEnvVariables() {
        return envVariables;
    }
}