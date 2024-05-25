package definition.rule;

import definition.rule.action.api.Action;
import java.util.List;

public interface RuleDef {
    String getName();
    boolean isActivated(long ticksNum);
    List<Action> getActionsToPerform();
    void addAction(Action action);
    long getTicks();
    double getProbability();
}
