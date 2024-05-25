package DTO.definition;

import java.util.Map;

public class WorldDTO {
    private final Map<String, EnvironmentDTO> environments;
    private final Map<String, EntityDTO> entities;
    private final Map<String, RuleDTO> rules;
    private final TerminationDTO terminator;
    private final GridDTO grid;

    private final int worldPopulation;

    public WorldDTO(Map<String, EntityDTO> entities, Map<String, RuleDTO> rules, Map<String, EnvironmentDTO> environments, TerminationDTO terminator, GridDTO grid) {
        this.entities = entities;
        this.rules = rules;
        this.terminator = terminator;
        this.grid = grid;
        this.environments = environments;
        worldPopulation = Integer.parseInt(grid.getColumns()) * Integer.parseInt(grid.getRows());
    }

    public Map<String, EntityDTO> getEntites() {
        return entities;
    }
    public Map<String, RuleDTO> getRules() {
        return rules;
    }
    public Map<String, EnvironmentDTO> getEnvironments() {
        return environments;
    }
    public TerminationDTO getTermination() {
        return terminator;
    }

    public GridDTO getGrid() {
        return grid;
    }

    public int getWorldPopulation() {
        return worldPopulation;
    }
}
