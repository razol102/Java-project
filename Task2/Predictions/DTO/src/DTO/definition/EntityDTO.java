package DTO.definition;

import java.util.Map;

public class EntityDTO {
    private final String name;
    private final Map<String, PropertyDTO> properties;

    public EntityDTO(String name, Map<String, PropertyDTO> properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }


    public Map<String, PropertyDTO> getProperties() {
        return properties;
    }
}