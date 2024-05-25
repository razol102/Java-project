package world;

import definition.entity.Entity;
import definition.entity.EntityDef;
import definition.environment.impl.EnvironmentManagerImpl;
import definition.property.api.PropertyDef;
import definition.property.impl.BooleanPropertyDefinition;
import definition.property.impl.FloatPropertyDefinition;
import definition.property.impl.IntegerPropertyDefinition;
import definition.property.impl.StringPropertyDefinition;
import definition.rule.Rule;
import definition.rule.action.api.Action;
import definition.rule.action.impl.KillAction;
import definition.rule.action.impl.condition.ConditionAction;
import definition.rule.action.impl.condition.singularity.AbstractCondition;
import definition.rule.action.impl.condition.singularity.MultipleCondition;
import definition.rule.action.impl.condition.singularity.SingleCondition;
import definition.rule.action.impl.withExpression.calculation.api.CalculationAction;
import definition.rule.action.impl.withExpression.calculation.impl.CalculationDivide;
import definition.rule.action.impl.withExpression.calculation.impl.CalculationMultiply;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.BooleanFixed;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.FloatFixed;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.IntegerFixed;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.StringFixed;
import definition.rule.action.impl.withExpression.expression.impl.FunctionExpression;
import definition.rule.action.impl.withExpression.expression.impl.PropertyExpression;
import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.impl.EnvironmentFunction;
import definition.rule.action.impl.withExpression.functions.impl.RandomFunction;
import definition.rule.action.impl.withExpression.singleExpression.impl.DecreaseAction;
import definition.rule.action.impl.withExpression.singleExpression.impl.IncreaseAction;
import definition.rule.action.impl.withExpression.singleExpression.impl.SetAction;
import definition.terminatoin.Termination;
import definition.value.api.Value;
import definition.value.fixed.FixedValue;
import definition.value.random.impl.bool.BoolRand;
import definition.value.random.impl.numeric.FloatRand;
import definition.value.random.impl.numeric.IntegerRand;
import definition.value.random.impl.string.StringRand;
import generated.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class World {
    private EnvironmentManagerImpl environments;
    private Map<String, EntityDef> entities;
    private Map<String, Rule> rules;
    private Termination terminator;

    public World() {
        entities = new HashMap<>();
        rules = new HashMap<>();
        environments = new EnvironmentManagerImpl();
    }
    // ======= Environments =======
    public void setEnvironments(PRDEvironment prdEnvProperty) throws IllegalArgumentException{
        for(PRDEnvProperty envProperty: prdEnvProperty.getPRDEnvProperty()) {
            if(environments.getProps().containsKey(envProperty.getPRDName())) {
                throw new IllegalArgumentException("Environments must have unique names! Please insert a file with unique names to the environments.\n");
            }
            Float from = null;
            Float to = null;

            if(envProperty.getPRDRange() != null) {
                from = (float) envProperty.getPRDRange().getFrom();
                to = (float) envProperty.getPRDRange().getTo();
            }

            switch (envProperty.getType().toUpperCase()) {
                case "FLOAT":
                    Value<Float> floatValue = new FloatRand(from, to);
                    PropertyDef floatPropertyToAdd = new FloatPropertyDefinition(envProperty.getPRDName(), floatValue);
                    environments.addEnvironmentVariable(floatPropertyToAdd);
                    break;

                case "DECIMAL":
                    Value<Integer> integerValue = new IntegerRand(from, to);
                    PropertyDef integerPropertyToAdd = new IntegerPropertyDefinition(envProperty.getPRDName(), integerValue);
                    environments.addEnvironmentVariable(integerPropertyToAdd);
                    break;

                case "BOOLEAN":
                    Value<Boolean> boolValue = new BoolRand();
                    PropertyDef booleanPropertyDefinition = new BooleanPropertyDefinition(envProperty.getPRDName(), boolValue);
                    environments.addEnvironmentVariable(booleanPropertyDefinition);
                    break;

                case "STRING":
                    Value<String> stringValue = new StringRand();
                    PropertyDef stringPropertyToAdd = new StringPropertyDefinition(envProperty.getPRDName(), stringValue);
                    environments.addEnvironmentVariable(stringPropertyToAdd);
                    break;

                default:  // exception

                    break;
            }
        }
    }

    // ======= Entities =======
    public void setEntitines(PRDEntities prdEntities) {
        for (PRDEntity prdEntity : prdEntities.getPRDEntity()) {
            if (entities.containsKey(prdEntity.getName())) {
                throw new RuntimeException("Exception"); // exception
            }
            Map<String, PropertyDef> propertyDefList = getPRDProperties(prdEntity.getPRDProperties());
            EntityDef entityToAdd = new Entity(prdEntity.getName(), prdEntity.getPRDPopulation(), propertyDefList);
            entities.put(prdEntity.getName(), entityToAdd);
        }

    }
    public Map<String, PropertyDef> getPRDProperties(PRDProperties prdProperties) {
        Map<String, PropertyDef> res = new HashMap<>();
        for (PRDProperty prdProperty : prdProperties.getPRDProperty()) {
            if (res.containsKey(prdProperty.getPRDName())) {
                throw new IllegalArgumentException("The properties of each entity must have a unique name! Please insert a correct file.\n");
            }
            boolean init = prdProperty.getPRDValue().isRandomInitialize();

            Float from = null;
            Float to = null;
            if(prdProperty.getPRDRange() != null) {
                from = (float) prdProperty.getPRDRange().getFrom();
                to = (float) prdProperty.getPRDRange().getTo();
            }

            switch (prdProperty.getType().toUpperCase()) {
                case "FLOAT":
                    Value<Float> floatValue;
                    if (init)
                        floatValue = new FloatRand(from, to);
                    else
                        floatValue = new FixedValue<>(parseFloat(prdProperty.getPRDValue().getInit()), from, to);
                    PropertyDef floatPropertyToAdd = new FloatPropertyDefinition(prdProperty.getPRDName(), floatValue);
                    res.put(prdProperty.getPRDName(), floatPropertyToAdd);
                    break;
                case "DECIMAL":
                    Value<Integer> integerValue;
                    if (init)
                        integerValue = new IntegerRand(from, to);
                    else
                        integerValue = new FixedValue<>(parseInt(prdProperty.getPRDValue().getInit()), from, to);
                    PropertyDef integerPropertyToAdd = new IntegerPropertyDefinition(prdProperty.getPRDName(), integerValue);
                    res.put(prdProperty.getPRDName(), integerPropertyToAdd);
                    break;
                case "BOOLEAN":
                    Value<Boolean> boolValue;
                    if (init)
                        boolValue = new BoolRand();
                    else
                        boolValue = new FixedValue<>(Boolean.parseBoolean(prdProperty.getPRDValue().getInit()), null, null);
                    PropertyDef boolPropertyToAdd = new BooleanPropertyDefinition(prdProperty.getPRDName(), boolValue);
                    res.put(prdProperty.getPRDName(), boolPropertyToAdd);
                    break;
                case "STRING":
                    Value<String> stringValue;
                    if (init)
                        stringValue = new StringRand();
                    else
                        stringValue = new FixedValue<>(prdProperty.getPRDValue().getInit(), null, null);
                    PropertyDef stringPropertyToAdd = new StringPropertyDefinition(prdProperty.getPRDName(), stringValue);
                    res.put(prdProperty.getPRDName(), stringPropertyToAdd);
                    break;
                default:  // exception

                    break;
            }
        }
        return res;
    }

    // ======= Rules =======
    public void setRules (PRDRules prdRule){
        List<PRDRule> ruleList = prdRule.getPRDRule();
        for (PRDRule rule : ruleList) {
            Rule newRuleToAdd = createRule(rule);
            List<PRDAction> actionList = rule.getPRDActions().getPRDAction();
            for (PRDAction action : actionList) {
                Action actionToAdd = createAction(action);
                newRuleToAdd.addAction(actionToAdd);
            }
            rules.put(rule.getName(), newRuleToAdd);
        }
    }
    public Rule createRule(PRDRule rule) {
        Integer ticks = 1;
        Double probability = 1.0;
        String ruleName = rule.getName();
        if (rules.containsKey(ruleName))
            throw new RuntimeException();
        if(rule.getPRDActivation() != null) {    // exception
            ticks = rule.getPRDActivation().getTicks();
            if (ticks == null)
                ticks = 1;
            probability = rule.getPRDActivation().getProbability();
            if(probability == null)
                probability = 1.0;
        }

        return new Rule(ruleName, ticks, probability);
    }
    public Action createAction(PRDAction prdAction) {
        entityAndPropValidation(prdAction.getEntity(), prdAction.getProperty());
        Expression arg1, arg2;

        switch (prdAction.getType().toLowerCase()) {
            case "increase":
                arg1 = createExpression(prdAction.getBy(), prdAction.getEntity());
                typeCheck(arg1, entities.get(prdAction.getEntity()).getProps().get(prdAction.getProperty()));
                expressionCheck(arg1, "increase");
                return new IncreaseAction(entities.get(prdAction.getEntity()), prdAction.getProperty(), arg1);

            case "decrease":
                arg1 = createExpression(prdAction.getBy(), prdAction.getEntity());
                typeCheck(arg1, entities.get(prdAction.getEntity()).getProps().get(prdAction.getProperty()));
                expressionCheck(arg1, "decrease");
                return new DecreaseAction(entities.get(prdAction.getEntity()), prdAction.getProperty(), arg1);

            case "calculation":
                CalculationAction calculationAction;
                if (prdAction.getPRDDivide() != null) {
                    arg1 = createExpression(prdAction.getPRDDivide().getArg1(), prdAction.getEntity());
                    arg2 = createExpression(prdAction.getPRDDivide().getArg2(), prdAction.getEntity());
                    typeCheck(arg1, entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()));
                    typeCheck(arg2, entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()));
                    expressionCheck(arg1, "divide");
                    expressionCheck(arg2, "divide");
                    calculationAction = new CalculationDivide(entities.get(prdAction.getEntity()), prdAction.getResultProp(), arg1, arg2);
                } else {   // multiply:
                    arg1 = createExpression(prdAction.getPRDMultiply().getArg1(), prdAction.getEntity());
                    arg2 = createExpression(prdAction.getPRDMultiply().getArg2(), prdAction.getEntity());
                    typeCheck(arg1, entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()));
                    typeCheck(arg2, entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()));
                    expressionCheck(arg1, "multiply");
                    expressionCheck(arg2, "multiply");
                    calculationAction = new CalculationMultiply(entities.get(prdAction.getEntity()), prdAction.getResultProp(), arg1, arg2);
                }
                return calculationAction;

            case "condition":
                AbstractCondition condition = createCondition(prdAction.getPRDCondition());
                ConditionAction actionCondition = new ConditionAction (entities.get(prdAction.getEntity()), condition);

                List<Action> thenActions = prdAction.getPRDThen().getPRDAction().stream()
                        .map(this::createAction)
                        .collect(Collectors.toList());
                actionCondition.setThenActions(thenActions);

                if(prdAction.getPRDElse() != null) {
                    List<Action> elseActions = prdAction.getPRDElse().getPRDAction().stream()
                            .map(this::createAction)
                            .collect(Collectors.toList());
                    actionCondition.setElseActions(elseActions);
                }
                return actionCondition;

            case "set":
                arg1 = createExpression(prdAction.getValue(), prdAction.getEntity());
                typeCheck(arg1, entities.get(prdAction.getEntity()).getProps().get(prdAction.getProperty()));
                return new SetAction(entities.get(prdAction.getEntity()), prdAction.getProperty(), arg1);

            case "kill":
                return new KillAction(entities.get(prdAction.getEntity()));

