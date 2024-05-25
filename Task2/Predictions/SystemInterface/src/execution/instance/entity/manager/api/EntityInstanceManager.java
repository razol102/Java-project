package execution.instance.entity.manager.api;

import definition.entity.EntityDef;
import execution.instance.entity.ins.api.EntityInstance;

import java.util.List;

public interface EntityInstanceManager {

    EntityInstance createInstance(EntityDef entityDef);

    List<EntityInstance> getInstances();

    List<String> getPropertyNames();

    int getInitPopulation();

    void killingSpree();

    void addInstance(EntityInstance instance);

    void addInstanceToAdd(EntityInstance instance);

    void clearInstanceReplacements();
    void concatInstances();
}
