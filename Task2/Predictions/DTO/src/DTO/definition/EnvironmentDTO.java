package DTO.definition;

public class EnvironmentDTO {
    private final String name;
    private final String type;
    private final String from;
    private final String to;

    public EnvironmentDTO(String name, String type, String from, String to) {
        this.name = name;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Float getFrom() {
        return Float.valueOf(from);
    }

    public Float getTo() {
        return Float.valueOf(to);
    }
    public String getRange() {
        if(from != null)
            return "Range: [" + from + ", " + to + "]";
        else
            return "No range for this environment";
    }
}
