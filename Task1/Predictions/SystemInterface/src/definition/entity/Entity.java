package definition.entity;

import definition.property.api.PropertyDef;
import java.util.Map;

public class Entity implements EntityDef {
    private final String name;       // unique name - without spaces!
    private final int population;
    private final Map<String, PropertyDef> properties;

    public Entity(String name, int population, Map<String, PropertyDef> properties) {
        this.name = name;
        this.population = population;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public Map<String, PropertyDef> getProps() {
        return properties;
    }
}