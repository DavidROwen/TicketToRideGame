package ticket.com.tickettoridegames.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.PlayerAction;
import ticket.com.tickettoridegames.utility.model.PlayerStats;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.model.User;

import static ticket.com.tickettoridegames.utility.TYPE.DESTINATIONUPDATE;
import static ticket.com.tickettoridegames.utility.TYPE.DISCARDDESTINATION;
import static ticket.com.tickettoridegames.utility.TYPE.HISTORYUPDATE;
import static ticket.com.tickettoridegames.utility.TYPE.NEWCHAT;
import static ticket.com.tickettoridegames.utility.TYPE.NEWROUTE;
import static ticket.com.tickettoridegames.utility.TYPE.NEWTEMPDECK;
import static ticket.com.tickettoridegames.utility.TYPE.NEWTRAINCARD;
import static ticket.com.tickettoridegames.utility.TYPE.START;

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

    private Game myActiveGame = null;
    private Player myPlayer = null;

    private ClientModel() {
        if(gameList == null) {
            gameList = new HashMap<>();
        }
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

    public Chat getNewestChat(String gameID){
        Game game = gameList.get(gameID);
        return game.getNewestChat();
    }

    public void addGameChat(String gameID, Chat chat){
        Game game = gameList.get(gameID);
        game.addToChat(chat);
        setChanged();
        notifyObservers(NEWCHAT);
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
        notifyObservers(START);
    }

    public boolean isGameStarted(String gameId) {
        Game game = gameList.get(gameId);
        return game != null && game.isStarted();
    }

    public Game getMyActiveGame() {
        if(myActiveGame == null) { locateMyActiveGame(); }
        return myActiveGame;
    }

    private void locateMyActiveGame() {
        //check every player in every game
        for (String curKey : gameList.keySet()) {
            Game curGame = gameList.get(curKey);
            if (curGame.getPlayer(ClientModel.get_instance().getUserId()) != null) {
                myActiveGame = curGame;
                break; //done
            }
        }
    }

    public Player getMyPlayer() {
        if(myPlayer != null) { return myPlayer; } //convenience function
        return getMyActiveGame().getPlayer(ClientModel.get_instance().getUserId());
    }

    public List<PlayerAction> getHistory(){
        Game myGame = getMyActiveGame();
        return myGame.getGameHistory();
    }

    //DestinationCards functions
    public void setMyPlayerTempDeck(List<DestinationCard> deck){
        Player player = getMyPlayer();
        player.setTempDeck(deck);
        myNotify(NEWTEMPDECK);
    }

    public void updateDestinationCards(String playerId, List<DestinationCard> cards){
        Game game = getMyActiveGame();
        game.claimDestinationCards(cards, playerId);
        myNotify(DESTINATIONUPDATE);
    }

    public void discardDestinationCards(List<DestinationCard> cards){
        Game game = getMyActiveGame();
        game.discardDestinationCards(cards);
        myNotify(DISCARDDESTINATION);
    }
    //END Destination Card Functions

    //Game History Functions
    public void addGameHistory(PlayerAction history){
        Game game = getMyActiveGame();
        game.addToHistory(history);
        myNotify(HISTORYUPDATE);
    }

    public PlayerAction getNewestGameHistory(){
        Game game = getMyActiveGame();
        return game.getNewestHistory();
    }
    //END Game History functions

    private void myNotify(Object arg) {
        setChanged();
        if(arg != null) { notifyObservers(arg); }
        else { notifyObservers(); }
//        clearChanged();
    }
}
