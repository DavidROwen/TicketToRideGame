package ticket.com.server.server.DB;

import com.google.gson.Gson;

import java.io.File;

import ticket.com.server.server.DAO.ICommandDAO;
import ticket.com.server.server.DAO.IGameDAO;
import ticket.com.server.server.DAO.IUserDAO;
import ticket.com.server.server.DAO.JsonCommandDAO;
import ticket.com.server.server.DAO.JsonGameDAO;
import ticket.com.server.server.DAO.JsonUserDAO;

public class JsonDbFactory implements IDbFactory {

    private JsonDbFactory instance;
    private int commandCount;
    private Gson gson;

    private final String COMMAND_FILE = "commands.json";
    private final String USER_FILE = "users.json";
    private final String GAMESTATE_FILE = "gamestate.json";

    private JsonDbFactory(){
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

    }
    public void finishTransaction(Boolean commit){

    }

    public IGameDAO getGameDAO(){
        File file = openFile(GAMESTATE_FILE);
        return new JsonGameDAO(file);
    }

    public IUserDAO getUserDAO(){
        File file = openFile(USER_FILE);
        return new JsonUserDAO(file);
    }

    public ICommandDAO getCommandDAO(){
        File file = openFile(COMMAND_FILE);
        return new JsonCommandDAO(file);
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
