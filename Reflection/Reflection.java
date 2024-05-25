import reflection.api.Investigator;

import java.lang.reflect.*;
import java.util.*;

public class Reflection implements Investigator {
    private Class<?>  Klass;
    private Object instance;
    @Override
    public void load(Object anInstanceOfSomething){
        Klass = anInstanceOfSomething.getClass();
        instance = anInstanceOfSomething;
    }
    @Override
    public int getTotalNumberOfMethods() {
        return (Klass.getDeclaredMethods().length);
    }

    @Override
    public int getTotalNumberOfConstructors(){
        return (Klass.getDeclaredConstructors().length);
    }
    @Override
    public int getTotalNumberOfFields(){
        return (Klass.getDeclaredFields().length);
    }
    @Override
    public Set<String> getAllImplementedInterfaces(){
        Set<String> res = new HashSet<>();
        Class<?>[] interfaces = Klass.getInterfaces();
        for (Class<?> i : interfaces)
            res.add(i.getSimpleName());
        return res;
    }
    @Override
    public int getCountOfConstantFields(){
        Field[] allFields = Klass.getDeclaredFields();
        int count = 0;
        for (Field fld : allFields){
            if (Modifier.isFinal(fld.getModifiers()))
                count++;
        }
        return count;
    }
    @Override
    public int getCountOfStaticMethods(){
        Method[] allMethods = Klass.getDeclaredMethods();
        int count = 0;
        for (Method mtd : allMethods){
            if (Modifier.isStatic(mtd.getModifiers()))
                count++;
        }
        return count;
    }
    @Override
    public boolean isExtending(){
        Class<?>  superClass = Klass.getSuperclass();
        return superClass != null && superClass != Object.class;
    }
    @Override
    public String getParentClassSimpleName(){
        Class<?>  parent = Klass.getSuperclass();
        if (parent == null) // Klass == Object class
            return null;
        return (parent.getSimpleName());
    }
    @Override
    public boolean isParentClassAbstract(){
        Class<?> parent = Klass.getSuperclass();
        if (parent == null) // Klass == Object class
            return false;

        return Modifier.isAbstract(parent.getModifiers());
    }
    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain(){
        Set<String> res = new HashSet<>();
        Field[] fieldsInClass = Klass.getDeclaredFields();
        for (Field field : fieldsInClass)
            res.add(field.getName());

        Class<?>  parent = Klass.getSuperclass();
        while (parent != null){
            fieldsInClass = parent.getDeclaredFields();
            for (Field field : fieldsInClass)
                res.add(field.getName());
            parent = parent.getSuperclass();
        }
        return res;
    }
    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        Method[] methods = Klass.getDeclaredMethods();
        Method theMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                theMethod = method;
                break;
            }
        }

        try {
            return ((int) theMethod.invoke(instance,args));
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Object createInstance(int numberOfArgs, Object... args){
        Constructor<?>[] constructors = Klass.getDeclaredConstructors();
        Constructor<?> theCtor = null;
        for (Constructor<?> ctor : constructors) {
            if (ctor.getParameterCount() == numberOfArgs) {
                theCtor = ctor;
                break;
            }
        }
        try {
            if (theCtor != null) {
                return theCtor.newInstance(args);
            }
            return null;
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        try {
            Method theMethod = Klass.getDeclaredMethod(name, parametersTypes);
            theMethod.setAccessible(true);
            return theMethod.invoke(instance,args);
        }
        catch (Exception var5) {
            return null;
        }
    }
    @Override
    public String getInheritanceChain(String delimiter){
        List<String> parentsLst = new ArrayList<>();
        parentsLst.add(Klass.getSimpleName());
        Class<?> parent = Klass.getSuperclass();
        while (parent != null){
            parentsLst.add(0, parent.getSimpleName());
            parent = parent.getSuperclass();
        }

        return String.join(delimiter, parentsLst);
    }
}