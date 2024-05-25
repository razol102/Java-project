package definition.rule.action.impl.withExpression.singleExpression.impl;

import definition.entity.EntityDef;
import definition.rule.action.api.Action;
import definition.rule.action.api.ActionType;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.singleExpression.SingleExpression;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import execution.context.impl.Context;
import execution.instance.entity.ins.api.EntityInstance;
import execution.instance.entity.ins.impl.Coordinate;

import java.util.List;

public abstract class ProximityAction extends SingleExpression {
    private final EntityDef targetEntity;
    private final Expression depth;
    private final List<Action> actions;

    public ProximityAction(EntityDef entityDef, SecondaryEntityDef secondaryEntity, EntityDef targetEntity, Expression depth, List<Action> actions, boolean actionOnSecondary) {
        super(entityDef, secondaryEntity, null, ActionType.PROXIMITY, depth, actionOnSecondary);
        this.targetEntity = targetEntity;
        this.depth = depth;
        this.actions = actions;
    }

    public Expression getDepth() {
        return depth;
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public void invoke(Context context) {

    }

    public void invokeWithSecondary(Context context, EntityInstance targetInstance) {
        EntityInstance sourceEntity = null;
        if(actionOnSecondary)
            sourceEntity = context.getSecondaryEntityInstance();
        else
            sourceEntity = context.getPrimaryEntityInstance();

        int depthOf = (int) depth.getValue(context);
        if (isNextTo(sourceEntity, targetInstance, depthOf)) // nizan !!!! maybe move the check to Simulation class
            for (Action action: actions)
                    action.invoke(context);
    }

    public boolean isNextTo(EntityInstance entity1, EntityInstance entity2, int circleRadius) {
        Coordinate entity1Coor = entity1.getCoordinate();
        Coordinate entity2Coor = entity2.getCoordinate();
        int dx = Math.abs(entity1Coor.getX() - entity2Coor.getX());
        int dy = Math.abs(entity1Coor.getY() - entity2Coor.getY());

        // Check if the entities are adjacent within the second circle (8 possible positions)
        return (dx <= circleRadius && dy <= circleRadius);
    }

    public EntityDef getTargetEntity() {
        return targetEntity;
    }
}
