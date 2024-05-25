package definition.entity;

import definition.property.api.PropertyDef;

import java.util.Map;

public interface EntityDef {
    String getName();

    int getPopulation();

    Map<String, PropertyDef> getProps();

    void setPopulation(int population);
}