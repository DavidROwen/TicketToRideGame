package ticket.com.tickettoridegames.utility.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

public class Game {

    //General game data
    private Map<String, Player> players;
    private List<Chat> chatList;
    private String id;
    private String name;
    private int  maxPlayers;
    private int numberOfPlayers;
    private boolean isStarted;
    private Chat newestChat;

    // Stores the playerIDs in turn order
    private List<String> turnOrder;
    private Integer turnNumber = 0;
    private List<TrainCard> trainBank;

    // Map data
    private GameMap map;
    private Stack<DestinationCard> destinationCards;
    private Map<String, DestinationCard> claimedDestinationCards;
    private TrainCard topTrainCard;

    // Stores player actions viewed in the stats history tab
    private List<PlayerAction> gameHistory;

    public Game(){
        this.players = new HashMap<>();
        this.chatList = new ArrayList<>();
        this.turnOrder = new LinkedList<>();
        this.map = map;
        this.destinationCards = new Stack<>();
        trainBank = new LinkedList<>();
        fillDestinationCards();
        this.claimedDestinationCards = new HashMap<>();
        this.gameHistory = new LinkedList<>();
    }

    public Game(String name, int numberOfPlayers){
        //collection subject to change
        this.players = new HashMap<>();
        this.chatList = new ArrayList<>();
        this.name = name;
        this.maxPlayers = numberOfPlayers;
        this.numberOfPlayers = 0;
        this.id = UUID.randomUUID().toString();
        this.isStarted = false;
        this.newestChat = null;
        trainBank = new LinkedList<>();

        this.isStarted = false;
        this.turnOrder = new LinkedList<>();
        this.map = map;
        this.destinationCards = new Stack<>();
        fillDestinationCards();
        this.claimedDestinationCards = new HashMap<>();
        this.gameHistory = new LinkedList<>();

        setupRoutes();
    }

