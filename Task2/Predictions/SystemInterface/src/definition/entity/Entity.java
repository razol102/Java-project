package definition.entity;

import definition.property.api.PropertyDef;

import java.util.Map;

public class Entity implements EntityDef {
    private final String name;
    private int population;
    private final Map<String, PropertyDef> properties;
    public Entity(String name, Map<String, PropertyDef> properties) {
        this.name = name;
        this.properties = properties;
        population = 0;
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

    @Override
    public void setPopulation(int population) {
        this.population = population;
    }
}