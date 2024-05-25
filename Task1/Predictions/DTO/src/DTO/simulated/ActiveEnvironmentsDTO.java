package DTO.simulated;

import java.util.Map;

public class ActiveEnvironmentsDTO {
    Map<String, Object> activeEnvironments;

    public ActiveEnvironmentsDTO(Map<String, Object> activeEnvironments) {
        this.activeEnvironments = activeEnvironments;
    }

    public Map<String, Object> getActiveEnvironments() {
        return activeEnvironments;
    }
}
