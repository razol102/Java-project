package DTO.definition;

import DTO.definition.actionDTO.ActionDTO;

import java.util.List;

public class RuleDTO {
    private final String name;
    private final long ticks;
    private final double probability;
    private final List<ActionDTO> actions;

    public RuleDTO(String name, long ticks, double probability, List<ActionDTO> actions) {
        this.name = name;
        this.ticks = ticks;
        this.probability = probability;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }
    public double getProbability() {
        return probability;
    }

    public long getTicks() {
        return ticks;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }
}
