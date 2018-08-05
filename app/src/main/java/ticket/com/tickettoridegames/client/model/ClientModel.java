package ticket.com.tickettoridegames.client.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.Stack;

import ticket.com.tickettoridegames.client.State.NotMyTurnState;
import ticket.com.tickettoridegames.client.State.PlayerState;
import ticket.com.utility.TYPE;
import ticket.com.utility.model.Chat;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Pair;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.PlayerAction;
import ticket.com.utility.model.PlayerStats;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

import static ticket.com.utility.TYPE.ALLHISTORY;
import static ticket.com.utility.TYPE.BANKUPDATE;
import static ticket.com.utility.TYPE.GAME_OVER;
import static ticket.com.utility.TYPE.NEW_DESTINATION_CARD;
import static ticket.com.utility.TYPE.DISCARDDESTINATION;
import static ticket.com.utility.TYPE.HISTORYUPDATE;
import static ticket.com.utility.TYPE.MAP_DREW_TRAINCARD;
import static ticket.com.utility.TYPE.NEWCHAT;
import static ticket.com.utility.TYPE.NEWROUTE;
import static ticket.com.utility.TYPE.NEWTEMPDECK;
import static ticket.com.utility.TYPE.NEWTRAINCARD;
import static ticket.com.utility.TYPE.REMOVED_PLAYER;
import static ticket.com.utility.TYPE.ROUTECLAIMED;
import static ticket.com.utility.TYPE.START;
import static ticket.com.utility.TYPE.TURN_NUMBER_CHANGED;


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

    private String newestGameId;
    private Pair<String, String> newestPlayerAdded;

    private PlayerState currentState;

    private ClientModel() {
        if(gameList == null) {
            gameList = new HashMap<>();
        }
        setState(NotMyTurnState.getInstance());
    }

    public PlayerState getCurrentState(){
        return currentState;
    }

    public void setState(PlayerState newState){
        if(currentState != null){
            currentState.exit(this);
        }
        currentState = newState;
        if(currentState != null){
            System.out.println("changing to state: " + newState.getClass().toString());
            currentState.enter(this);
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
        newestGameId = game.getId();

        // notify lobby presenter
        myNotify(TYPE.NEW_GAME_ADDED);
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
        newestPlayerAdded = new Pair<String, String>(gameID, player.getId());
        //gameList.put(gameID, game);
        myNotify(TYPE.PLAYER_ADDED);
    }

    public void removePlayerFromGame(String gameID, String playerId){
        Game game = gameList.get(gameID);

        if (getCurrentGameID() != null && gameID.equals(getCurrentGameID())){
            myActiveGame = null;
        }

        game.removePlayer(playerId);

        setChanged();
        notifyObservers(REMOVED_PLAYER);
    }


    public void startGame(String gameId){
        Game game = gameList.get(gameId);
        game.setStarted(true);

        myNotify(START);
    }

    public boolean isGameStarted(String gameId) {
        Game game = gameList.get(gameId);
        return game != null && game.isStarted();
    }

    public Game getMyActiveGame() {
        if(myActiveGame == null) { return locateMyActiveGame(); }
        return myActiveGame;
    }

    private Game locateMyActiveGame() {
        //check every player in every game
        for (String curKey : gameList.keySet()) {
            Game curGame = gameList.get(curKey);
            if (curGame.getPlayer(getUserId()) != null) {
                myActiveGame = curGame;
                return myActiveGame;
            }
        }
        return null;
    }

    public Player getMyPlayer() {
        if(myPlayer != null) { return myPlayer; } //convenience function
        if (locateMyActiveGame() != null) {
            return getMyActiveGame().getPlayer(getUserId());
        }
        return null;
    }

    public List<PlayerAction> getHistory(){
        Game myGame = getMyActiveGame();
        return myGame.getGameHistory();
    }

    //DestinationCards functions
    public void setMyPlayerTempDeck(List<DestinationCard> deck){
        getMyActiveGame().getPlayer(getUserId()).setTempDeck(deck);

        Player player = getMyPlayer();
        player.setTempDeck(deck);
        myNotify(NEWTEMPDECK);
    }

    public void clearTempDeck(){
        getMyPlayer().clearTempDeck();
    }

    public void updateDestinationCards(String playerId, List<DestinationCard> cards){
        Game game = getMyActiveGame();
        game.claimDestinationCards(cards, playerId);
        myNotify(NEW_DESTINATION_CARD);
        myNotify(HISTORYUPDATE);
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
//        myNotify(HISTORYUPDATE);
    }

    public PlayerAction getNewestGameHistory(){
        Game game = getMyActiveGame();
        return game.getNewestHistory();
    }
    //END Game History functions

    //GamePlay functions
    public void setTurnOrder(LinkedList<String> order){
        getMyActiveGame().setTurnOrder(order);
    }

    public void setPlayersColors(HashMap<String, Player.COLOR> colors){
        getMyActiveGame().setPlayersColors(colors);
    }

    public void setTrainCardsDeck(Stack<TrainCard> deck){
        getMyActiveGame().setTrainCardsDeck(deck);
    }

    public void initMyGameNonRandom(){
        getMyActiveGame().initGameNonRandom();
        myNotify(ALLHISTORY);
        myNotify(TURN_NUMBER_CHANGED);
        myNotify(NEWTRAINCARD);
        myNotify(BANKUPDATE);
    }

    public List<PlayerStats> getPlayerStats(){
        return getMyActiveGame().getPlayerStats();
    }

    public void drawTrainCard(String playerId){
        getMyActiveGame().drawTrainCard(playerId);
        myNotify(NEWTRAINCARD);
        myNotify(HISTORYUPDATE);
        if(getUser().getId().equals(playerId)) { myNotify(MAP_DREW_TRAINCARD); }
    }

    public void pickupTrainCard(String playerId, Integer index){
        getMyActiveGame().pickupTrainCard(playerId, index);
        myNotify(NEWTRAINCARD);
        myNotify(BANKUPDATE);
        myNotify(HISTORYUPDATE);
    }

    public List<TrainCard> getTrainBank(){
        return getMyActiveGame().getTrainBank();
    }

    public Stack<TrainCard> getTrainCardsDeck(){
        return getMyActiveGame().getTrainCardsDeck();
    }

    public List<DestinationCard> getDestinationCards(){
        return getMyActiveGame().getDestinationCards();
    }
    //END Gameplay functions

    private void myNotify(Object arg) {
        setChanged();
        if(arg != null) { notifyObservers(arg); }
        else { notifyObservers(); }
    }

    //when the user clicks on endTurn button
    public void changeTurn(String gameId){
        Game game = getGame(gameId);
        game.switchTurn();

//        if (game.isMyTurn(getMyPlayer().getId())){
//            setState(MyTurnState.getInstance());
//        }
//        else {
//            setState(NotMyTurnState.getInstance());
//        }

        setChanged();
        notifyObservers(TURN_NUMBER_CHANGED);
    }



    public String getTurnUsername(){
        return getMyActiveGame().getTurnUsername();
    }

    public Result claimRoute(String playerID, String route, TrainCard.TRAIN_TYPE decidedType) {
        Result result = getMyActiveGame().claimRoute(playerID, route, decidedType);

        if (result.isSuccess()) {
            myNotify(NEWROUTE);
            myNotify(NEWTRAINCARD);
            myNotify(ROUTECLAIMED);
            myNotify(HISTORYUPDATE);

            getMyActiveGame().switchTurn();
            myNotify(TURN_NUMBER_CHANGED);
        }

        return result;
    }

    public List<Pair<String, Integer>> getClaimedRoutes(){
        return getMyActiveGame().getClaimedRoutes();
    }

    public boolean isMyTurn(){
        return getMyActiveGame().isMyTurn(getMyPlayer().getId());
    }

    private Game getGame(String gameId){
        return gameList.get(gameId);
    }

    public Pair<String,Integer> getNewestClaimedRoute() {
        return getMyActiveGame().getNewestClaimedRoute();
    }

    public void resetBank(String gameId){
        gameList.get(gameId).resetTrainBank();

        if (getMyActiveGame().getId().equals(gameId)){
            setChanged();
            notifyObservers(BANKUPDATE);
        }
    }

    public String getNewestGameId() {
        return newestGameId;
    }

    public Pair<String, String> getNewestPlayerAdded() {
        return newestPlayerAdded;
    }

    public boolean isInitialized() {
        return getMyActiveGame() != null && getMyActiveGame().isInitialized();
    }

    public Boolean getGameOver(String gameId) {
        return gameList.get(gameId).getGameOver();
    }

    public void setGameOver(String gameId, Boolean gameOver) {
        gameList.get(gameId).setGameOver(gameOver);

        setChanged();
        notifyObservers(GAME_OVER);
    }
}
