package DTO.simulated;

public class SimulatedPropertyDTO {
    private final String propertyName;
    private final String propertyType;
    private final Object value;
    private final Integer tick;

    public SimulatedPropertyDTO(String propertyName, String propertyType, Object value, Integer tick) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.value = value;
        this.tick = tick;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public Object getValue() {
        return value;
    }

    public Integer getTick() {
        return tick;
    }
}
