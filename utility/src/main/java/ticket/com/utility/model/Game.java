package ticket.com.utility.model;

import java.util.ArrayList;
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

import ticket.com.utility.web.Result;


public class Game extends Observable {
    //General game data
    private Map<String, Player> players = new HashMap<>();
    private List<Chat> chatList = new LinkedList<>();
    private String id = "";
    private String name = "";
    private int maxPlayers = 2;
    private int numberOfPlayers = 0;
    private boolean isStarted = false;
    private Chat newestChat;
    private Boolean gameOver = false;

    // Stores the playerIDs in turn order
    private List<String> turnOrder = new ArrayList<>();
    private Integer turnNumber = 0;
    private List<TrainCard> trainBank = new LinkedList<>();

    // Map data
    private GameMap map = new GameMap();
    private List<DestinationCard> destinationCards = new ArrayList<>();
    private Stack<TrainCard> trainCardsDeck = new Stack<>();
    private List<TrainCard> trainDiscards = new ArrayList<>();

    // Stores player actions viewed in the stats history tab
    private List<PlayerAction> gameHistory = new LinkedList<>();
    private PlayerAction newestHistory;
    public static final Integer NUM_CARDS_TRAINCARD_BANK = 5;
//    private final int INIT_HAND_SIZE = 4; //comment
    private final int INIT_HAND_SIZE = 15; //comment

    private boolean initializedCorrectly = false;

    public Game(){
        this.players = new HashMap<>();
        this.chatList = new ArrayList<>();
        this.turnOrder = new LinkedList<>();
        this.map = new GameMap();
        this.destinationCards = new LinkedList<>();
        fillDestinationCards();

        fillDestinationCards();
    }

    public Game(String name, int numberOfPlayers){
        //collection subject to change
        this.players = new HashMap<>();
        this.chatList = new ArrayList<>();
        this.name = name;
        this.maxPlayers = numberOfPlayers;
        this.numberOfPlayers = 0;
        this.id = UUID.randomUUID().toString();
        this.newestChat = null;
        this.isStarted = false;
        this.turnOrder = new LinkedList<>();
        this.map = new GameMap();
        this.destinationCards = new LinkedList<>();

        fillDestinationCards();
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

    public Player getPlayerByID(String playerId){
        return players.get(playerId);
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
            return players.containsKey(p.getId());
        }
    }

