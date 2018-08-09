package ticket.com.server.server.DB;

import java.util.HashMap;
import java.util.Map;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Command;

public class DatabaseManager {
    IDbFactory factory = null;
    Integer commandCount;
    Integer refreshCount;
    ServerModel sm = ServerModel.getInstance();
    Map<String, Integer> commandCounts = new HashMap<String, Integer>();//needs to store a count for each game


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

    public void assignFactory(IDbFactory factory){
        this.factory = factory;
    }

    public void assignRefreshCount(Integer n){
        this.refreshCount = n;
    }

    //Called from commandHandler, sends to ICommandDAO
    public Boolean addCommand(Command command, String gameID){
        if(!commandCounts.containsKey(gameID)){
            commandCounts.put(gameID, 0);
        }

        try {
            factory.getCommandDAO().addCommand(command);
            commandCounts.put(gameID, commandCounts.get(gameID) + 1);

            if(commandCounts.get(gameID) == refreshCount){
                commandCounts.put(gameID, 0);
                factory.getCommandDAO().clearCommands();
                factory.getGameDAO().updateGame(gameID, sm.getGameById(gameID)); //update game for n commands
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    //Called by the ServerModel, serializes the user and sends it to IUserDAO
    public Boolean addUser(User user){
        try {
            factory.getUserDAO().addUser(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    //Called by the ServerModel, serializes the game and sends it to IGameDAO
    public Boolean addGame(Game game){
        try {
            factory.getGameDAO().addGame(game);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //called by ServerCommunicator, tells ICommandDAO, IUserDAO, and IGameDAO to clear database
    public void clearDatabase(){
        factory.getGameDAO().clearGames();
        factory.getUserDAO().clearUsers();
        factory.getCommandDAO().clearCommands();
    }

    //When the Server reboots it calls this to get the User from the factory
    public User getUser(String userID){
        return factory.getUserDAO().getUser(userID);
    }
    //When the Server reboots it gives the most recent command and asks for
    // all commands after from the ICommandDAO
    public Command getCommmand(String commandID){
        return factory.getCommandDAO().getCommand(commandID);
    }
    //When the Server reboots it calls this to get the Game from the IGameDAO
    public Game getGame(String gameID){
        return factory.getGameDAO().getGame(gameID);
    }

    // called by the ServerModel every n commands, it
    // clears the recent commands, re-serializes, and sends the game to the factory
    public Boolean updateGame(String gameID, Game game){
        try {
            factory.getGameDAO().updateGame(gameID, game);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
