package DTO.definition.actionDTO.types.condition;

import DTO.definition.actionDTO.ActionDTO;

public class SingleConditionDTO extends ActionDTO {
    private final String operand;
    private final String operator;
    private final String value;
    private final String numOfThen;
    private final String numOfElse;

    public SingleConditionDTO(String mainEntity, String secondaryEntity, String operand, String operator, String value, String numOfThen, String numOfElse) {
        super("Condition", mainEntity, secondaryEntity);
        this.operand = operand;
        this.operator = operator;
        this.value = value;
        this.numOfThen = numOfThen;
        this.numOfElse = numOfElse;
    }

    public String getOperand() {
        return operand;
    }

    public String getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }

    public String getNumOfThen() {
        return numOfThen;
    }

    public String getNumOfElse() {
        return numOfElse;
    }

    public String getSingularity() {
        return "Single";
    }
}
