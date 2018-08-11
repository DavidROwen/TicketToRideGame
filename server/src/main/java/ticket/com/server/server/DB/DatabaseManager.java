package ticket.com.server.server.DB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Command;

public class DatabaseManager {
    IDbFactory dbFactory = null;

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
        this.dbFactory = factory;
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
            dbFactory.getCommandDAO().addCommand(command);
            commandCounts.put(gameID, commandCounts.get(gameID) + 1);

            if(commandCounts.get(gameID) == refreshCount){
                commandCounts.put(gameID, 0);
                dbFactory.getCommandDAO().clearCommands();
                dbFactory.getGameDAO().updateGame(gameID, sm.getGameById(gameID)); //update game for n commands
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
            dbFactory.getUserDAO().addUser(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Boolean addCommand(Command command){
        if(dbFactory == null) {
            System.out.println("ERROR: dbFactory hasn't been initialized");
            return false;
        }
        return null;
    }

    public void clearCommands(){
        dbFactory.clear();

    }

    //Called by the ServerModel, serializes the game and sends it to IGameDAO
    public Boolean addGame(Game game){
        try {
            dbFactory.getGameDAO().addGame(game);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //called by ServerCommunicator, tells ICommandDAO, IUserDAO, and IGameDAO to clear database
    public void clearDatabase(){
        dbFactory.clear();
    }

    //When the Server reboots it calls this to get the User from the factory
    public User getUser(String userID){
        return dbFactory.getUserDAO().getUser(userID);
    }


    //When the Server reboots it calls this to get the Game from the IGameDAO
    public Game getGame(String gameID){
        return dbFactory.getGameDAO().getGame(gameID);
    }

    // called by the ServerModel every n commands, it
    // clears the recent commands, re-serializes, and sends the game to the factory
    public Boolean updateGame(String gameID, Game game){
        try {
            dbFactory.getGameDAO().updateGame(gameID, game);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //todo clear all data in all databases
    public void wipe() {
        System.out.println("All of the databases were wiped");
    }

    public void setDbFactory(IDbFactory dbFactory) {
        this.dbFactory = dbFactory;
        System.out.println("The manager's database was successfully set");
    }

    public List<User> getAllUsers() {
        dbFactory.startTransaction();

        List<User> users = dbFactory.getUserDAO().getAllUsers();
        if(users == null) {
            System.out.println("ERROR: dbFactory returned null when getAllUsers() was called");
            dbFactory.finishTransaction(false);
            return null;
        }

        dbFactory.finishTransaction(true);
        return users;
    }

    public List<Game> getAllGames() {
        dbFactory.startTransaction();

        List<Game> games = dbFactory.getGameDAO().getAllGames();
        if(games == null) {
            System.out.println("ERROR: dbFactory returned null when getAllGames() was called");
            dbFactory.finishTransaction(false);
            return null;
        }

        dbFactory.finishTransaction(true);
        return games;
    }

    public List<Command> getAllCommands() {
        dbFactory.startTransaction();

        List<Command> commands = dbFactory.getCommandDAO().getAllCommands();
        if(commands == null) {
            System.out.println("ERROR: dbFactory returned null when getAllCommands() was called");
            dbFactory.finishTransaction(false);
            return null;
        }

        dbFactory.finishTransaction(true);
        return commands;
    }
}
