package ticket.com.jsondb;


import java.io.File;

import ticket.com.jsondb.dao.JsonCommandDao;
import ticket.com.jsondb.dao.JsonGameDao;
import ticket.com.jsondb.dao.JsonUserDao;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.db.dao.IUserDAO;

public class JsonDbFactory implements IDbFactory {
    private JsonDbFactory instance;

    private final String COMMAND_FILE = "commands.json";
    private final String USER_FILE = "users.json";
    private final String GAMESTATE_FILE = "gamestate.json";

    public JsonDbFactory(){
    }

    public IDbFactory getInstance(){
        if(instance == null){
            instance = new JsonDbFactory();
        }
        return instance;
    }
    public void startTransaction(){ }

    public void finishTransaction(Boolean commit){
        if(!commit){
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

    public void clear(){
        getUserDAO().clearUsers();
        getGameDAO().clearGames();
        getCommandDAO().clearCommands();
    }

    private File openFile(String filename){
        return new File(filename);
    }

    private void writeFile(String filename){

    }

    private void appendChange(String filename, String change){

    }
}
