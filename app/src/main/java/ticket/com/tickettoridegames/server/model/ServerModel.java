package ticket.com.tickettoridegames.server.model;

import java.util.HashMap;
import java.util.Map;

import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.User;


public class ServerModel {

    private static ServerModel instance = null;

    public static ServerModel getInstance(){
        if(instance == null){
            instance  = new ServerModel();
        }
        return instance;
    }
    //Map of users that stores the Username as the key
    private Map<String, User> registeredUsers;
    //Map of games that stores the GameId ast he key
    private Map<String, Game> games;
    //Map of users that stores the UserID as the kay
    private Map<String, User> activeUsers;

    private ServerModel(){
        registeredUsers = new HashMap<>();
        games = new HashMap<>();
        activeUsers = new HashMap<>();
    }

    public void addUser(User user) throws Exception {
        if(registeredUsers.containsKey(user.getUsername())){
            throw new Exception();
        }
        else{
            registeredUsers.put(user.getUsername(), user);
            activeUsers.put(user.getId(), user);
        }
    }

    public void addGame(Game game, String userId) throws Exception{
        if(games.containsKey(game.getId())){
            throw new Exception();
        }
        else{
            games.put(game.getId(), game);
            addToGame(userId, game.getId());
        }
    }

    public User getUserByName(String username) {
        return registeredUsers.get(username);
    }

    private User getUserById(String id){
        return activeUsers.get(id);
    }

    public void addToGame(String userId, String gameId) throws Exception{
        User user = getUserById(userId);
        Player player = new Player(user.getUsername(),user.getId());
        Game game = games.get(gameId);
        if(game == null){
            throw new Exception();
        }
        else{
            game.addPlayers(player);
        }
    }
}
