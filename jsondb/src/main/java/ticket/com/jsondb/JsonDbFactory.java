package ticket.com.jsondb;

import com.google.gson.Gson;

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
    private final String COMMAND_FILE = "C:\\Users\\dastin\\Documents\\BYUSummer2018\\cs340\\TicketToRideGame\\commands.json";
    private final String USER_FILE = "C:\\Users\\dastin\\Documents\\BYUSummer2018\\cs340\\TicketToRideGame\\users.json";
    private final String GAMESTATE_FILE = "C:\\Users\\dastin\\Documents\\BYUSummer2018\\cs340\\TicketToRideGame\\gamestate.json";

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
        return new JsonGameDao(GAMESTATE_FILE);
    }

    public IUserDAO getUserDAO(){
        return new JsonUserDao(USER_FILE);
    }

    public ICommandDAO getCommandDAO(){
        return new JsonCommandDao(COMMAND_FILE);
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
