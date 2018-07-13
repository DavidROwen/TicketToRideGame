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

    public void clear(){
        instance = new ServerModel();
    }

    public void addNewUser(User user) throws Exception {
        if(registeredUsers.containsKey(user.getUsername())){
            throw new Exception();
        }
        else{
            registeredUsers.put(user.getUsername(), user);
            activeUsers.put(user.getId(), user);
            System.out.println("User: "+user.getId()+" registered ");
            for(String gameId : games.keySet()){
                Command command;
                Game currentGame = games.get(gameId);
                try{
                    command = new Command(ticket.com.tickettoridegames.client.service.JoinService.class.getName(),
                            ticket.com.tickettoridegames.client.service.JoinService.class,
                            ticket.com.tickettoridegames.client.service.JoinService.class.newInstance(),
                            "addGame",
                            new Class<?>[]{ticket.com.tickettoridegames.utility.model.Game.class},
                            new Object[]{currentGame});
                }
                catch(Exception e){
                    command = null;
                }
                CommandsManager.instance().addCommand(command,user.getId());
            }
        }
    }

    public String loginUser(String username, String password) throws Exception{
        User user = getUserByName(username);
        if(user == null){
            throw new Exception();
        }
        else{
            if(user.getPassword().equals(password)){
                if(!activeUsers.containsKey(user.getId())){
                    activeUsers.put(user.getId(), user);
                    System.out.println("User: " + user.getId() + " Logged in");
                }
                for(String gameId : games.keySet()){
                    Command command;
                    Game currentGame = games.get(gameId);
                    try{
                        command = new Command(ticket.com.tickettoridegames.client.service.JoinService.class.getName(),
                                ticket.com.tickettoridegames.client.service.JoinService.class,
                                ticket.com.tickettoridegames.client.service.JoinService.class.newInstance(),
                                "addGame",
                                new Class<?>[]{ticket.com.tickettoridegames.utility.model.Game.class},
                                new Object[]{currentGame});
                    }
                    catch(Exception e){
                        command = null;
                    }
                    CommandsManager.instance().addCommand(command,user.getId());
                }
                return user.getId();
            }
            else{
                return null;
            }
        }
    }

    public void addNewGame(Game game, String userId) throws Exception{
        if(games.containsKey(game.getId())){
            throw new Exception("Game already exists.");
        }
        else{
            games.put(game.getId(), game);
            System.out.println("Game with id: " + game.getId() + " created "+game.toString());
            //send commands to other connected Users
            for(String id : activeUsers.keySet()){
                Command command;
                try{
                    command = new Command(ticket.com.tickettoridegames.client.service.JoinService.class.getName(),
                            ticket.com.tickettoridegames.client.service.JoinService.class,
                            ticket.com.tickettoridegames.client.service.JoinService.class.newInstance(),
                            "addGame",
                            new Class<?>[]{ticket.com.tickettoridegames.utility.model.Game.class},
                            new Object[]{game});
                }
                catch(Exception e){
                    command = null;
                }
                CommandsManager.instance().addCommand(command, id);
            }
            addPlayerToGame(userId, game.getId());
        }
    }

    private User getUserByName(String username) {
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
            boolean addSuccess = game.addPlayers(player);
            if(addSuccess){
                System.out.println("User: " + player.getId() + " added to game: " + gameId);
                for(String id : activeUsers.keySet()){
                    Command command;
                    try{
                        command = new Command(ticket.com.tickettoridegames.client.service.LobbyService.class.getName(),
                                ticket.com.tickettoridegames.client.service.LobbyService.class,
                                ticket.com.tickettoridegames.client.service.LobbyService.class.newInstance(),
                                "addPlayer",
                                new Class<?>[]{String.class, ticket.com.tickettoridegames.utility.model.Player.class},
                                new Object[]{game.getId(), player});
                    }
                    catch (Exception e){
                        command = null;
                    }
                    CommandsManager.instance().addCommand(command,id);
                }
                return true;
            }
            else{
                return false;
            }
        }
    }

    public void addToGameChat(String gameId, String playerId, String message){
        Game game = games.get(gameId);
        Chat chat = new Chat(playerId, message);
        game.addToChat(chat);
        System.out.println("User: " + playerId + " added chat to game: " + gameId);
        //send commands to all the users in the game.
        for(String id : game.getPlayers()){
            Command command;
            try {
                command = new Command(ticket.com.tickettoridegames.client.service.LobbyService.class.getName(),
                        ticket.com.tickettoridegames.client.service.LobbyService.class,
                        ticket.com.tickettoridegames.client.service.LobbyService.class.newInstance(),
                        "updateChat",
                        new Class<?>[]{String.class, ticket.com.tickettoridegames.utility.model.Chat.class},
                        new Object[]{game.getId(), chat});
            }
            catch(Exception e){
                command = null;
                //do some kind of error notification. Error command?
            }
            CommandsManager.instance().addCommand(command,id);
        }
    }
}
