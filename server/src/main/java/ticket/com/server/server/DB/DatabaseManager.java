package ticket.com.server.server.DB;

import ticket.com.utility.model.Game;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Command;

public class DatabaseManager {
    /**
     * private instance that creates a singleton pattern
     */
    private static DatabaseManager instance;

    /**
     * Returns an instance of DatabaseManager, if the instance is not created yet it calls the constructor
     * and returns the newly created instance
     *
     * @pre none
     * @post you now have a DatabaseManager instance
     * @return DatabaseManager instance
     */
    public static DatabaseManager instance() {
        if(instance == null) { instance = new DatabaseManager(); }
        return instance;
    }

    /**
     * a private constructor only called in the instance() method if the instance is currently null
     * (private functions so the pre conditions should always be fulfilled)
     *
     * @pre instance == null
     * @post Commands Manager object is now created
     */
    private DatabaseManager() {

    }


    public Boolean addUser(User user){
        return null;
    }

    public User getUser(String userID){
        return null;
    }

    public void clearUsers(){
    }

    public Boolean addCommand(Command command){
        return null;
    }

    public Command[] getCommmand(String commandID){
        return null;
    }

    public void clearCommands(){

    }

    public Boolean addGame(Game game){
        return null;
    }

    public Game getGame(String gameID){
        return null;
    }

    public Boolean updateGame(String gameID, Game game){
        return null;
    }
}
