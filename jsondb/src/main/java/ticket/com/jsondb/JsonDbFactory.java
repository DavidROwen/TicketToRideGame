package ticket.com.jsondb;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import ticket.com.jsondb.dao.JsonCommandDao;
import ticket.com.jsondb.dao.JsonGameDao;
import ticket.com.jsondb.dao.JsonUserDao;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.db.dao.IUserDAO;

public class JsonDbFactory implements IDbFactory {
    private JsonDbFactory instance;
    private int commandCount;
    private Gson gson;

    //SET THESE TO YOUR LOCAL FILES!
    private final String COMMAND_FILE = "commands.json";
    private final String USER_FILE = "users.json";
    private final String GAMESTATE_FILE = "gamestate.json";

    public JsonDbFactory(){
        commandCount = 0;
        gson = new Gson();
    }

    public IDbFactory getInstance(){
        if(instance == null){
            instance = new JsonDbFactory();
        }
        return instance;
    }
    public void startTransaction(){
        System.out.println("Starting Database Transaction");
    }
    public void finishTransaction(Boolean commit){
        if(commit){
            System.out.println("Database updated");
        }
        else{
            System.out.println("Database failed to Update");
        }
    }

    public IGameDAO getGameDAO(){
        File file = openFile(GAMESTATE_FILE);
        return new JsonGameDao(file);
    }

    public IUserDAO getUserDAO(){
        File file = openFile(USER_FILE);
        return new JsonUserDao(file);
    }

    public ICommandDAO getCommandDAO(){
        File file = openFile(COMMAND_FILE);
        return new JsonCommandDao(file);
    }

    public void clearDB(){

    }

    private File openFile(String filename){
        return new File(filename);
    }

    private void writeFile(String filename){

    }

    private void appendChange(String filename, String change){

    }
}