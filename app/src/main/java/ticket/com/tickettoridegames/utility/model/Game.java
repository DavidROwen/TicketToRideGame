package ticket.com.tickettoridegames.utility.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import ticket.com.tickettoridegames.utility.TYPE;

public class Game extends Observable {
    public static final Integer LENGTH_TO_POINTS[] = new Integer[]{1,2,4,7,10,15};

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
    private Map<String, List<Route>> routes = new HashMap<>();

    // Map data
    private GameMap map;
    private List<DestinationCard> destinationCards;
    private Stack<TrainCard> trainCardsDeck = new Stack<>();
//    private TrainCard topTrainCard;

    // Stores player actions viewed in the stats history tab
    private List<PlayerAction> gameHistory = new LinkedList<>();
    private PlayerAction newestHistory;
    public static final Integer NUM_CARDS_TRAINCARD_DECK = 52;
    public static final Integer NUM_CARDS_TRAINCARD_BANK = 5;

    public Game(){
        this.players = new HashMap<>();
        this.chatList = new ArrayList<>();
        this.turnOrder = new LinkedList<>();
        this.map = new GameMap();
        this.destinationCards = new LinkedList<>();
        this.trainBank = new LinkedList<>();
        fillDestinationCards();

        fillDestinationCards();
        setupRoutes();
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
        this.trainBank = new LinkedList<>();
        this.isStarted = false;
        this.turnOrder = new LinkedList<>();
        this.map = new GameMap();
        this.destinationCards = new LinkedList<>();

        fillDestinationCards();
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
                routes.put(p.getId(), new ArrayList<>());
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
        myNotify(TYPE.NEWCHAT);
    }

    public void addToHistory(PlayerAction pa){
        if(newestHistory != null) {  //first time
            gameHistory.add(newestHistory);
        }
        newestHistory = pa;
        myNotify(TYPE.HISTORYUPDATE);
    }

    public Chat getNewestChat(){
        return newestChat;
    }

    public PlayerAction getNewestHistory(){
        return newestHistory;
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

    public List<DestinationCard> getDestinationCards() {
        return destinationCards;
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

    public Map<String, Integer> getCountsOfRoutes() {
        Map<String,Integer> counts = new HashMap<>();

        for(String curId : players.keySet()) {
            counts.put(curId, routes.get(curId).size());
        }

        return counts;
    }

    private TrainCard getRandomTrainCard() {
        Integer randInt = Math.abs(new Random().nextInt() % TrainCard.TRAIN_TYPE.values().length);
        return new TrainCard(TrainCard.TRAIN_TYPE.values()[randInt]);
    }

    public void drawTrainCard(String playerId) {
        TrainCard card = trainCardsDeck.pop();
        players.get(playerId).addTrainCard(card);
        myNotify(TYPE.NEWTRAINCARD);
        addToHistory(new PlayerAction(players.get(playerId).getUsername(), "drew " + card.getType()));
    }

    private TrainCard drawTrainCard() {
        return trainCardsDeck.pop();
    }

    public Map<String,Integer> getTrainCounts() {
        Map<String,Integer> counts = new HashMap<>();

        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            counts.put(curPlayer.getId(), curPlayer.getTrains());
        }

        return counts;
    }

    public void switchTurn() {
        turnNumber = (turnNumber + 1) % players.size();
    }

    public Boolean isMyTurn(String playerId) {
        return playerId.equals(turnOrder.get(turnNumber));
    }

    private void initTurnOrder() {
        //all players
        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            turnOrder.add(curPlayer.getId());
        }
        //shuffle
        Collections.shuffle(turnOrder);
    }

    private void initTrainCardDeck() {
        for(int i = 0; i < NUM_CARDS_TRAINCARD_DECK; i++) {
            trainCardsDeck.push(getRandomTrainCard());
        }
    }

    public void initGame() {
        initTurnOrder();
        initColors();
        initTrainCardDeck();
    }

    //convenience function so it can be called from the client side also
    //must be seperate so train deck can be passed before altered
    public void initGameNonRandom() {
        initHandAll();
        initTrainBank();
//        initPlayerDestinationCards();
    }

