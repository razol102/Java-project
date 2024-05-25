package DTO;

import java.util.List;

public class WorldDTO {
    private List<EnvironmentDTO> environments;
    private List<EntityDTO> entites;
    private List<RuleDTO> rules;
    private TerminationDTO terminator;

    public WorldDTO(List<EntityDTO> entites, List<RuleDTO> rules, TerminationDTO terminator) {
        this.entites = entites;
        this.rules = rules;
        this.terminator = terminator;
    }

    public List<EntityDTO> getEntites() {
        return entites;
    }
    public List<RuleDTO> getRules() {
        return rules;
    }
    public List<EnvironmentDTO> getEnvironments() {
        return environments;
    }

    public TerminationDTO getTermination() {
        return terminator;
    }
}
