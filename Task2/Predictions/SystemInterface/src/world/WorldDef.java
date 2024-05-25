package world;

import definition.entity.EntityDef;
import definition.entity.secondaryEntity.SecondaryEntityDef;
import definition.environment.impl.EnvironmentManagerImpl;
import definition.property.api.PropertyDef;
import definition.rule.Rule;
import definition.rule.action.api.Action;
import definition.rule.action.impl.condition.singularity.AbstractCondition;
import definition.rule.action.impl.withExpression.expression.api.Expression;
import definition.rule.action.impl.withExpression.expression.impl.FunctionExpression;
import definition.terminatoin.Termination;
import generated.*;

import java.util.Map;

public interface WorldDef {

    // ======= Environments =======
    void setEnvironments(PRDEnvironment prdEnvProperty);

    // ======= Entities =======
    void setEntitines(PRDEntities prdEntities);
    Map<String, PropertyDef> getPRDProperties(PRDProperties prdProperties);

    // ======= Rules =======
    void setRules (PRDRules prdRule);
    Rule createRule(PRDRule rule);
    Action createAction(PRDAction prdAction);
    SecondaryEntityDef createSecondaryEntity(PRDAction.PRDSecondaryEntity prdSecondaryEntity);
    int getCountNum(String countStr);
    AbstractCondition createCondition (PRDCondition prdCondition);

    void entityAndPropValidation (String entity, String property);
    void entityValidation (String entity);
    void expressionCheck(Expression arg, String actionType);
    void typeCheck(String type1, String type2);
    Expression createExpression(String expressionStr, String entityStr);
    FunctionExpression createFunctionExp(String expressionStr, String entityStr);
    Expression fixedExp(String expressionStr);

    // ======= Termination =======
    void setTerminator(PRDTermination prdTermination);

    // ======= Grid =======
    void setGrid(PRDWorld.PRDGrid prdGrid);

    Map<String, EntityDef> getEntities();
    EnvironmentManagerImpl getEnvironments();
    Map<String, Rule> getRules();
    Termination getTermination();

    void transferDataFromXMLToEngine(PRDWorld prdWorld);
    int getRows();
    int getColumns();
    int getWorldPopulation();
    boolean isPopulationOverGrid();
    void setWorldPopulation(int worldPopulation);
}

