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
    public static DatabaseManager getInstance() {
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

    //Called from commandHandler, sends to ICommandDAO
    public Boolean addCommand(Command command){

        return null;
    }
    //Called by the ServerModel, serializes the user and sends it to IUserDAO
    public Boolean addUser(User user){
        return null;
    }
    //Called by the ServerModel, serializes the game and sends it to IGameDAO
    public Boolean addGame(Game game){
        return null;
    }

    //called by ServerCommunicator, tells ICommandDAO, IUserDAO, and IGameDAO to clear database
    public void clearDatabase(){

    }

    //When the Server reboots it calls this to get the User from the factory
    public User getUser(String userID){
        return null;
    }
    //When the Server reboots it gives the most recent command and asks for
    // all commands after from the ICommandDAO
    public Command[] getCommmands(String commandID){
        return null;
    }
    //When the Server reboots it calls this to get the Game from the IGameDAO
    public Game getGame(String gameID){
        return null;
    }

    // called by the ServerModel every n commands, it
    // clears the recent commands, re-serializes, and sends the game to the factory
    public Boolean updateGame(String gameID, Game game){
        return null;
    }
}
