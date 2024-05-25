package DTO.definition.actionDTO.types.singleExpression;

import DTO.definition.actionDTO.ActionDTO;

public class SingleExpressionDTO extends ActionDTO {        // condition dto for increase, decrease and set actions
    private final String actionType;
    private final String propertyName;
    private final String arg;

    public SingleExpressionDTO(String actionType, String mainEntity, String secondaryEntity, String propertyName, String arg) {
        super(actionType, mainEntity, secondaryEntity);
        this.actionType = actionType;
        this.propertyName = propertyName;
        this.arg = arg;
    }

    @Override
    public String getActionType() {
        return actionType;
    }

    public String getProperty() {
        return propertyName;
    }

    public String getBy() {
        return arg;
    }
}
