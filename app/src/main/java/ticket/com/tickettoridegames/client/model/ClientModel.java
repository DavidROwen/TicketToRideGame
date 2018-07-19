package ticket.com.tickettoridegames.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.User;

public class ClientModel extends Observable {

    // Singleton Class
    static private ClientModel _instance;

    static public ClientModel get_instance() {
        if (_instance == null) {
            _instance = new ClientModel();
        }
        return _instance;
    }

    static private User currentUser;
    static private Map<String, Game> gameList;

    private ClientModel() {
//        if(gameList == null) {
            gameList = new HashMap<>();
//        } //todo bandaid
    }

    public void setUser(User user){
        currentUser = user;
        // notify the login presenter
        setChanged();
        notifyObservers();
    }

    public String getUserId(){
        return currentUser.getId();
    }

    public User getUser(){
        return currentUser;
    }

    public void setGames(Map<String, Game> games){
        gameList = games;
        // notify lobby presenter
        setChanged();
        notifyObservers();
    }

    public Map<String, Game> getGames(){
        return gameList;
    }

    public boolean addGameToList(Game game){
        if (gameList.containsValue(game)){
            // Game is already in the list
            return false;
        }
        gameList.put(game.getId(), game);
        // notify lobby presenter
        setChanged();
        notifyObservers();
        return true;
    }

    public void updateGame(Game game){
        gameList.put(game.getId(), game);
        setChanged();
        notifyObservers();
    }

    public List<Chat> getGameChat(String gameID){
        Game game = gameList.get(gameID);
        return game.getChatList();
    }

    public void addGameChat(String gameID, Chat chat){
        Game game = gameList.get(gameID);
        game.addToChat(chat);
        setChanged();
        notifyObservers();
    }

    public String getCurrentGameID(){
        return currentUser.getGameId();
    }

    public Set<String> getGamePlayersName(String gameID){
        Game game = gameList.get(gameID);
        return game.getPlayerNames();
    }

    public void addPlayerToGame(String gameID, Player player){
        Game game = gameList.get(gameID);
        game.addPlayers(player);
        //gameList.put(gameID, game);
        setChanged();
        notifyObservers();
    }

    public void removePlayerFromGame(String gameID, Player player){
        Game game = gameList.get(gameID);
        game.removePlayer(player);
        //gameList.put(gameID, game);
        setChanged();
        notifyObservers();
    }

    public void startGame(String gameId){
        Game game = gameList.get(gameId);
        if (game == null){
            game = new Game();
            game.setId(gameId);
            addGameToList(game);
        }
        game.setStarted(true);
        setChanged();
        notifyObservers();
    }

    public boolean isGameStarted(String gameId){
        Game game = gameList.get(gameId);
        if (game == null){
            return false;
        }
        else {
            return game.isStarted();
        }

    }
}
