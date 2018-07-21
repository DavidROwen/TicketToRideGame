package ticket.com.tickettoridegames.utility.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command {
    //allows null instead of empty paramValues
    //for static method, null
    public Command(Class<?> methodsClass, Object instance, String methodName, Object[] paramValues) {
        this.className = methodsClass.getName();
        this.instance = instance;
        if(instance != null) { instanceType = instance.getClass(); }//type will get lost in serialization
        this.methodName = methodName;
        if(paramValues != null) {
            this.paramValues = paramValues;
            this.paramTypes = calcTypes(paramValues);
        }
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
        }
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

    private String className;
    private String methodName;
    private Object[] paramValues = new Object[]{};
    private Object instance;

    private Class<?> instanceType = null; //for serialization
    private Class<?>[] paramTypes = new Class<?>[]{}; //for serialization

    private Class<?>[] calcTypes(Object[] paramValues) {
        Class<?>[] types = new Class<?>[paramValues.length];

        for(int i = 0; i < paramValues.length; i++) {
            types[i] = paramValues[i].getClass();
        }

        return types;
    }
}
