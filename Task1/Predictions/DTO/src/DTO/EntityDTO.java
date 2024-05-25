package DTO;

import java.util.List;

public class EntityDTO {
    private final String name;
    private final int poplulation;
    private final List<PropertyDTO> properties;

    public EntityDTO(String name, int population, List<PropertyDTO> properties) {
        this.name = name;
        this.poplulation = population;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public int getPoplulation() {
        return poplulation;
    }

    public List<PropertyDTO> getProperties() {
        return properties;
    }
}