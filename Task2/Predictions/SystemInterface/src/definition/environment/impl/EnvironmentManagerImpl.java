package definition.environment.impl;

import definition.environment.api.EnvironmentManager;
import definition.property.api.PropertyDef;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.environment.impl.ActiveEnvironmentImpl;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentManagerImpl implements EnvironmentManager {
    private final Map<String, PropertyDef> propNameToPropDef;

    public EnvironmentManagerImpl() {
        propNameToPropDef = new HashMap<>();
    }

    @Override
    public void addEnvironmentVariable(PropertyDef propertyDef) {
        propNameToPropDef.put(propertyDef.getName(), propertyDef);
    }

    @Override
    public ActiveEnvironment createActiveEnvironment() {
        return new ActiveEnvironmentImpl();
    }

    @Override
    public Map<String, PropertyDef> getProps() {
        return propNameToPropDef;
    }

}
