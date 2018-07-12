package ticket.com.tickettoridegames.server.model;

import java.util.HashMap;
import java.util.Map;

import ticket.com.tickettoridegames.server.CommandsManager;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Command;


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

    public void addNewGame(Game game, String userId) throws Exception{
        if(games.containsKey(game.getId())){
            throw new Exception();
        }
        else{
            games.put(game.getId(), game);
            addPlayerToGame(userId, game.getId());
            //send commands to other connected Users
        }
    }

    public User getUserByName(String username) {
        return registeredUsers.get(username);
    }

    private User getUserById(String id){
        return activeUsers.get(id);
    }

    public boolean addPlayerToGame(String userId, String gameId) throws Exception{
        User user = getUserById(userId);
        Player player = new Player(user.getUsername(),user.getId());
        Game game = games.get(gameId);
        if(game == null){
            throw new Exception();
        }
        else{
            if(game.addPlayers(player)){
                return true;
            }
            else{
                return false;
            }
        }
        //send commands to all the users updating game list

    }

    public void addToGameChat(String gameId, String playerId, String message){
        Game game = games.get(gameId);
        Chat chat = new Chat(playerId, message);
        game.addToChat(chat);
        //send commands to all the users in the game.
        CommandsManager cm = CommandsManager.instance();
        for(String id : game.getPlayers()){
            Command command;
            try {
                command = new Command(ticket.com.tickettoridegames.client.service.LobbyService.class.getName(),
                        ticket.com.tickettoridegames.client.service.LobbyService.class,
                        ticket.com.tickettoridegames.client.service.LobbyService.class.newInstance(),
                        "updateChat",
                        new Class<?>[]{ticket.com.tickettoridegames.utility.model.Chat.class},
                        new Object[]{chat});
            }
            catch(Exception e){
                command = null;
                //do some kind of error notification. Error command?
            }
            cm.addCommand(command,id);
        }
    }
}