    public boolean removePlayer(String p){
        if (players.containsKey(p)){
            players.remove(p);
            numberOfPlayers--;
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
        chatList.add(c);
        newestChat = c;
    }

    public void addToHistory(PlayerAction pa){
        gameHistory.add(pa);
        newestHistory = pa;
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

    public void drawTrainCard(String playerId) {
        TrainCard card = drawTrainCard();
        players.get(playerId).addTrainCard(card);
        addToHistory(new PlayerAction(players.get(playerId).getUsername(), "did a blind draw"));
    }

    private TrainCard drawTrainCard() {
        if (trainCardsDeck.empty()){ makeDiscardsToTrainDeck(); }
        return trainCardsDeck.pop();
    }

    private void makeDiscardsToTrainDeck() {
        Collections.shuffle(trainDiscards, new Random(getId().hashCode()));
        trainCardsDeck.addAll(trainDiscards);
        trainDiscards.clear();
    }

    public Map<String,Integer> getTrainCounts() {
        Map<String,Integer> counts = new HashMap<>();

        for(String curKey : players.keySet()) {
            Player curPlayer = players.get(curKey);
            counts.put(curPlayer.getId(), curPlayer.getTrains());
        }

        return counts;
    }

    //only called from gamePlayService
    public void switchTurn() {
        turnNumber = (turnNumber + 1) % players.size();
        System.out.println("It's now " + getTurnUsername() + "'s turn");
    }

    public Boolean isMyTurn(String playerId) {
        if (turnOrder.size() == 0){ //todo then the server and client would be out of sync
            initTurnOrder();
        }
        return playerId.equals(turnOrder.get(turnNumber));
    }

    private void initTurnOrder() {
        //all players
        if (turnOrder.size() == 0) {
            for (String curKey : players.keySet()) {
                Player curPlayer = players.get(curKey);
                turnOrder.add(curPlayer.getId());
            }
        }
    }

    private void initTrainCardDeck() {
        for (TrainCard.TRAIN_TYPE type: TrainCard.TRAIN_TYPE.values()) {
            Integer card_count = 12;
            if (type == TrainCard.TRAIN_TYPE.WILD){
                card_count = 14;
            }
            for (int i = 0; i < card_count; i++) {
                trainCardsDeck.add(new TrainCard(type));
            }
        }
        Collections.shuffle(trainCardsDeck, new Random(getId().hashCode()));
        if(trainCardsDeck.size() != 110) { throw new AssertionError("Train Cards Deck size should be 110 but it was "+trainCardsDeck.size()); }
    }

    private TrainCard getRandomTrainCard() {
        Integer randInt = Math.abs(new Random().nextInt() % TrainCard.TRAIN_TYPE.values().length);
        return new TrainCard(TrainCard.TRAIN_TYPE.values()[randInt]);
    }

    public void initGame() {
        if (!initializedCorrectly) {
            initTurnOrder();
            initColors();
            initTrainCardDeck();
        }
        else {
            System.out.println("initGame() called multiple times :(");
        }
    }

    // TODO: improve the checking here to always be accurate
    public boolean isInitialized(){
        if (id == null) {
            System.out.println("ERROR: Initialization failed because of id");
            return false;
        }
        if (!isStarted) {
            System.out.println("ERROR: Initialization failed because of isStarted");
            return false;
        }
        if (turnOrder == null || turnOrder.size() != players.size()) {
            System.out.println("ERROR: Initialization failed because of turn order");
            return false;
        }
        if (trainBank == null || trainBank.size() != 5) {
            System.out.println("ERROR: Initialization failed because of trainBank");
            return false;
        }
//        if(destinationCards.size() < 2); //called before they've chosen
        if (trainCardsDeck.size() != 110 - 4 * numberOfPlayers - 5) {
            System.out.println("ERROR: Initialization failed because of trainCardsDeck");
            return false;
        } //4 in each hand //5 in the bank
        if (!trainDiscards.isEmpty()) {
            System.out.println("ERROR: Initialization failed because of trainDiscards");
            return false;
        }

        for (String each : players.keySet()) {
            if (!players.get(each).isInitialized()) {
                return false;
            }
        }

        initializedCorrectly = true;
        return true;
    }

    //convenience function so it can be called from the client side also
    //must be separate so train deck can be passed before altered
    public void initGameNonRandom() {
        if (!initializedCorrectly) {
            initHandAll();
            initTrainBank();
        }
        else {
            System.out.println("initGameNonRandom() called multiple times :(");
        }
    }

    public void initHandAll() {
        for(String curKey : turnOrder) { //key set order isn't random
            Player curPlayer = players.get(curKey);
            initHand(curPlayer);
        }
    }

    private void initTrainBank() {
        for(int i = 0; i < NUM_CARDS_TRAINCARD_BANK; i++) {
            trainBank.add(drawTrainCard());
        }
        assert(trainCardsDeck.size() == 110 - (INIT_HAND_SIZE*players.size() + 5));
        assert(trainBank.size() == 5);
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
        for(int i = 0; i < INIT_HAND_SIZE; i++) {
            drawTrainCard(player.getId());
        }
    }

    //Destination Card Functions
    private DestinationCard getTopDestinationCard(){
        DestinationCard dc = destinationCards.get(0);
        destinationCards.remove(0);
        return dc;
    }

    public List<DestinationCard> drawDestinationCards(){
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
        addToHistory(new PlayerAction(players.get(playerId).getUsername(), "drew " + String.valueOf(cards.size()) + " destination cards"));
    }

    /**
     * Places Destination cards in the bottom of the destination card deck
     *
     * @pre cards cannot be empty or null
     * @post destinatonCards.size() == old(destinationCards.size()) + cards.size()
     * @post destinatonCards.size() >= 1
     * @param cards a list of destination cards
     */
    public void discardDestinationCards(List<DestinationCard> cards){
        for(DestinationCard card : cards){
            addDestinationCard(card);
        }
    }

    private void addDestinationCard(DestinationCard card) {
        destinationCards.add(card);
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
        System.out.println(getTurnUsername() + " is up first");
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
        //replace card
        trainBank.set(index, drawTrainCard());
        addToHistory(new PlayerAction(players.get(playerId).getUsername(), "picked up " + pickedCard.getType()));

//        if (tooManyLocos()){
//            resetTrainBank();
//        }
    }

    public boolean tooManyLocos(){
        Integer locoCount = 0;
        for (TrainCard card: trainBank){
            if (card.getType() == TrainCard.TRAIN_TYPE.WILD){
                locoCount++;
                if (locoCount >= 3){
                    return true;
                }
            }
        }
        return false;
    }

    public void resetTrainBank(){
        for (int i = 0; i < NUM_CARDS_TRAINCARD_BANK; i++){
            trainDiscards.add(trainBank.get(i));
            trainBank.set(i, drawTrainCard());
        }
    }

    public List<TrainCard> getTrainBank() {
        return trainBank;
    }

    public Result claimRoute(String playerId, String routeName, TrainCard.TRAIN_TYPE routeType) {
        Player player = players.get(playerId);

        Result result = canClaim(playerId, routeName, routeType);
        if(!result.isSuccess()) { return result; }

        map.claimRoute(playerId, routeName);
        List<TrainCard> discardedCards = player.claimRoute(routeType, map.getLength(routeName), map.getPoints(routeName));
        trainDiscards.addAll(discardedCards);
        System.out.println(player.getUsername() + " discarded " + discardedCards.size() + " cards");
        addToHistory(new PlayerAction(player.getUsername(), "claimed " + map.toString(routeName)));
        // TODO: make this message nicer
        addToHistory(new PlayerAction(player.getUsername(), " claimed " + routeName));


        return result;
    }

    private Result canClaim(String playerId, String routeName, TrainCard.TRAIN_TYPE routeType) {
        //map
        Result mapResult = map.canClaim(routeName, playerId, players.size());
        if(!mapResult.isSuccess()) { return mapResult; }

        //player
        Result playerResult = players.get(playerId).canClaim(routeType, map.getLength(routeName));
        if(!playerResult.isSuccess()) { return playerResult; }

        return new Result(true, null, null);
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
    
    // This function returns all claimed routs associated with the color that they should be on the map
    public List<Pair<String, Integer>> getClaimedRoutes(){
        List<Pair<String, Integer>> claimed = new ArrayList<>();

        for(Pair<String,String> each : map.getClaimedRoutes()) {
            Integer playerColor =  players.get(each.second).getColorValue();
            claimed.add(new Pair<String, Integer>(each.first, playerColor));
        }

        return claimed;
    }

    public String getTurnUsername() {
        return players.get(turnOrder.get(turnNumber)).getUsername();
    }

    public Pair<String, Integer> getNewestClaimedRoute() {
        Pair<String, String> routeInfo = map.getNewestClaimedRoute();
        Integer playerColor =  players.get(routeInfo.second).getColorValue();

        return new Pair<String, Integer>(routeInfo.first, playerColor);
    }


    public void addDestinationCardPoints(Player player){
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        List<Route> playersRoutes = map.getPlayersRoutes(player.getId());
        List<Route> storedRoutes = new ArrayList<>();
        Integer points = 0;

        for (Route route:playersRoutes) {
            if(!storedRoutes.contains(route)) {
                ArrayList<String> group = new ArrayList<>();
                group.add(route.getStartCity());
                group.add(route.getEndCity());
                storedRoutes.add(route);

                while (true) {
                    Boolean Continue = false;
                    for (Route innerRoute : playersRoutes) {
                        if(!storedRoutes.contains(innerRoute)) {
                            if (group.contains(innerRoute.getStartCity())) { //never gets here
                                group.add(innerRoute.getEndCity());
                                storedRoutes.add(innerRoute);
                                Continue = true;
                            } else if (group.contains(innerRoute.getEndCity())) {
                                group.add(innerRoute.getStartCity());
                                storedRoutes.add(innerRoute);
                                Continue = true;
                            }
                        }
                    }
                    if(!Continue){
                        break;
                    }
                }
                groups.add(group);
            }
        }

        for(DestinationCard destinationCard:player.getDestinationCards()) { //iterates through owned destinations and gives points for completed ones
            for(ArrayList<String> group:groups){
                if(group.contains(destinationCard.getLocation()) && group.contains(destinationCard.getLocation2())){
                    destinationCard.setCompleted();
                    points = points + destinationCard.getValue();
                }
            }
            if(!destinationCard.isCompleted()){
                points = points - destinationCard.getValue();
            }
        }

        player.addPoints(points); // gives player points from destination cards
    }

//    public ArrayList<ArrayList<String>> TestCompletedDestinationPoints(List<Route> playersRoutes){
//        ArrayList<ArrayList<String>> groups = new ArrayList<>();
//        Integer points = 0;
//        List<Route> storedRoutes = new ArrayList<>();
//
//        for (Route route:playersRoutes) {
//            if(!storedRoutes.contains(route)) {
//                ArrayList<String> group = new ArrayList<>();
//                group.add(route.getStartCity());
//                group.add(route.getEndCity());
//                storedRoutes.add(route);
//
//                while (true) {
//                    Boolean Continue = false;
//                    for (Route innerRoute : playersRoutes) {
//                        if(!storedRoutes.contains(innerRoute)) {
//                            if (group.contains(innerRoute.getStartCity())) { //never gets here
//                                group.add(innerRoute.getEndCity());
//                                storedRoutes.add(innerRoute);
//                                Continue = true;
//                            } else if (group.contains(innerRoute.getEndCity())) {
//                                group.add(innerRoute.getStartCity());
//                                storedRoutes.add(innerRoute);
//                                Continue = true;
//                            }
//                        }
//                    }
//                    if(!Continue){
//                        break;
//                    }
//                }
//                groups.add(group);
//            }
//        }
//
//        return groups;
//    }

    public boolean isBankCardWild(Integer index){
        TrainCard card = getTrainBank().get(index);
        return card.getType() == TrainCard.TRAIN_TYPE.WILD;
    }

    public boolean isRouteWild(String routeName){
        return map.getType(routeName) == TrainCard.TRAIN_TYPE.WILD;
    }

    public TrainCard.TRAIN_TYPE getType(String routeName) {
        return map.getType(routeName);
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public List<TrainCard> getTrainDiscards() {
        return Collections.unmodifiableList(trainDiscards);
    }
}
