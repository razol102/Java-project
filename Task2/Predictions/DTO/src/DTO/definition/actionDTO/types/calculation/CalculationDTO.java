package DTO.definition.actionDTO.types.calculation;

import DTO.definition.actionDTO.ActionDTO;

public class CalculationDTO extends ActionDTO {
    private final String resultProp;
    private final String calculationType;  // divide or multiply
    private final String arg1;
    private final String arg2;

    public CalculationDTO(String mainEntity, String secondaryEntity, String resultProp, String calculationType, String arg1, String arg2) {
        super("Calculation", mainEntity, secondaryEntity);
        this.resultProp = resultProp;
        this.calculationType = calculationType;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public String getArg1() {
        return arg1;
    }
    public String getArg2() {
        return arg2;
    }
    public String getCalculationType() {
        return calculationType;
    }
    public String getResultProp() {
        return resultProp;
    }
}
