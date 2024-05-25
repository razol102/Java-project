package world;

import definition.entity.Entity;
import definition.entity.EntityDef;
import definition.entity.secondaryEntity.SecondaryEntity;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import definition.environment.impl.EnvironmentManagerImpl;
import definition.property.api.PropertyDef;
import definition.property.impl.BooleanPropertyDefinition;
import definition.property.impl.FloatPropertyDefinition;
import definition.property.impl.IntegerPropertyDefinition;
import definition.property.impl.StringPropertyDefinition;
import definition.rule.Rule;
import definition.rule.action.api.Action;
import definition.rule.action.impl.condition.ConditionAction;
import definition.rule.action.impl.condition.singularity.AbstractCondition;
import definition.rule.action.impl.condition.singularity.MultipleCondition;
import definition.rule.action.impl.condition.singularity.SingleCondition;
import definition.rule.action.impl.others.KillAction;
import definition.rule.action.impl.others.ReplaceAction;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.BooleanFixed;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.FloatFixed;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.IntegerFixed;
import definition.rule.action.impl.withExpression.expression.impl.Fixed.impl.StringFixed;
import definition.rule.action.impl.withExpression.expression.impl.FunctionExpression;
import definition.rule.action.impl.withExpression.expression.impl.PropertyExpression;
import definition.rule.action.impl.withExpression.functions.api.Function;
import definition.rule.action.impl.withExpression.functions.impl.*;
import definition.rule.action.impl.withExpression.multiExpression.calculation.CalculationAction;
import definition.rule.action.impl.withExpression.multiExpression.impl.CalculationDivide;
import definition.rule.action.impl.withExpression.multiExpression.impl.CalculationMultiply;
import definition.rule.action.impl.withExpression.singleExpression.impl.DecreaseAction;
import definition.rule.action.impl.withExpression.singleExpression.impl.IncreaseAction;
import definition.rule.action.impl.withExpression.singleExpression.impl.ProximityAction;
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

public class World implements WorldDef{
    private final EnvironmentManagerImpl environments;
    private final Map<String, EntityDef> entities;
    private final Map<String, Rule> rules;
    private Termination terminator;
    private int rows, columns;
    private int worldPopulation = 0;
    public World() {
        entities = new HashMap<>();
        rules = new HashMap<>();
        environments = new EnvironmentManagerImpl();
    }

