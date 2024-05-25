package DTO.definition;

public class PropertyDTO {
    private final String name;
    private final String type;
    private final Float from;
    private final Float to;
    private final Boolean isRandom;

    public PropertyDTO(String name, String type, Float from, Float to, Boolean isRandom) {
        this.name = name;
        this.type = type;
        this.from = from;
        this.to = to;
        this.isRandom = isRandom;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Float getFrom() {
        return from;
    }

    public Float getTo() {
        return to;
    }

    public String getIsRandom() {
        if(isRandom == true)
            return "The property is random";
        else
            return "The property is not random";
    }

    public String getRange() {
        if(from != null)
            return "Range: [" + from + ", " + to + "]";
        else
            return "None";
    }
}