/*            case "replace":
                return new KillAction(entities.get(prdAction.getEntity()));

            case "proximity":
                return new KillAction(entities.get(prdAction.getEntity()));*/

            default:
                throw new RuntimeException();       // exception
        }
    }
    public AbstractCondition createCondition (PRDCondition prdCondition) {
        AbstractCondition condition = null;

        if (prdCondition.getSingularity().equalsIgnoreCase("single")) {
            Expression arg = createExpression(prdCondition.getValue(), prdCondition.getEntity());
            typeCheck(arg, entities.get(prdCondition.getEntity()).getProps().get(prdCondition.getProperty()));
            String operator = prdCondition.getOperator().toLowerCase();
            condition = new SingleCondition(entities.get(prdCondition.getEntity()), prdCondition.getProperty(), operator, arg);
        } else {       // multiple:
            List<AbstractCondition> conditionList = prdCondition.getPRDCondition()
                    .stream()
                    .map(this::createCondition)
                    .collect(Collectors.toList());
            condition = new MultipleCondition(prdCondition.getLogical().toLowerCase(), conditionList);
        }
        return condition;
    }
    public void entityAndPropValidation (String entity, String property){      // exception
        EntityDef entityDef = entities.get(entity);
        if (entityDef == null)
            throw new IllegalArgumentException("actions must be defined on existing entities! Please insert a file with existing entities for the actions.\n");

        if (property != null) {
            PropertyDef propToCheck = entityDef.getProps().get(property);
            if (propToCheck == null)
                throw new IllegalArgumentException("actions must be defined on existing properties for the defined entity! Please insert a correct file.\n");
        }
    }

    public void expressionCheck(Expression arg, String actionType) {
        if(!((arg.getExpressionType().name().equalsIgnoreCase("Decimal")) || (arg.getExpressionType().name().equalsIgnoreCase("Float")))) {
            throw new IllegalArgumentException(actionType + " actions can work only on numeric expressions! Please insert a correct file.\n");
        }
    }
    public void typeCheck(Expression arg, PropertyDef property) {
        switch (property.getType().name().toUpperCase()){
            case "DECIMAL":
                if(!(arg.getExpressionType().name().equalsIgnoreCase("Decimal")))
                    throw new IllegalArgumentException("decimal properties should get decimal expressions! Please insert a correct file.\n");
                break;
            case "FLOAT":
                if(!(arg.getExpressionType().name().equalsIgnoreCase("Decimal")))
                    if(!(arg.getExpressionType().name().equalsIgnoreCase("Float")))
                        throw new IllegalArgumentException("numeric properties should get numeric expressions! Please insert a correct file.\n");
                break;
            case "BOOLEAN":
                if(!(arg.getExpressionType().name().equalsIgnoreCase("Boolean")))
                    throw new IllegalArgumentException("\n");
                break;
            case "STRING":
                if(!(arg.getExpressionType().name().equalsIgnoreCase("String")))
                    throw new IllegalArgumentException("\n");
                break;
            default:
                throw new IllegalArgumentException("\n");
        }
    }

    public Expression createExpression(String expressionStr, String entityStr) {
        Expression res = null;
        EntityDef entityDef = entities.get(entityStr);
        PropertyDef propertyDef = entityDef.getProps().get(expressionStr);

        if (expressionStr.contains("(") && expressionStr.contains(")")) {
            res = createFunctionExp(expressionStr);
        }
        else if (propertyDef != null)
            res = new PropertyExpression(propertyDef);
        else
            res = fixedExp(expressionStr);
        return res;
    }

    public FunctionExpression createFunctionExp(String expressionStr) {

        int opener = expressionStr.indexOf("(");
        int closer = expressionStr.indexOf(")");
        String expressionType = expressionStr.substring(0, opener);
        String funcArg = expressionStr.substring(opener + 1, closer);

        switch (expressionType.toUpperCase()) {
            case "ENVIRONMENT":
                if (!(environments.getProps().containsKey(funcArg)))
                    throw new IllegalArgumentException("the function 'Environment' must get an existing environment! Please enter a new file.\n");
                Function envFunc = new EnvironmentFunction(funcArg);
                return new FunctionExpression(envFunc, environments.getProps().get(funcArg).getType().name());
            case "RANDOM":
                Integer num = Integer.parseInt(funcArg); // exception if value is not a decimal number :    catch (NumberFormatException e)
                Function randFunc = new RandomFunction(num);
                return new FunctionExpression(randFunc, "DECIMAL");
                   /* case "EVALUATE":
                        if (value.contains(".")) { // <entity>.<property name>
                            int dotIndex = byExpressionStr.indexOf(".");
                            String entityEvaluate = byExpressionStr.substring(0, dotIndex);
                            // if (entityEvaluate.equals(context.getPrimaryEntityInstance()))
                        }
                        break;
                    case "PERCENT":
                        if (value.contains(",")) {
                            int commaIndex = byExpressionStr.indexOf(",");
                            String wholeStr = value.substring(0, commaIndex);
                            Float.parseFloat(wholeStr); // exception if value is not a float number
                            String partStr = value.substring(commaIndex + 1);
                            Float.parseFloat(partStr); // exception if value is not a float number
                            isValid = true;
                        }
                        break;
                    case "TICKS":
                        if (value.contains(".")) { // <entity>.<property name>
                            int dotIndex = byExpressionStr.indexOf(".");
                            String entityEvaluate = byExpressionStr.substring(0, dotIndex);
                            // if (entityEvaluate.equals(context.getPrimaryEntityInstance()))
                        }
                        break;*/

        }
        return null;
    }

    public Expression fixedExp(String expressionStr) {      // exception
        try{
            Integer exp = Integer.parseInt(expressionStr);
            return new IntegerFixed(exp);
        } catch (NumberFormatException ignored){
            try {
                Float exp = Float.parseFloat(expressionStr);
                return new FloatFixed(exp);
            } catch (NumberFormatException ignored1){
                    if (expressionStr.equalsIgnoreCase("true"))
                        return new BooleanFixed(true);
                    else if (expressionStr.equalsIgnoreCase("false"))
                        return new BooleanFixed(false);
                    else
                        return new StringFixed(expressionStr);
            }
        }
    }

    // ======= Termination =======
    public void setTerminator(PRDTermination prdTermination) {
        int ticksCount = 0, secondsCount = 0;
        boolean ticksDefined = false;
        boolean secondsDefined = false;

        for (Object terminator : prdTermination.getPRDByTicksOrPRDBySecond()) {
            if (terminator instanceof PRDByTicks) {
                if (terminator != null) {
                    ticksCount = ((PRDByTicks) terminator).getCount();
                    ticksDefined = true;
                }
            }
            else {
                if(terminator != null) {
                    secondsCount = ((PRDBySecond) terminator).getCount();
                    secondsDefined = true;
                }
            }
        }
        terminator = new Termination(ticksCount, secondsCount, secondsDefined, ticksDefined);
    }



    public Map<String, EntityDef> getEntities(){
        return entities;
    }
    public EnvironmentManagerImpl getEnvironments(){
        return environments;
    }
    public Map<String, Rule> getRules(){
        return rules;
    }
    public Termination getTermination(){
        return terminator;
    }

    public void transferDataFromXMLToEngine(PRDWorld prdWorld) {
        // Environment:
        setEnvironments(prdWorld.getPRDEvironment());
        // Entities (including their properties):
        setEntitines(prdWorld.getPRDEntities());
        // Rules (including actions):
        setRules(prdWorld.getPRDRules());
        // Termination:
        setTerminator(prdWorld.getPRDTermination());
    }
}






