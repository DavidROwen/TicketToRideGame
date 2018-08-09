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
    private final String USER_FILE = "C:\\Users\\dastin\\Documents\\BYUSummer2018\\cs340\\TicketToRideGame\\users.json";
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
        return new JsonGameDAO(GAMESTATE_FILE);
    }

    public IUserDAO getUserDAO(){
        return new JsonUserDAO(USER_FILE);
    }

    public ICommandDAO getCommandDAO(){
        return new JsonCommandDAO(COMMAND_FILE);
    }

    public void clearDB(){

    }

    private void openFile(String filename){

    }

    private void writeFile(String filename){

    }

    private void appendChange(String filename, String change){

    }
}
