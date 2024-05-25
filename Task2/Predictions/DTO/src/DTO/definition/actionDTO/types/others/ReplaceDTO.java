package DTO.definition.actionDTO.types.others;

import DTO.definition.actionDTO.ActionDTO;

public class ReplaceDTO  extends ActionDTO {
    private final String create;
    private final String mode;
    public ReplaceDTO(String mainEntity, String secondaryEntity, String create, String mode) {
        super("Replace", mainEntity, secondaryEntity);
        this.create = create;
        this.mode = mode;
    }

    public String getCreate() {
        return create;
    }

    public String getMode() {
        return mode;
    }
}
