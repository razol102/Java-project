package DTO.simulated;

import java.util.Map;

public class ActiveEnvironmentsDTO {
    Map<String, ActiveEnvironmentDTO> activeEnvironments;

    public ActiveEnvironmentsDTO(Map<String, ActiveEnvironmentDTO> activeEnvironments) {
        this.activeEnvironments = activeEnvironments;
    }

    public Map<String, ActiveEnvironmentDTO> getActiveEnvironments() {
        return activeEnvironments;
    }
}
