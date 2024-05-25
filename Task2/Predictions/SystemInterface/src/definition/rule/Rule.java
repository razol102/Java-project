package definition.rule;

import definition.rule.action.api.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rule implements RuleDef {
    private final String name;
    private final Integer ticks;
    private final double probability;
    private final List<Action> activitiesNames;


    public Rule(String name, Integer ticks, double probability) {
        this.name = name;
        this.ticks = ticks;
        this.probability = probability;
        activitiesNames = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isActivated(long ticksNum) {
        if (ticksNum % ticks == 0){
            Random random = new Random();
            double rand = (random.nextDouble());
            return rand < probability;
        }
        return false;
    }

    @Override
    public void addAction(Action action) {
        activitiesNames.add(action);
    }

    @Override
    public List<Action> getActionsToPerform() {
        return activitiesNames;
    }

    @Override
    public long getTicks() {
        return ticks;
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
