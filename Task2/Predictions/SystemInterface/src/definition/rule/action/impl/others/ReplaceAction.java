package definition.rule.action.impl.others;

import definition.entity.EntityDef;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import execution.context.impl.Context;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.ins.impl.Coordinate;
import execution.instance.property.api.PropertyInstance;

import java.util.Map;

public class ReplaceAction extends Action {
    private final String mode;
    private final EntityDef entityToCreate;

    public ReplaceAction(EntityDef entityToKill, EntityDef entityToCreate, SecondaryEntityDef secondaryEntity, String mode, boolean actionOnSecondary) {
        super(entityToKill, secondaryEntity, ActionType.REPLACE, actionOnSecondary);
        this.mode = mode;
        this.entityToCreate = entityToCreate;
    }
    public String getMode() {
        return mode;
    }

    public EntityDef getEntityToCreate() {
        return entityToCreate;
    }

    @Override
    public void invoke(Context context) {
        EntityInstance entityInstanceToKill = null;
        if(actionOnSecondary)
            entityInstanceToKill = context.getSecondaryEntityInstance();
        else
            entityInstanceToKill = context.getPrimaryEntityInstance();

        Coordinate coordinate = new Coordinate(entityInstanceToKill.getCoordinate().getX(), context.getPrimaryEntityInstance().getCoordinate().getY());
        EntityInstance newEntity =  context.getEntityInstanceManager().createInstance(entityToCreate);
        newEntity.setCoordinate(coordinate);
        context.getEntityInstanceManager().addInstanceToAdd(newEntity);

        if(mode.equalsIgnoreCase("derived")) {
            for (Map.Entry<String, PropertyInstance> killedProperty: entityInstanceToKill.getProperties().entrySet()) {
                PropertyInstance propertyToUpdate = newEntity.getPropertyByName(killedProperty.getKey());
                if(propertyToUpdate != null)
                    if(killedProperty.getValue().getPropertyDef().getType().equals(propertyToUpdate.getPropertyDef().getType())) // type check
                        propertyToUpdate.updateValue(killedProperty.getValue().getValue());
            }
            entityInstanceToKill.killMe();
        }
    }
}

