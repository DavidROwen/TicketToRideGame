package ticket.com.server.server;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ticket.com.utility.db.IDbFactory;

public class PluginManager {
    private Map<String, PluginDescription> descriptions = new HashMap<>(); //key is pluginName

    public PluginManager() {}

    public boolean loadDescriptions(String descriptionsFileName) {
        if(!canUseFile(descriptionsFileName)) {
            System.out.println("Could not load descriptions");
            return false;
        }

        PluginDescription[] descriptionsArray = loadDescriptionsArray(descriptionsFileName);
        if(descriptionsArray == null) {
            System.out.println("Could not load descriptions");
            return false;
        }

        if(!canUseDescriptionsArray(descriptionsArray)) {
            System.out.println("Could not load descriptions");
            return false;
        }

        setDescriptions(descriptionsArray);

        return true;
    }


    //assumes descriptions were already checked
    public boolean canCreate(String name) {
        return descriptions.get(name) != null;
    }

    //assumes that name and ratio were already checked
    //assumes that the descriptions were already checked
    public IDbFactory createPlugin(String name, Integer ratio){
        try {
            //find jar
            URL[] classLoaderUrls = new URL[]{new URL(descriptions.get(name).JAR_PATH)};
            //find class
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
            Class<?> pluginClass = urlClassLoader.loadClass(descriptions.get(name).IMPLEMENTATION_NAME);
            //build class
            return (IDbFactory) pluginClass.getConstructor().newInstance();
        } catch(Exception e) {
            System.out.println("Failed to create plugin \"" + name + "\", with ratio \"" + ratio + "\"");
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getPossiblePluginNames() {
        return new ArrayList<>(descriptions.keySet());
    }


    private boolean canUseFile(String descriptionsFileName) {
        try {
            new File(descriptionsFileName);
        } catch (Exception e) {
            System.out.println("ERROR: Could not find file \"" + descriptionsFileName + "\"");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean canUseDescriptionsArray(PluginDescription[] descriptionsArray) {
        for(PluginDescription each : descriptionsArray) {
            if(!each.canUse()) {
                System.out.println("ERROR: One or more array elements were not formatted correctly");
                return false;
            }

            //check jar
            URL[] classLoaderUrls;
            try {
                classLoaderUrls = new URL[]{new URL(each.JAR_PATH)};
            } catch (MalformedURLException e) {
                System.out.println("ERROR: jar path \"" + each.JAR_PATH + "\" was invalid");
                e.printStackTrace();
                return false;
            }
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

            //check implementation name
            Class<?> dbFactoryClass;
            try {
                dbFactoryClass = urlClassLoader.loadClass(each.IMPLEMENTATION_NAME);
            } catch (ClassNotFoundException e) {
                System.out.println("ERROR: implementation for \"" + each.IMPLEMENTATION_NAME
                        + "\" did not exist");
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                System.out.println("ERROR: jar is outdated");
                return false;
            }

            //check correct implementation type
            try {
                IDbFactory factory = (IDbFactory) dbFactoryClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println("ERROR: Failed to create IDb object for \"" + each + "\"");
                e.printStackTrace();
                return false;
            }
        }

        System.out.println("Confirmed that all of the json's plugin descriptions are valid");
        return true;
    }

    private PluginDescription[] loadDescriptionsArray(String descriptionsFileName) {
        Gson gson = new Gson();
        JsonReader jsonReader;

        try {
            jsonReader = new JsonReader(new FileReader(descriptionsFileName));
            return gson.fromJson(jsonReader, PluginDescription[].class); //doesn't call constructor
        } catch (FileNotFoundException e) {
            //should have already checked file
            e.printStackTrace();
            return null;
        } catch (AssertionError e) { //thrown by pluginDescription's constructor
            e.printStackTrace();
            return null;
        } catch (IllegalStateException e) {
            System.out.println("ERROR: Json file was not formatted correctly");
            e.printStackTrace();
            return null;
        }
    }

    //assumes that descriptions were all checked when constructed
    private void setDescriptions(PluginDescription[] descriptionsArray) {
        for(PluginDescription each : descriptionsArray) {
            descriptions.put(each.NAME, each);
        }
    }
}

class PluginDescription {
    private static List<String> usedNames = new LinkedList<>(); //todo never set

    public final String NAME;
    public final String DESCRIPTION;
    public final String JAR_PATH;
    public final String IMPLEMENTATION_NAME;

    public PluginDescription(String NAME, String DESCRIPTION, String JAR_PATH, String IMPLEMENTATION_NAME) {
        this.NAME = NAME;
        usedNames.add(NAME);
        this.DESCRIPTION = DESCRIPTION;
        this.JAR_PATH = JAR_PATH;
        this.IMPLEMENTATION_NAME = IMPLEMENTATION_NAME;
    }

    public static void clearUsedNames() {
        usedNames = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "PluginDescription{" +
                "NAME='" + NAME + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", JAR_PATH='" + JAR_PATH + '\'' +
                ", IMPLEMENTATION_NAME='" + IMPLEMENTATION_NAME + '\'' +
                '}';
    }

    public boolean canUse() {
        //fields can't be null
        if (NAME == null || DESCRIPTION == null || JAR_PATH == null || IMPLEMENTATION_NAME == null) {
            System.out.println("ERROR: \"" + NAME + "\"'s description fields cannot have null values");
            return false;
        }
        //all names must be unique
        if (usedNames.contains(NAME)) {
            System.out.println("ERROR: \"" + NAME + "/ is already being used");
            return false;
        }
        return true;
    }

    public static void addUsedName(String name) {
        usedNames.add(name);
    }
}
