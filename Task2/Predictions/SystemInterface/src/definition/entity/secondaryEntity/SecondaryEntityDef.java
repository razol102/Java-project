package definition.entity.secondaryEntity;

import definition.entity.EntityDef;
import definition.rule.action.impl.condition.singularity.AbstractCondition;

public interface SecondaryEntityDef {
    EntityDef getEntityDef();
    int getCount();
    AbstractCondition getCondition();
    String getName();

}
