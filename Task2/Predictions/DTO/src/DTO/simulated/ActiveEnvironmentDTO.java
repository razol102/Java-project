package DTO.simulated;

public class ActiveEnvironmentDTO {
    private final String type;
    private final String value;

    public ActiveEnvironmentDTO(String type, String value) {
        this.type = type;
        String capitalizedValue = null;
        if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            capitalizedValue = value.substring(0, 1).toUpperCase() + value.substring(1);
            this.value = capitalizedValue;
        }
        else
            this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
