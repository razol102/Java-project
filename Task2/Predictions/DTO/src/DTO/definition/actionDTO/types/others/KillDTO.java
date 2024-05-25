package DTO.definition.actionDTO.types.others;

import DTO.definition.actionDTO.ActionDTO;

public class KillDTO extends ActionDTO{
    public KillDTO (String mainEntity, String secondaryEntity) {
        super("Kill", mainEntity, secondaryEntity);
    }
}
