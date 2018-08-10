package ticket.com.jsondb.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ticket.com.utility.model.User;

public class JsonDao {

    private Gson gson;

    public JsonDao(){
        gson = new Gson();
    }

    public List<?> getCurrentJson(String filename){
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            String currentJson = new String(encoded);
            return gson.fromJson(currentJson, new TypeToken<List<User>>() {}.getType());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public String toJson(List<?> list){
        return gson.toJson(list);
    }

    public boolean writeToFile(File file, String text){
        try{
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.flush();
            bw.close();
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearFile(File file){
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.close();
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
