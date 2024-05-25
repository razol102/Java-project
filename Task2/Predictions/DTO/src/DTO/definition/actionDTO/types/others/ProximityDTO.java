package DTO.definition.actionDTO.types.others;

import DTO.definition.actionDTO.ActionDTO;

public class ProximityDTO  extends ActionDTO {
    private final String targetEntity;
    private final String depth;
    private final String numOfActions;

    public ProximityDTO(String mainEntity, String secondaryEntity, String targetEntity, String depth, String numOfActions) {
        super("Proximity", mainEntity, secondaryEntity);
        this.targetEntity = targetEntity;
        this.depth = depth;
        this.numOfActions = numOfActions;
    }

    public String getTargetEntity() {
        return targetEntity;
    }
    public String getDepth() {
        return depth;
    }

    public String getNumOfActions() {
        return numOfActions;
    }
}
