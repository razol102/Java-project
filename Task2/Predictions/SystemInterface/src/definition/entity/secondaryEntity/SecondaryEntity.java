package definition.entity.secondaryEntity;

import definition.entity.EntityDef;
import definition.rule.action.impl.condition.singularity.AbstractCondition;

public class SecondaryEntity implements SecondaryEntityDef{
    private final EntityDef entityDef;
    private final int count;
    private final AbstractCondition condition;

    public SecondaryEntity(EntityDef entityDef, int count, AbstractCondition condition) {
        this.entityDef = entityDef;
        this.condition = condition;
        this.count = count;
    }

    @Override
    public EntityDef getEntityDef() {
        return entityDef;
    }
    @Override
    public int getCount() {
        return count;
    }
    @Override
    public AbstractCondition getCondition() {
        return condition;
    }
    @Override
    public String getName() {
        return entityDef.getName();
    }
}
