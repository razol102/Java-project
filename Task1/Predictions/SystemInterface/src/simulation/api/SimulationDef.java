package simulation.api;

import execution.instance.entity.manager.api.EntityInstanceManager;

import java.util.Map;

public interface SimulationDef {

    String getTimeFormat();
    int getId();
    Map<String, EntityInstanceManager> getEntityNameToInstanceManager();
}
