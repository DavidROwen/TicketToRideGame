package ticket.com.server.server.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ticket.com.server.server.CommandsManager;
import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.utility.model.Chat;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.PlayerAction;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Command;

public class ServerModel {
    private static final String JOIN_SERVICE_PATH = "ticket.com.tickettoridegames.client.service.JoinService";
    private static final String LOBBY_SERVICE_PATH = "ticket.com.tickettoridegames.client.service.LobbyService";

    private static ServerModel instance = null;
    public static ServerModel getInstance(){
        if(instance == null){
            instance = new ServerModel();
            instance.initWithDb();
        }
        return instance;
    }

    //Map of users that stores the Username as the key
    private Map<String, User> users; //key is userId
    //Map of games that stores the GameId ast he key
    private Map<String, Game> games; //key is gameId

    private ServerModel(){
        users = new HashMap<>();
        games = new HashMap<>();
    }

    public void clear(){
        instance = new ServerModel();
    }

    public void addNewUser(User user) throws Exception {
        if(getUserByName(user.getUsername()) != null) { //user passed in is new user created with credentials, so id's wouldn't match up
            throw new Exception();
        }
        else{
            users.put(user.getId(), user);
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
                CommandsManager.addCommand(command,user.getId());
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
                if(!users.containsKey(user.getId())){
                    users.put(user.getId(), user);
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
                    CommandsManager.addCommand(command,user.getId());
                }
                return user.getId();
            }
            else{
                return null;
            }
        }
    }

    public void addNewGame(Game game) throws Exception{
        if(games.containsKey(game.getId())){
            throw new Exception("Game already exists.");
        }
        else{
            games.put(game.getId(), game);
            System.out.println("Game with id: " + game.getId() + " created "+game.toString());
            //send commands to other connected Users
            for(String id : users.keySet()){
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
                CommandsManager.addCommand(command, id);
            }
            //addPlayerToGame(userId, game.getId());
        }
    }

    private User getUserByName(String username) {
        for(String each : users.keySet()) {
            User user = users.get(each);
            if(user.getUsername().equals(username)) { return user; }
        }
        return null;
    }

    public User getUserById(String id){
        return users.get(id);
    }

    public boolean addPlayerToGame(String userId, String gameId) throws Exception{
        User user = getUserById(userId);
        Player player = new Player(user.getUsername(),user.getId());
        Game game = games.get(gameId);
        if(game == null){
            throw new Exception("Game not found with id "+gameId);
        }
        else{
            boolean addSuccess = game.addPlayers(player);

            if(addSuccess){
                System.out.println("User: " + player.getId() + " added to game: " + gameId);
                for(String id : users.keySet()){
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
                    CommandsManager.addCommand(command,id);
                }
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean removePlayerFromGame(String gameId, String playerId) throws Exception{
        Game game = games.get(gameId);
        if(game == null){
            throw new Exception("Game not found with id "+gameId);
        }
        else{
            boolean removeSuccess = game.removePlayer(playerId);

            if(removeSuccess){
                System.out.println("User: " + playerId + " removed from game: " + gameId);
                for(String id : users.keySet()){
                    Command command;
                    try{
                        command = new Command(LOBBY_SERVICE_PATH,
                                null,
                                "removePlayer",
                                new Object[]{gameId, playerId});
                    }
                    catch (Exception e){
                        command = null;
                    }
                    CommandsManager.addCommand(command,id);
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
        User player = users.get(playerId);
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
            CommandsManager.addCommand(command,id);
        }
    }

    public boolean startGame(String gameId) throws Exception {
        Game game = games.get(gameId);
        if(game == null){
            throw new Exception("Null game passed into start game");
        }
        if(game.getNumberOfPlayers() == game.getMaxPlayers()){
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
                CommandsManager.addCommand(command, playerId);
            }
            return true;
        }
        return false;
    }
    //Destination Card Functions
    public static List<DestinationCard> drawTemporaryDestinationCards(String gameId) {
        return getInstance().games.get(gameId).drawDestinationCards();
    }

    public static void claimDestinationCards(String playerId, String gameId, DestinationCard[] cards){
        LinkedList<DestinationCard> cardsList = new LinkedList<>(Arrays.asList(cards));
        getInstance().games.get(gameId).claimDestinationCards(cardsList, playerId);
    }

    public static void addDestinationCard(String gameId, DestinationCard[] cards) {
        LinkedList<DestinationCard> cardsList = new LinkedList<>(Arrays.asList(cards));
        getInstance().games.get(gameId).discardDestinationCards(cardsList);
    }
    //End Destination Card Functions

    public static void addToGameHistory(String gameId, PlayerAction pa){
        getInstance().games.get(gameId).addToHistory(pa);
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public Game getGameById(String gameId){
        return games.get(gameId);
    }

    public static void endGame(String gameId) {
        getInstance().getGames().get(gameId).setGameOver(true);
    }

    private void setUsers(List<User> users) {
        for(User each : users) {
            this.users.put(each.getId(), each);
        }
    }

    private void setGames(List<Game> games) {
        for(Game each : games) {
            this.games.put(each.getId(), each);
        }
    }

    public static void initGame(String gameId){
        getInstance().getGameById(gameId).initGame();
    }

    public static void drawTrainCard(String gameId, String playerId){
        getInstance().getGameById(gameId).drawTrainCard(playerId);
    }

    public static void pickupTrainCard(String gameId, String playerId, Integer index){
        getInstance().getGameById(gameId).pickupTrainCard(playerId, index);
    }

    public static void resetTrainBank(String gameId){
        getInstance().getGameById(gameId).resetTrainBank();
    }

    public static void claimRoute(String gameId, String playerId, String route, TrainCard.TRAIN_TYPE typeChoice){
        getInstance().getGameById(gameId).claimRoute(playerId, route, typeChoice);
    }

    public static void switchTurn(String gameId){
        getInstance().getGameById(gameId).switchTurn();
    }

    public static void stAddPlayerToGame(String gameId, String userId) {
        User user = getInstance().getUserById(userId);
        Player player = new Player(user.getUsername(),user.getId());
        getInstance().games.get(gameId).addPlayers(player);
    }

    public static void stRemovePlayerFromGame(String gameId, String playerId) {
        getInstance().games.get(gameId).removePlayer(playerId);
    }

    public static void stAddChatToGame(String gameId, String playerId, String message) {
        Chat chat = new Chat(getInstance().activeUsers.get(playerId).getUsername(), message);
        getInstance().games.get(gameId).addToChat(chat);
    }

    public static void stStartGame(String gameId) {
        Game game = getInstance().games.get(gameId);
        if(game.getNumberOfPlayers() == game.getMaxPlayers()) {
            game.setStarted(true);
        }
    }

    private void initWithDb() {
        try {
            setRegisteredUsers(DatabaseManager.getInstance().getAllUsers());
            setActiveUsers(DatabaseManager.getInstance().getAllUsers()); //todo
            setGames(DatabaseManager.getInstance().getAllGames());
            executeCommands(DatabaseManager.getInstance().getAllCommands());
        } catch (Exception e) {
            System.out.println("ERROR: Server failed to initialize from db");
            e.printStackTrace();
            return;
        }
        System.out.println("Server was successfully initialized from db");
    }

    private void executeCommands(List<Command> commands) {
        for(Command each : commands) {
            try {
                each.execute();
            } catch(Exception e) {
                System.out.println("ERROR: Server failed to execute command");
                e.printStackTrace();
            }
        }
    }

    public void setActiveUsers(List<User> activeUsers) {
        for(User each : activeUsers) {
            this.activeUsers.put(each.getUsername(), each);
        }
    }
}
