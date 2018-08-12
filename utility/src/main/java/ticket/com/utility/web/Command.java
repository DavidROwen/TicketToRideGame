package ticket.com.utility.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command {

    public Command(){
        ID = curId++;
    }

    public Command(String methodsClass, Object instance, String methodName, Object[] paramValues) {
        this.className = methodsClass;
        this.instance = instance;
        if(instance != null) { instanceType = instance.getClass(); }//type will get lost in serialization
        this.methodName = methodName;
        if(paramValues != null) {
            this.paramValues = paramValues;
            this.paramTypes = calcTypes(paramValues);
        } else {
            this.paramValues = new Object[]{};
            this.paramTypes = new Class<?>[]{};
        }
        ID = curId++;
    }

    //use if types are Object
    //allows null instead of empty paramValues
    //for static method, null
    public Command(String className, Class<?> instanceType, Object instance, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
        this.className = className;
        this.instanceType = instanceType;
        this.instance = instance;
        this.methodName = methodName;
        if(paramValues != null) {
            this.paramTypes = paramTypes;
            this.paramValues = paramValues;
        } else {
            this.paramValues = new Object[]{};
            this.paramTypes = new Class<?>[]{};
        }
        ID = curId++;
    }

    public Object execute() {

        try {
            Class<?> receiver = Class.forName(className);
            Method method = receiver.getMethod(methodName, paramTypes);
            return method.invoke(instance, paramValues);
        } catch (NoSuchMethodException e) {
            System.out.println("invalid method: " + toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("execution failed: " + toString());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("invalid class: " + toString());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("nullPointer: " + toString());
            e.printStackTrace();
        }

        return null;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public Object getInstance() {
        return instance;
    }

    public Class<?> getInstanceType() {
        return instanceType;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setInstanceType(Class<?> instanceType) {
        this.instanceType = instanceType;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public String toString() {
        String str = "";
        str += className + " " + instance + "." + methodName + "(";
        for(int i = 0; i < paramTypes.length; i++) {
            str += paramTypes[i].toString() + " ";
            str += paramValues[i].toString() + ", ";
        }
        str += ")";

        return str;
    }

    private static Integer curId = 0;

    private String className;
    private String methodName;
    private Class<?> instanceType = null; //for serialization
    private Object instance;
    private Class<?>[] paramTypes; //for serialization
    private Object[] paramValues;

    public final Integer ID;

    private Class<?>[] calcTypes(Object[] paramValues) {
        Class<?>[] types = new Class<?>[paramValues.length];

        try {
            for (int i = 0; i < paramValues.length; i++) {
                types[i] = paramValues[i].getClass();
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        return types;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public void setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;
    }

    public Integer getID() {
        return ID;
    }
}