    // ======= Environments =======
    @Override
    public void setEnvironments(PRDEnvironment prdEnvProperty) throws IllegalArgumentException{
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

                default:
            }
        }
    }

    // ======= Entities =======
    @Override
    public void setEntitines(PRDEntities prdEntities) {

        for (PRDEntity prdEntity : prdEntities.getPRDEntity()) {
            Map<String, PropertyDef> propertyDefList = getPRDProperties(prdEntity.getPRDProperties());
            EntityDef entityToAdd = new Entity(prdEntity.getName(), propertyDefList);
            entities.put(prdEntity.getName(), entityToAdd);
        }

    }

    @Override
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
                default:
            }
        }
        return res;
    }

    // ======= Rules =======
    @Override
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

    @Override
    public Rule createRule(PRDRule rule) {
        Integer ticks = 1;
        Double probability = 1.0;
        String ruleName = rule.getName();
        if(rule.getPRDActivation() != null) {
            ticks = rule.getPRDActivation().getTicks();
            if (ticks == null)
                ticks = 1;
            probability = rule.getPRDActivation().getProbability();
            if(probability == null)
                probability = 1.0;
        }

        return new Rule(ruleName, ticks, probability);
    }

    @Override
    public Action createAction(PRDAction prdAction) {
        if(!prdAction.getType().equalsIgnoreCase("proximity"))
            if(!prdAction.getType().equalsIgnoreCase("replace"))
                entityAndPropValidation(prdAction.getEntity(), prdAction.getProperty());
        Expression arg1, arg2;
        boolean isActionOnSecondaryEntity = false;
        SecondaryEntityDef secondaryEntityDef = null;
        if(prdAction.getPRDSecondaryEntity() != null){
            secondaryEntityDef = createSecondaryEntity(prdAction.getPRDSecondaryEntity());
            isActionOnSecondaryEntity = isActionOnSecondaryEntity(entities.get(prdAction.getEntity()).getName(), secondaryEntityDef.getName());
        }
        switch (prdAction.getType().toLowerCase()) {
            case "increase":
                arg1 = createExpression(prdAction.getBy(), prdAction.getEntity());
                typeCheck(arg1.getExpressionType().name(), entities.get(prdAction.getEntity()).getProps().get(prdAction.getProperty()).getType().name());
                expressionCheck(arg1, "increase");
                return new IncreaseAction(entities.get(prdAction.getEntity()), secondaryEntityDef, prdAction.getProperty(), arg1, isActionOnSecondaryEntity);

            case "decrease":
                arg1 = createExpression(prdAction.getBy(), prdAction.getEntity());
                typeCheck(arg1.getExpressionType().name(), entities.get(prdAction.getEntity()).getProps().get(prdAction.getProperty()).getType().name());
                expressionCheck(arg1, "decrease");
                return new DecreaseAction(entities.get(prdAction.getEntity()), secondaryEntityDef, prdAction.getProperty(), arg1, isActionOnSecondaryEntity);

            case "calculation":
                CalculationAction calculationAction;
                if (prdAction.getPRDDivide() != null) {
                    arg1 = createExpression(prdAction.getPRDDivide().getArg1(), prdAction.getEntity());
                    arg2 = createExpression(prdAction.getPRDDivide().getArg2(), prdAction.getEntity());
                    typeCheck(arg1.getExpressionType().name(), entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()).getType().name());
                    typeCheck(arg2.getExpressionType().name(), entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()).getType().name());
                    expressionCheck(arg1, "divide");
                    expressionCheck(arg2, "divide");
                    calculationAction = new CalculationDivide(entities.get(prdAction.getEntity()), secondaryEntityDef, prdAction.getResultProp(), arg1, arg2, isActionOnSecondaryEntity);
                } else {   // multiply:
                    arg1 = createExpression(prdAction.getPRDMultiply().getArg1(), prdAction.getEntity());
                    arg2 = createExpression(prdAction.getPRDMultiply().getArg2(), prdAction.getEntity());
                    typeCheck(arg1.getExpressionType().name(), entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()).getType().name());
                    typeCheck(arg2.getExpressionType().name(), entities.get(prdAction.getEntity()).getProps().get(prdAction.getResultProp()).getType().name());
                    expressionCheck(arg1, "multiply");
                    expressionCheck(arg2, "multiply");
                    calculationAction = new CalculationMultiply(entities.get(prdAction.getEntity()), secondaryEntityDef, prdAction.getResultProp(), arg1, arg2, isActionOnSecondaryEntity);
                }
                return calculationAction;

            case "condition":
                AbstractCondition condition = createCondition(prdAction.getPRDCondition());
                ConditionAction actionCondition = new ConditionAction(entities.get(prdAction.getEntity()), secondaryEntityDef, condition, isActionOnSecondaryEntity);

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
                typeCheck(arg1.getExpressionType().name(), entities.get(prdAction.getEntity()).getProps().get(prdAction.getProperty()).getType().name());
                return new SetAction(entities.get(prdAction.getEntity()), secondaryEntityDef, prdAction.getProperty(), arg1, isActionOnSecondaryEntity);

            case "kill":
                return new KillAction(entities.get(prdAction.getEntity()), secondaryEntityDef, isActionOnSecondaryEntity);

            case "replace":
                String mode = prdAction.getMode();
                if (!(mode.equalsIgnoreCase("scratch") || mode.equalsIgnoreCase("derived")))
                    throw new IllegalArgumentException("Inserted mode is illegal, insert scratch or derived");

                String entityToKill = prdAction.getKill();
                entityValidation(entityToKill);
                String entityToCreate = prdAction.getCreate();
                entityValidation(entityToCreate);
                return new ReplaceAction(entities.get(entityToKill), entities.get(entityToCreate), secondaryEntityDef, mode, isActionOnSecondaryEntity);

            case "proximity":
                String sourceEntity = prdAction.getPRDBetween().getSourceEntity();
                entityValidation(sourceEntity);
                String targetEntity = prdAction.getPRDBetween().getTargetEntity();
                entityValidation(targetEntity);
                arg1 = createExpression(prdAction.getPRDEnvDepth().getOf(), sourceEntity);
                expressionCheck(arg1, "proximity");
                List<Action> proxActions = null;
                if(prdAction.getPRDActions() != null) {
                    proxActions = prdAction.getPRDActions().getPRDAction().stream()
                            .map(this::createAction)
                            .collect(Collectors.toList());
                }
                return new ProximityAction(entities.get(sourceEntity), secondaryEntityDef, entities.get(targetEntity), arg1, proxActions, isActionOnSecondaryEntity) {
                };
        }
        return null;
    }
    public boolean isActionOnSecondaryEntity(String currEntity, String secondaryEntity) {
        return (currEntity.equalsIgnoreCase(secondaryEntity));
    }
    @Override
    public SecondaryEntityDef createSecondaryEntity(PRDAction.PRDSecondaryEntity prdSecondaryEntity){
        String secondaryEntityStr = prdSecondaryEntity.getEntity();
        EntityDef entityDef = entities.get(secondaryEntityStr);
        if(entityDef != null) {
            AbstractCondition condition = createCondition(prdSecondaryEntity.getPRDSelection().getPRDCondition());
            int countNum = getCountNum(prdSecondaryEntity.getPRDSelection().getCount());
            return new SecondaryEntity(entities.get(secondaryEntityStr), countNum, condition);
        }
        else
            throw new IllegalArgumentException("Secondary entity doesn't exist!");
    }

    @Override
    public int getCountNum(String countStr) {
        if(countStr.equalsIgnoreCase("ALL"))
            return 0;
        else if (countStr.matches("\\d+")) {
            int res = Integer.parseInt(countStr);
            if(res < 1)
                throw new IllegalArgumentException("Count for secondary entity must be positive integer or an integer or a string 'ALL'!!");
            return res;
        }
        else
            throw new IllegalArgumentException("Count for secondary entity must be positive integer or an integer or a string 'ALL'!!");
    }

    @Override
    public AbstractCondition createCondition (PRDCondition prdCondition) {
        AbstractCondition condition;

        if (prdCondition.getSingularity().equalsIgnoreCase("single")) {
            Expression arg = createExpression(prdCondition.getValue(), prdCondition.getEntity());
            Expression operand = createExpression(prdCondition.getProperty(), prdCondition.getEntity());
            typeCheck(arg.getExpressionType().name(), operand.getExpressionType().name());
            String operator = prdCondition.getOperator().toLowerCase();
            condition = new SingleCondition(entities.get(prdCondition.getEntity()), operand, operator, arg);
        } else {       // multiple:
            List<AbstractCondition> conditionList = prdCondition.getPRDCondition()
                    .stream()
                    .map(this::createCondition)
                    .collect(Collectors.toList());
            condition = new MultipleCondition(prdCondition.getLogical().toLowerCase(), conditionList);
        }
        return condition;
    }

    @Override
    public void entityAndPropValidation (String entity, String property){
        EntityDef entityDef = entities.get(entity);
        if (entityDef == null)
            throw new IllegalArgumentException("Actions must be defined on existing entities! Please insert a file with existing entities for the actions.\n");

        if (property != null) {
            PropertyDef propToCheck = entityDef.getProps().get(property);
            if (propToCheck == null)
                throw new IllegalArgumentException("Actions must be defined on existing properties for the defined entity! Please insert a correct file.\n");
        }
    }

    @Override
    public void entityValidation (String entity) {
        EntityDef entityDef = entities.get(entity);
        if (entityDef == null)
            throw new IllegalArgumentException("actions must be defined on existing entities! Please insert a file with existing entities for the actions.\n");
    }

    @Override
    public void expressionCheck(Expression arg, String actionType) {
        if((!(arg.getExpressionType().name().equalsIgnoreCase("Decimal")) && (!arg.getExpressionType().name().equalsIgnoreCase("Float")))) {
            throw new IllegalArgumentException(actionType + " actions can work only on numeric expressions! Please insert a correct file.\n");
        }
    }

    @Override
    public void typeCheck(String type1, String type2) {
        switch (type2.toUpperCase()){
            case "DECIMAL":
                if(!(type1.equalsIgnoreCase("Decimal")))
                    throw new IllegalArgumentException("decimal properties should get decimal expressions! Please insert a correct file.\n");
                break;
            case "FLOAT":
                if(!(type1.equalsIgnoreCase("Decimal")))
                    if(!(type1.equalsIgnoreCase("Float")))
                        throw new IllegalArgumentException("numeric properties should get numeric expressions! Please insert a correct file.\n");
                break;
            case "BOOLEAN":
                if(!(type1.equalsIgnoreCase("Boolean")))
                    throw new IllegalArgumentException("\n");
                break;
            case "STRING":
                if(!(type1.equalsIgnoreCase("String")))
                    throw new IllegalArgumentException("\n");
                break;
            default:
                throw new IllegalArgumentException("\n");
        }
    }

    @Override
    public Expression createExpression(String expressionStr, String entityStr) {
        Expression res = null;
        EntityDef entityDef = entities.get(entityStr);
        PropertyDef propertyDef = entityDef.getProps().get(expressionStr);

        if (expressionStr.contains("(") && expressionStr.contains(")")) {
            res = createFunctionExp(expressionStr, entityStr);
        }
        else if (propertyDef != null)
            res = new PropertyExpression(propertyDef, expressionStr);
        else
            res = fixedExp(expressionStr);
        return res;
    }

    @Override
    public FunctionExpression createFunctionExp(String expressionStr, String entityStr) {

        int opener = expressionStr.indexOf("(");
        int closer = expressionStr.length() - 1;
        String expressionType = expressionStr.substring(0, opener);
        String funcArg = expressionStr.substring(opener + 1, closer);

        switch (expressionType.toUpperCase()) {
            case "ENVIRONMENT":
                if (!(environments.getProps().containsKey(funcArg)))
                    throw new IllegalArgumentException("the function 'Environment' must get an existing environment! Please enter a new file.\n");
                Function envFunc = new EnvironmentFunction(funcArg);
                return new FunctionExpression(envFunc, environments.getProps().get(funcArg).getType().name(), expressionStr);

            case "RANDOM":
                Integer num = Integer.parseInt(funcArg);
                Function randFunc = new RandomFunction(num);
                return new FunctionExpression(randFunc, "DECIMAL", expressionStr);

            case "EVALUATE":
                int dotIndex = funcArg.indexOf(".");
                String entityEvaluate = funcArg.substring(0, dotIndex);
                String propertyEvaluate = funcArg.substring(dotIndex + 1, funcArg.length());

                if (!entities.containsKey(entityEvaluate))
                    throw new IllegalArgumentException("The 'Evaluate' function referred to a not existing entity!");
                if (!entities.get(entityEvaluate).getProps().containsKey(propertyEvaluate))
                    throw new IllegalArgumentException("The 'Evaluate' function must be referred to an existing property for the entity " + entityEvaluate);
                Function evalFunc = new EvaluateFunction(propertyEvaluate);
                return new FunctionExpression(evalFunc, entities.get(entityEvaluate).getProps().get(propertyEvaluate).getType().name(), expressionStr);

            case "PERCENT":
                int commaIndex = funcArg.indexOf(",");
                String wholeStr = funcArg.substring(0, commaIndex);
                Expression wholeExpression = createExpression(wholeStr, entityStr);
                if((!wholeExpression.getExpressionType().name().equals("DECIMAL")) && (!wholeExpression.getExpressionType().name().equals("FLOAT")))
                    throw new IllegalArgumentException("The whole expression in 'Percent' function must be numeric!");
                String partStr = funcArg.substring(commaIndex + 1, funcArg.length());
                Expression partExpression = createExpression(partStr, entityStr);
                if((!wholeExpression.getExpressionType().name().equals("DECIMAL")) && (!wholeExpression.getExpressionType().name().equals("FLOAT")))
                    throw new IllegalArgumentException("The part expression in 'Percent' function must be numeric!");

                Function percentFunc = new PercentFunction(wholeExpression, partExpression);
                return new FunctionExpression(percentFunc, "FLOAT", expressionStr);

            case "TICKS":
                int dotIndex2 = funcArg.indexOf(".");
                String entityEvaluate2 = funcArg.substring(0, dotIndex2);
                String propertyEvaluate2 = funcArg.substring(dotIndex2 + 1, funcArg.length());
                if (!entities.containsKey(entityEvaluate2))
                    throw new IllegalArgumentException("The 'Evaluate' function referred to a not existing entity!");
                if (!entities.get(entityEvaluate2).getProps().containsKey(propertyEvaluate2))
                    throw new IllegalArgumentException("The 'Evaluate' function must be referred to an existing property for the entity " + entityEvaluate2);
                Function ticksFunc = new TicksFunction(propertyEvaluate2);
                return new FunctionExpression(ticksFunc, "DECIMAL", expressionStr);
        }
        return null;
    }

    @Override
    public Expression fixedExp(String expressionStr) {
        try{
            Integer exp = Integer.parseInt(expressionStr);
            return new IntegerFixed(exp, expressionStr);
        } catch (NumberFormatException ignored){
            try {
                Float exp = Float.parseFloat(expressionStr);
                return new FloatFixed(exp, expressionStr);
            } catch (NumberFormatException ignored1){
                    if (expressionStr.equalsIgnoreCase("true"))
                        return new BooleanFixed(true, expressionStr);
                    else if (expressionStr.equalsIgnoreCase("false"))
                        return new BooleanFixed(false, expressionStr);
                    else
                        return new StringFixed(expressionStr, expressionStr);
            }
        }
    }

    // ======= Termination =======
    @Override
    public void setTerminator(PRDTermination prdTermination) {
        int ticksCount = 0, secondsCount = 0;
        boolean ticksDefined = false;
        boolean secondsDefined = false;

        for (Object terminator : prdTermination.getPRDBySecondOrPRDByTicks()) {
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


    // ======= Grid =======

    @Override
    public void setGrid(PRDWorld.PRDGrid prdGrid) {
        if (prdGrid.getRows() < 10 || prdGrid.getRows() > 100)
            throw new IllegalArgumentException("Grid rows should be between 10-100!");
        if (prdGrid.getColumns() < 10 || prdGrid.getColumns() > 100)
            throw new IllegalArgumentException("Grid columns should be between 10-100!");
        rows = prdGrid.getRows();
        columns = prdGrid.getColumns();
    }



    @Override
    public Map<String, EntityDef> getEntities(){
        return entities;
    }

    @Override
    public EnvironmentManagerImpl getEnvironments(){
        return environments;
    }

    @Override
    public Map<String, Rule> getRules(){
        return rules;
    }

    @Override
    public Termination getTermination(){
        return terminator;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }
    @Override
    public void setWorldPopulation(int worldPopulation){
        this.worldPopulation = worldPopulation;
    }

    @Override
    public int getWorldPopulation() {
        return worldPopulation;
    }

    @Override
    public boolean isPopulationOverGrid() {
        if (worldPopulation > (rows * columns))
            return true;
        else
            return false;
    }

    @Override
    public void transferDataFromXMLToEngine(PRDWorld prdWorld) {
        // Grid:
        setGrid(prdWorld.getPRDGrid());
        // Environment:
        setEnvironments(prdWorld.getPRDEnvironment());
        // Entities (including their properties):
        setEntitines(prdWorld.getPRDEntities());
        // Rules (including actions):
        setRules(prdWorld.getPRDRules());
        // Termination:
        setTerminator(prdWorld.getPRDTermination());
    }
}






