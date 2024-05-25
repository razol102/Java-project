package DTO;

import java.util.List;

public class RuleDTO {
    private final String name;
    private final long ticks;
    private final double propability;
    private final List<String> actionNames;

    public RuleDTO(String name, long ticks, double propability, List<String> actionNames) {
        this.name = name;
        this.ticks = ticks;
        this.propability = propability;
        this.actionNames = actionNames;
    }

    public String getName() {
        return name;
    }
    public double getPropability() {
        return propability;
    }

    public long getTicks() {
        return ticks;
    }

    public List<String> getActionNames() {
        return actionNames;
    }
}
