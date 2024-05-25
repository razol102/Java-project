package DTO.definition.actionDTO.types.condition;

import DTO.definition.actionDTO.ActionDTO;

public class MultipleConditionDTO extends ActionDTO {
    private final String logical;
    private final String numOfConditions;
    private final String numOfThen;
    private final String numOfElse;

    public MultipleConditionDTO(String mainEntity, String secondaryEntity, String logical, String numOfConditions, String numOfThen, String numOfElse) {
        super("Condition", mainEntity, secondaryEntity);
        this.logical = logical;
        this.numOfConditions = numOfConditions;
        this.numOfThen = numOfThen;
        this.numOfElse = numOfElse;
    }

    public String getLogical() {
        return logical;
    }

    public String getNumOfConditions() {
        return numOfConditions;
    }

    public String getNumOfElse() {
        return numOfElse;
    }

    public String getNumOfThen() {
        return numOfThen;
    }

    public String getSingularity() {
        return "Multiple";
    }
}
