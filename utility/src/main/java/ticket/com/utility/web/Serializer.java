package ticket.com.utility.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class Serializer {
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static Object fromJson(InputStreamReader in, Class<?> returnType ) {
        return GSON.fromJson(in, returnType);
    }

    public static Object fromJson(String in, Class<?> returnType ) {
        return GSON.fromJson(in, returnType);
    }

    public static Object fromJson(String in, Type returnType ) {
        return GSON.fromJson(in, returnType);
    }

    public static void toJsonOutput(Object result, OutputStreamWriter outputStreamWriter) {
        GSON.toJson(result, outputStreamWriter);
    }

    public static Object fromInputJson(InputStreamReader inputStreamReader, Type returnType) {
        return GSON.fromJson(inputStreamReader, returnType);
    }
    
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Command.class, new GenericCommandSerializer())
        .registerTypeAdapter(Command.class, new GenericCommandDeserializer())
        .create();

    private static class GenericCommandSerializer implements JsonSerializer<Command> {
        @Override
        public JsonElement serialize(Command src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("className", src.getClassName());
            jsonObject.addProperty("methodName", src.getMethodName());
            jsonObject.add("paramTypes", calcParamTypes(src));
            jsonObject.add("paramValues", calcParamValues(src));
            if(src.getInstance() != null) {
                jsonObject.addProperty("instanceType", src.getInstanceType().getName());
                jsonObject.add("instance", calcInstance(src));
            }

            return jsonObject;
        }

        private JsonElement calcInstance(Command src) {
            JsonElement instance;

            JsonElement elem = calcJsonElement(src.getInstance());
            if(elem.isJsonObject()) {
                instance = elem.getAsJsonObject();
            } else {
                instance = new JsonPrimitive(src.getInstance().toString());
            }

            return instance;
        }

        private JsonElement calcJsonElement(Object obj) {
            JsonParser parser = new JsonParser();
            return parser.parse(toJson(obj));
        }

        private JsonElement calcParamValues(Command src) {
            JsonArray paramValues = new JsonArray();

            for (Object cur : src.getParamValues()) {
                if(isJsonObject(cur)) {
                    JsonObject paramValue = calcJsonObject(cur);
                    paramValues.add(paramValue);
                } else if(isJsonArray(cur)) {
                    JsonArray paramValue = calcJsonArray(cur);
                    paramValues.add(paramValue);
                } else {
                    paramValues.add(cur.toString());
                }
            }

            return paramValues;
        }

        private boolean isJsonObject(Object cur) {
            JsonParser parser = new JsonParser();
            return parser.parse(toJson(cur)).isJsonObject();
        }

        private boolean isJsonArray(Object cur) {
            JsonParser parser = new JsonParser();
            return parser.parse(toJson(cur)).isJsonArray();
        }

        private JsonObject calcJsonObject(Object cur) {
            JsonParser parser = new JsonParser();
            return parser.parse(toJson(cur)).getAsJsonObject();
        }

        private JsonArray calcJsonArray(Object cur) {
            JsonParser parser = new JsonParser();
            return parser.parse(toJson(cur)).getAsJsonArray();
        }

        private JsonElement calcParamTypes(Command src) {
            JsonArray paramTypes = new JsonArray();

            for (Class<?> cur : src.getParamTypes()) {
                paramTypes.add(new JsonPrimitive(cur.getName()));
            }

            return paramTypes;
        }
    }


    private static class GenericCommandDeserializer implements JsonDeserializer<Command> {
        @Override
        public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject(); //put json into object

            //parse jsonObject
            Class<?> methodsClass = calcMethodsClass(jsonObject);
            String methodName = jsonObject.get("methodName").getAsString();
            Class<?>[] paramTypes = calcParamTypes(jsonObject); //because object is ambiguous
            Object[] paramValues = calcParamValues(jsonObject, paramTypes);
            Class<?> instanceType = calcInstanceType(jsonObject); //because object is ambiguous
            Object instance = calcInstance(jsonObject, instanceType);

            //build object
            return new Command(methodsClass.getName(), methodName, paramTypes, paramValues); //types may be object
        }

        private Class<?> calcMethodsClass(JsonObject jsonObject) {
            return calcType(jsonObject.get("className"));
        }

        private Object calcInstance(JsonObject jsonObject, Class<?> instanceType) {
            if(jsonObject.get("instance") == null) { return null; } //static methods
//            System.out.println("look here: " + jsonObject.get getString("instance"));
            return calcObject(jsonObject.get("instance").getAsJsonObject(), instanceType);
        }

        private Class<?> calcInstanceType(JsonObject jsonObject) {
            if(jsonObject.get("instance") == null) { return null; } //static methods
            return calcType(jsonObject.get("instanceType"));
        }

        private Object[] calcParamValues(JsonObject jsonObject, Class<?>[] paramTypes) {
            Object[] paramValues;

            //to jsonArray
            JsonArray jsonParamValues = jsonObject.get("paramValues").getAsJsonArray(); //get values from json

            //to array
            paramValues = new Object[jsonParamValues.size()];
            for (int i = 0; i < jsonParamValues.size(); i++) {
                paramValues[i] = calcObject(jsonParamValues.get(i), paramTypes[i]);
            }

            return paramValues;
        }

        private Object calcObject(JsonElement value, Class<?> type) {
            Object obj;

            //to string
            String paramValuesStr;
            if(value.isJsonObject()) {
                paramValuesStr = value.getAsJsonObject().toString();
            } else if (value.isJsonArray()) {
                paramValuesStr = value.getAsJsonArray().toString();
            } else {//primitive types
                paramValuesStr = value.toString();
            }

            //to reader
            InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(paramValuesStr.getBytes()));

            //to obj
            obj = fromJson(reader, type);

            return obj;
        }

        private Class<?>[] calcParamTypes(JsonObject jsonObject) {
            Class<?>[] paramTypes;

            //to jsonArray
            JsonArray jsonParamTypes = jsonObject.get("paramTypes").getAsJsonArray(); //get types from json

            //to array
            paramTypes = new Class<?>[jsonParamTypes.size()];
            for (int i = 0; i < jsonParamTypes.size(); i++) {
                paramTypes[i] = calcType(jsonParamTypes.get(i));
            }

            return paramTypes;
        }

        private Class<?> calcType(JsonElement className) {
            if(className.getAsString().length() > 6) {
                try {
                    return Class.forName(className.getAsString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                switch(className.getAsString()) {
                    case "int":
                        return int.class;
                    default:
                        return null;//assume primitive
                }
            }
        }
    }
}
