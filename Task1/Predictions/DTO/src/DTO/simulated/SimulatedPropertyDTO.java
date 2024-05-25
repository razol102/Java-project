package DTO.simulated;

import java.util.List;

public class SimulatedPropertyDTO {
    private final String propertyName;

    private final List<String> values;
    public SimulatedPropertyDTO(String propertyName, List<String> values) {
        this.propertyName = propertyName;
        this.values = values;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public List<String> getValues() {
        return values;
    }
}
