package ticket.com.jsondb.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ticket.com.utility.db.dao.IUserDAO;
import ticket.com.utility.model.User;

public class JsonUserDao implements IUserDAO {
    private File file;
    private JsonDao helper;

    public JsonUserDao(File file){
        this.file = file;
        helper = new JsonDao();
    }

    public Boolean addUser(User user){
        List<User> users;
        try {
            users = (List<User>) helper.getCurrentJson(file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        if(users == null){
            users = new ArrayList<>();
        }
        users.add(user);
        String json = helper.toJson(users);
        return helper.writeToFile(file, json);
    }

    public User getUser(String username){
        List<User> users = null;
        try {
            users = (List<User>) helper.getCurrentJson(file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if(users == null){
            return null;
        }
        else{
            for(User user : users){
                if(user.getUsername().equals(username)){
                    return user;
                }
            }
            return null;
        }
    }

    public Boolean clearUsers(){
        return helper.clearFile(file);
    }
}
