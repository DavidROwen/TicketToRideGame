package ticket.com.server.server.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ticket.com.server.server.CommandsManager;
import ticket.com.server.server.service.GameService;
import ticket.com.utility.model.Chat;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.PlayerAction;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Command;

public class ServerModel {
    public static final String JOIN_SERVICE_PATH = "ticket.com.tickettoridegames.client.service.JoinService";
    public static final String LOBBY_SERVICE_PATH = "ticket.com.tickettoridegames.client.service.LobbyService";

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
    private Map<String, Game> games; //key is gameId
    //Map of users that stores the UserID as the kay
    private Map<String, User> activeUsers; //key is userId

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
                    command = new Command(JOIN_SERVICE_PATH,
                            null,
                            "addGame",
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
                        command = new Command(JOIN_SERVICE_PATH,
                                null,
                                "addGame",
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
                    command = new Command(JOIN_SERVICE_PATH,
                            null,
                            "addGame",
                            new Object[]{game});
                }
                catch(Exception e){
                    command = null;
                }
                CommandsManager.instance().addCommand(command, id);
            }
            //addPlayerToGame(userId, game.getId());
        }
    }

    private User getUserByName(String username) {
        return registeredUsers.get(username);
    }

    public User getUserById(String id){
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
                        command = new Command(JOIN_SERVICE_PATH,
                                null,
                                "addPlayer",
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

    public void addChatToGame(String gameId, String playerId, String message){
        Game game = games.get(gameId);
        User player = activeUsers.get(playerId);
        Chat chat = new Chat(player.getUsername(), message);
        game.addToChat(chat);
        System.out.println("User: " + playerId + " added chat to game: " + gameId);
        //send commands to all the users in the game.
        for(String id : game.getPlayersId()){
            Command command;
            try {
                command = new Command(LOBBY_SERVICE_PATH,
                        null,
                        "updateChat",
                        new Object[]{game.getId(), chat});
            }
            catch(Exception e){
                command = null;
                //do some kind of error notification. Error command?
            }
            CommandsManager.instance().addCommand(command,id);
        }
    }

    public boolean startGame(String gameId) throws Exception {
        Game game = games.get(gameId);
        if(game == null){
            throw new Exception("Null game passed into start game");
        }
        if(game.getNumberOfPlayers() > 1){
            game.setStarted(true);
            for(String playerId : game.getPlayersId()){
                Command command;
                try{
                    command = new Command(
                            LOBBY_SERVICE_PATH,
                            null,
                            "startingGame",
                            new Object[]{game.getId()});
                }
                catch (Exception e){
                    command = null;
                }
                CommandsManager.instance().addCommand(command, playerId);
            }
            return true;
        }
        return false;
    }
    //Destination Card Functions
    public List<DestinationCard> drawTemporaryDestinationCards(String gameId) {
        return games.get(gameId).drawDestinationCards();
    }

    public void claimDestinationCards(String playerId, String gameId, List<DestinationCard> cards){
        Game game = games.get(gameId);
        game.claimDestinationCards(cards, playerId);
    }

    public void addDestinationCard(String gameId, List<DestinationCard> card) {
        games.get(gameId).discardDestinationCards(card);
    }
    //End Destination Card Functions

    //Game History Function
    public void addToGameHistory(String gameId, PlayerAction pa){
        Game game = games.get(gameId);
        game.addToHistory(pa);
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public Game getGameById(String gameId){
        return games.get(gameId);
    }

}