    private void initTrainBank() {
        for(int i = 0; i < NUM_CARDS_TRAINCARD_BANK; i++) {
            try {
                trainBank.add(drawTrainCard());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initHandAll() {
        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            initHand(curPlayer);
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
            drawTrainCard(player.getId());
        }
    }

    //Destination Card Functions
    private DestinationCard getTopDestinationCard(){
        DestinationCard dc = destinationCards.get(0);
        destinationCards.remove(0);
        return dc;
    }

    public List drawDestinationCards(){
        //list type subject to change
        ArrayList<DestinationCard> list = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            list.add(getTopDestinationCard());
        }
        return list;
    }

    public void claimDestinationCards(List<DestinationCard> cards, String playerId){
        Player player = players.get(playerId);
        try {
            for (DestinationCard card : cards) {
                player.addDestinationCard(card);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void discardDestinationCards(List<DestinationCard> cards){
        for(DestinationCard card : cards){
            addDestinationCard(card);
        }
    }

    private void addDestinationCard(DestinationCard card) {
        destinationCards.add(card);
//        addToHistory(new PlayerAction(playerId, "drew a destination card")); //todo
    }

    private void fillDestinationCards() {
        assert destinationCards != null;

        //temporary fix
        destinationCards.add(new DestinationCard(new City("Los Angeles"), new City("New York City"), 21));
        destinationCards.add(new DestinationCard(new City("Duluth"), new City("Houston"), 8));
        destinationCards.add(new DestinationCard(new City("Sault Ste. Marie"), new City("Nashville"), 8));
        destinationCards.add(new DestinationCard(new City("New York City"), new City("Atlanta"), 6));
        destinationCards.add(new DestinationCard(new City("Portland"), new City("Nashville"), 17));
        destinationCards.add(new DestinationCard(new City("Vancouver"), new City("Montreal"), 20));
        destinationCards.add(new DestinationCard(new City("Duluth"), new City("El Paso"), 10));
        destinationCards.add(new DestinationCard(new City("Toronto"), new City("Miami"), 10));
        destinationCards.add(new DestinationCard(new City("Portland"), new City("Phoenix"), 11));
        destinationCards.add(new DestinationCard(new City("Dallas"), new City("New York City"), 11));
        destinationCards.add(new DestinationCard(new City("Calgary"), new City("Salt Lake City"), 7));
        destinationCards.add(new DestinationCard(new City("Calgary"), new City("Phoenix"), 13));
        destinationCards.add(new DestinationCard(new City("Los Angeles"), new City("Miami"), 20));
        destinationCards.add(new DestinationCard(new City("Winnipeg"), new City("Little Rock"), 11));
        destinationCards.add(new DestinationCard(new City("San Francisco"), new City("Atlanta"), 17));
        destinationCards.add(new DestinationCard(new City("Kansas City"), new City("Houston"), 5));
        destinationCards.add(new DestinationCard(new City("Los Angeles"), new City("Chicago"), 16));
        destinationCards.add(new DestinationCard(new City("Denver"), new City("Pittsburgh"), 11));
        destinationCards.add(new DestinationCard(new City("Chicago"), new City("Santa Fe"), 9));
        destinationCards.add(new DestinationCard(new City("Vancouver"), new City("Santa Fe"), 13));
        destinationCards.add(new DestinationCard(new City("Boston"), new City("Miami"), 12));
        destinationCards.add(new DestinationCard(new City("Chicago"), new City("New Orleans"), 7));
        destinationCards.add(new DestinationCard(new City("Montreal"), new City("Atlanta"), 9));
        destinationCards.add(new DestinationCard(new City("Seattle"), new City("New York"), 22));
        destinationCards.add(new DestinationCard(new City("Denver"), new City("El Paso"), 4));
        destinationCards.add(new DestinationCard(new City("Helena"), new City("Los Angeles"), 8));
        destinationCards.add(new DestinationCard(new City("Winnipeg"), new City("Houston"), 12));
        destinationCards.add(new DestinationCard(new City("Montreal"), new City("New Orleans"), 13));
        destinationCards.add(new DestinationCard(new City("Sault Ste. Marie"), new City("Oklahoma City"), 9));
        destinationCards.add(new DestinationCard(new City("Seattle"), new City("Los Angeles"), 9));
        Collections.shuffle(destinationCards);
    }
    //END Destination Card Functions

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
            String colorStr = String.valueOf(colors.get(id)); //doesn't know that it was deserialized as a string
            Player.COLOR color = Player.COLOR.valueOf(colorStr);
            players.get(id).setColor(color);
        }
    }

    public void pickupTrainCard(String playerId, Integer index) {
        //add card
        TrainCard pickedCard = trainBank.get(index);

        players.get(playerId).addTrainCard(pickedCard);
        myNotify(TYPE.NEWTRAINCARD);
        //replace card
        trainBank.set(index, drawTrainCard());
        myNotify(TYPE.BANKUPDATE);
        addToHistory(new PlayerAction(players.get(playerId).getUsername(), "picked up " + pickedCard.getType()));
    }

    public List<TrainCard> getTrainBank() {
        return trainBank;
    }

    public Boolean claimRoute(String playerId, Route route) {
        Player player = players.get(playerId);

        //check not claimed
        if(isClaimed(route)) { return false; }

        //check player has cards
        TrainCard[] neededCards = getNeededCards(route);
        if(!player.hasTrainCards(neededCards)) { return false; }

        //check player has trains
        if(!player.hasTrains(route.getLength())) { return false; }

        //claim
        routes.get(playerId).add(route);
        player.removeTrainCards(neededCards); //cash out cards
        myNotify(TYPE.NEWTRAINCARD);
        player.addPoints(LENGTH_TO_POINTS[route.getLength()]); //collect points
        player.removeTrains(route.getLength());
        myNotify(TYPE.STATSUPDATE);
        addToHistory(new PlayerAction(player.getUsername(), "claimed " + route.getStart() + " to " + route.getEnd()));
        return true;
    }

    private TrainCard[] getNeededCards(Route route) {
        TrainCard[] neededCards = new TrainCard[route.getLength()];
        Arrays.fill(neededCards, new TrainCard(route.getType()));
        return neededCards;
    }

    private Boolean isClaimed(Route route) {
        for(String id : players.keySet()) {
            for(Route cur : routes.get(id)) {
                if(cur == route) { return true; }
            }
        }

        return false;
    }

    public Map<String, List<Route>> getRoutes() {
        return routes;
    }


    public List<PlayerStats> getPlayerStats(){
        List<PlayerStats> stats = new ArrayList<>();

        for (Player player : getPlayers().values()){
            stats.add(player.getStats());
        }

        return stats;
    }

    public Stack<TrainCard> getTrainCardsDeck() {
        return trainCardsDeck;
    }

    public void setTrainCardsDeck(Stack<TrainCard> trainCardsDeck) {
        this.trainCardsDeck = trainCardsDeck;
    }

    private void myNotify(Object arg) {
        setChanged();
        if(arg != null) { notifyObservers(arg); }
        else { notifyObservers(); }
//        clearChanged();
    }
}