    public void setupRoutes(){
        // this function can set the default route data
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getPlayersId(){
        return players.keySet();
    }

    public String getPlayerNamesString() {
        String string = "";
        for (String key:players.keySet()) {
            string += players.get(key).getUsername() + " ";
        }
        return string;
    }

    public Set<String> getPlayerNames() {
        Set<String> names = new HashSet<>();
        for (String key:players.keySet()) {
            names.add(players.get(key).getUsername());
        }
        return names;
    }

    public boolean addPlayers(Player p){
        if(numberOfPlayers != maxPlayers) {
            //check if already in game.
            if(players.containsKey(p.getId())){
                //call the join game function
                return true;
            }
            else{
                players.put(p.getId(), p);
                numberOfPlayers++;
                return true;
            }
        }
        else{
            if(players.containsKey(p.getId())){
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean removePlayer(Player p){
        if (players.containsValue(p)){
            players.remove(p);
            return true;
        }
        else {
            return false;
        }
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public void addToChat(Chat c){
        chatList.add(newestChat);
        newestChat = c;
    }

    public Chat getNewestChat(){
        return newestChat;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public Player getPlayer(String userId) {
        return players.get(userId);
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public List<String> getTurnOrder() {
        return turnOrder;
    }

    public GameMap getMap() {
        return map;
    }

    public Stack<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public Map<String, DestinationCard> getClaimedDestinationCards() {
        return claimedDestinationCards;
    }

    public List<PlayerAction> getGameHistory() {
        return gameHistory;
    }

    public Map<String,Integer> getCountsOfPoints() {
        Map<String,Integer> points = new HashMap<>();

        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            points.put(curPlayer.getId(), curPlayer.getPoints());
        }

        return points;
    }

    public Map<String,Integer> getCountsOfCardsInHand() {
        Map<String,Integer> counts = new HashMap<>();

        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            counts.put(curPlayer.getId(), curPlayer.getTrainCards().size());
        }

        return counts;
    }

    public Map<String,Integer> getCountsOfTrains() {
        Map<String,Integer> counts = new HashMap<>();

        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            counts.put(curPlayer.getId(), curPlayer.getTrains());
        }

        return counts;
    }

    public Map<String,Integer> getCountsOfRoutes() {
//        Map<String,Integer> counts = new HashMap<>();
//
//        for(String curKey : players.keySet()) {
//            Player curPlayer = players.get(curKey);
//            counts.put(curPlayer.getId(), curPlayer.getTrainCards().size());
//        }
//
//        return counts;
        return null;
        //todo
    }

    public TrainCard getTopTrainCard() {
        if(topTrainCard == null) { takeTopTrainCard(); } //cycle through it
        return topTrainCard;
    }

    public TrainCard takeTopTrainCard() {
        TrainCard card = topTrainCard;

        //set a new rand card
        Integer randInt = Math.abs(new Random().nextInt() % TrainCard.TRAIN_TYPE.values().length);
        topTrainCard = new TrainCard(TrainCard.TRAIN_TYPE.values()[randInt]);

        if(card == null) { return takeTopTrainCard(); } //cycle through it
        return card;
    }

    public Map<String,Integer> getTrainCounts() {
        Map<String,Integer> counts = new HashMap<>();

        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            counts.put(curPlayer.getId(), curPlayer.getTrains());
        }

        return counts;
    }

//    public void switchTurn() {
//        turnNumber = (turnNumber + 1) % players.size();
//    }

//    //player up is the player who's turn it currently is
//    public String getIdPlayerUp() {
//        return turnOrder.get(turnNumber);
//    }

    private void initTurnOrder() {
        //todo randomize
        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            turnOrder.add(curPlayer.getId());
        }
    }

    public void initGame() {
        initTurnOrder();
        initColors();
        //todo lay out face up cards
        takeTopTrainCard();

        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            initHand(curPlayer);
            initPlayerDestinationCards(curPlayer);
        }
    }

    //give player 3 destination cards to start the game
    private void initPlayerDestinationCards(Player curPlayer) {
        for(int i = 0; i < 3; i++) {
            curPlayer.addDestinationCard(getTopDestinationCard());
        }
    }

    //max of 7 players //for 7 colors
    private void initColors() {
        int i = 0;
        for(String curKey : players.keySet()) {
            Player.COLOR nextColor = Player.COLOR.values()[i];
            players.get(curKey).setColor(nextColor);
            i++;
        }
    }

    private void initHand(Player player) {
        for(int i = 0; i < 4; i++) {
            player.addTrainCard(takeTopTrainCard());
        }
    }

    public DestinationCard takeTopDestinationCard() {
        return destinationCards.pop();
    }

    public DestinationCard getTopDestinationCard() {
        return destinationCards.peek();
    }

    private void fillDestinationCards() {
        assert destinationCards != null;

        //temporary fix
        destinationCards.add(new DestinationCard(new City("Saltlake City"), new City("Portland"), 10));
        destinationCards.add(new DestinationCard(new City("Portland"), new City("Saltlake City"), 10));
        destinationCards.add(new DestinationCard(new City("Seattle"), new City("Saltlake City"), 10));
    }

    public void addDestinationCard(DestinationCard card) {
        destinationCards.add(card); //todo should add to bottom
    }

    public void setTurnOrder(List<String> turnOrder) {
        this.turnOrder = turnOrder;
    }

    public Map<String, Player.COLOR> getPlayersColors() {
        Map<String, Player.COLOR> colors = new HashMap<>();

        for(String id : players.keySet()) {
            colors.put(id, players.get(id).getColor());
        }

        return colors;
    }

    public void setPlayersColors(Map<String, Player.COLOR> colors) {
        for(String id : players.keySet()) {
            Player.COLOR color = colors.get(id);
            players.get(id).setColor(color);
        }
    }

    public void removeDestinationCard() {
        destinationCards.pop();
    }

    public void setTopTrainCard(TrainCard topTrainCard) {
        this.topTrainCard = topTrainCard;
    }

    public void pickupTrainCard(String playerId, Integer index) {
        //get card
        TrainCard pickedCard = trainBank.get(index);
        //add card
        players.get(playerId).addTrainCard(pickedCard);
        //replace card
        trainBank.set(index, takeTopTrainCard());
    }

    public List<TrainCard> getTrainBank() {
        return trainBank;
    }

    public void setTrainBank(List<TrainCard> trainBank) {
        this.trainBank = trainBank;
    }
}
