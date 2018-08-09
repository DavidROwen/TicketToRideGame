package ticket.com.server.server.DAO;

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

public class JsonUserDAO implements IUserDAO {

    private File file;
    private Gson gson;

    public JsonUserDAO(File file){
        this.file = file;
        gson = new Gson();
    }

    public Boolean addUser(User user){
        List<User> users = getCurrentJson();
        if(users == null){
            return false;
        }
        users.add(user);
        String json = gson.toJson(users);
        try{
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.flush();
            bw.close();
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public User getUser(String playerId){
        List<User> users = getCurrentJson();
        if(users == null){
            return null;
        }
        else{
            for(User user : users){
                if(user.getId().equals(playerId)){
                    return user;
                }
            }
            return null;
        }
    }

    public void clearUsers(){
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private List<User> getCurrentJson(){
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
            String currentJson = new String(encoded);
             return gson.fromJson(currentJson, new TypeToken<List<User>>() {}.getType());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
