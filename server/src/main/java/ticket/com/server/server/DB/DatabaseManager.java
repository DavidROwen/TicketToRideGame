package ticket.com.server.server.DB;

import java.util.ArrayList;
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
    Integer commandDelta;

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

    public void createServerModel(){
        ServerModel.getInstance();
    }

    public void setCommandDelta(Integer delta){
        this.commandDelta = delta;
    }

    //Called from commandHandler, sends to ICommandDAO
    public void addCommand(Command command, String gameID){
        try {
            dbFactory.startTransaction();
            dbFactory.getCommandDAO().addCommand(command);
            dbFactory.finishTransaction(true);
            commandCount++;

            if(commandCount >= commandDelta){

                List<Command> commands = getAllCommands();

                for (Command c: commands){
                    c.execute();
                }

                List<Game> games = new ArrayList<>(ServerModel.getInstance().getGames().values());

                for (Game game: games){
                    updateGame(game.getId(), game);
                }

                clearCommands();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //Called by the ServerModel, serializes the user and sends it to IUserDAO
    public Boolean addUser(User user){
        try {
            dbFactory.startTransaction();
            dbFactory.getUserDAO().addUser(user);
            dbFactory.finishTransaction(true);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void clearCommands(){
        dbFactory.startTransaction();
        dbFactory.getCommandDAO().clearCommands();
        dbFactory.finishTransaction(true);

        commandCount = 0;
    }

    //Called by the ServerModel, serializes the game and sends it to IGameDAO
    public void addGame(Game game){
        dbFactory.startTransaction();
        boolean status = dbFactory.getGameDAO().addGame(game);
        dbFactory.finishTransaction(status);
    }

    //called by ServerCommunicator, tells ICommandDAO, IUserDAO, and IGameDAO to clear database
    public void clearDatabase(){
        dbFactory.startTransaction();
        dbFactory.clear();
        dbFactory.finishTransaction(true);
        System.out.println("All of the databases were wiped");
    }

    //When the Server reboots it calls this to get the User from the factory
    public User getUser(String userID){
        dbFactory.startTransaction();
        User user = dbFactory.getUserDAO().getUser(userID);
        dbFactory.finishTransaction(true);

        return user;
    }


    //When the Server reboots it calls this to get the Game from the IGameDAO
    public Game getGame(String gameID){
        dbFactory.startTransaction();
        Game game = dbFactory.getGameDAO().getGame(gameID);
        dbFactory.finishTransaction(true);

        return game;
    }

    // called by the ServerModel every n commands, it
    // clears the recent commands, re-serializes, and sends the game to the factory
    public void updateGame(String gameID, Game game){
        dbFactory.startTransaction();
        boolean status = dbFactory.getGameDAO().updateGame(gameID, game);
        dbFactory.finishTransaction(status);
    }

    public void setDbFactory(IDbFactory dbFactory) {
        this.dbFactory = dbFactory;
        System.out.println("The manager's database factory was successfully set");
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
