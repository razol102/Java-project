package DTO.definition.actionDTO;

public class ActionDTO {

    private final String actionType;
    private final String mainEntity;
    private final String secondaryEntity;


    public ActionDTO(String actionType, String mainEntity, String secondaryEntity) {
        this.actionType = actionType;
        this.mainEntity = mainEntity;
        this.secondaryEntity = secondaryEntity;
    }

    public String getActionType() {
        return actionType;
    }
    public String getMainEntity() {
        return mainEntity;
    }
    public String getSecondaryEntity() {
        return secondaryEntity;
    }
}
