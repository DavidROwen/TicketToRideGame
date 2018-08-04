package ticket.com.utility.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.utility.web.Result;

public class Player {
    public enum COLOR {RED, YELLOW, GREEN, BLUE, BLACK}

    private String username = "";
    private String id = "";

    // Game Data
    private COLOR color = COLOR.RED;
    private Integer trains = 45;
    private Integer points = 0;

    private List<DestinationCard> tempDeck = new LinkedList<>();
    private List<TrainCard> trainCards = new LinkedList<>();
    private TrainCard newestTrainCard;
    private Set<DestinationCard> destinationCards = new HashSet<>();

    public Player(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public boolean isInitialized() {
        if(id.equals("")) { return false; }
//        color
        if(trainCards.size() != 4) { return false; }
//        destinationCards

        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public Integer getTrains() {
        return trains;
    }

    public void setTrains(Integer trains) {
        this.trains = trains;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Set<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void addDestinationCard(DestinationCard card) {
        destinationCards.add(card);
    }

    public void addTrainCard(TrainCard card) {
        trainCards.add(card);
        newestTrainCard = card;
        System.out.println(username + " added " + card.toString());
    }

    private Boolean removeTrainCards(TrainCard...cards) {
        if(!hasTrainCards(cards)) { return false; }

        int wildsUsed = 0;
        for(TrainCard card : cards) {
            if(!trainCards.remove(card)) {
                trainCards.remove(new TrainCard(TrainCard.TRAIN_TYPE.WILD));
                wildsUsed++;
            }
        }
        System.out.println(username + " used " + (cards.length - wildsUsed) + " " + cards[0].toString() +
            " and " + wildsUsed + " WILD");

        return true;
    }

    //assuming all the same kind in parameter
//    public Boolean hasTrainCards(TrainCard...cards) {
//        TrainCard[] adjustedCards = adjustForWilds(cards);
//
//        //convert it to strings for comparison
//        List<String> myTypes = new LinkedList<>();
//        List<String> cardsTypes = new LinkedList<>();
//        for(TrainCard card : trainCards) { myTypes.add(card.getType().toString()); }
//        for(TrainCard card : adjustedCards) { cardsTypes.add(card.getType().toString()); }
//
//        return myTypes.containsAll(cardsTypes);
//    }

    public Boolean hasTrainCards(TrainCard...cards) {
        if(cards == null || cards.length == 0) { return false; }
        if(cards.length > trainCards.size()) { return false; } //impossible
        TrainCard.TRAIN_TYPE cardsType = cards[0].getType();
        for(TrainCard each : cards) {
            if(each.getType() != cardsType) { return false; } //cards are all same type
        }

        TrainCard[] adjustedCards = adjustForWilds(cards);
        int neededMatches = adjustedCards.length;
        int curMatches = 0;
        for(TrainCard each : trainCards) {
            if(each.getType() == cardsType) { curMatches++; }
            if(curMatches >= neededMatches) { return true; } //success
        }
        return false; //didn't have enough matches
    }

    private TrainCard[] adjustForWilds(TrainCard[] cards) {
        LinkedList<TrainCard> adjCards = new LinkedList<>(Arrays.asList(cards));

        for(TrainCard card : trainCards) {
            if(card.getType() == TrainCard.TRAIN_TYPE.WILD) { adjCards.removeLast(); }
            if(adjCards.isEmpty()) { break; } //limit
        }

        return adjCards.toArray(new TrainCard[adjCards.size()]);
    }

    public List<TrainCard> getTrainCards() {
        return Collections.unmodifiableList(trainCards);
    }

    public Integer getCardCount(){
        return trainCards.size() + destinationCards.size();
    }

    public List<DestinationCard> getTempDeck(){return tempDeck;}

    public boolean hasTempDeck(){
        return tempDeck.size() == 0;
    }

    public void setTempDeck(List<DestinationCard> deck){
        tempDeck = deck;
    }

    public void clearTempDeck(){
        tempDeck.clear();
    }

    public PlayerStats getStats() {
        PlayerStats stats = new PlayerStats();

        stats.setName(getUsername());
//        stats.setNumberOfCards(getCardCount());
        stats.setNumberOfCards(trainCards.size());
        stats.setNumberOfRoutes(destinationCards.size());
        stats.setPoints(getPoints());
        stats.setNumberOfPieces(trains);
        stats.setColor(getColorValue());

        return stats;
    }

    public Integer getColorValue(){
        if(color == null) { return 0; }
        switch(color) {
            case RED:
                return 0xffff0000;
            case YELLOW:
                return 0xffffff00;
            case GREEN:
                return 0xff00ff00;
            case BLUE:
                return 0xff0000ff;
            case BLACK:
                return 0xff000000;
            default:
                return null;
        }
    }

    public Result canClaim(TrainCard.TRAIN_TYPE type, Integer length) {
        //if(type == TrainCard.TRAIN_TYPE.GREY) { return new Result(false, null, "An alternative to grey trainType should have been selected"); }
        if(!hasTrainCards(getNeededCards(type,length))) {
            System.out.println("failed to claim route because player doesn't have the right cards");
            return new Result(false, null, username + " doesn't have the right cards");
        }
        else if(length > trains) {
            System.out.println("failed to claim route because player doesn't have enough trains");
            return new Result(false, null, username + " doesn't have enough trains");
        }
        else {
            return new Result(true, null, null);
        }
    }

    public void claimRoute(TrainCard.TRAIN_TYPE type, Integer length, Integer points) {
        if(!canClaim(type, length).isSuccess()) { return; }

        removeTrainCards(getNeededCards(type, length));
        this.points += points;
        trains -= length;
    }

    private TrainCard[] getNeededCards(TrainCard.TRAIN_TYPE type, Integer length) {
        TrainCard[] neededCards = new TrainCard[length];
        Arrays.fill(neededCards, new TrainCard(type));
        return neededCards;
    }

    public void addPoints(Integer points) { this.points += points; }

    public Map<String, Integer> getColorCardCounts(){

        Map<String, Integer> items = new HashMap<>();

        items.put("BLACK",0);
        items.put("BLUE",0);
        items.put("GREEN",0);
        items.put("ORANGE",0);
        items.put( "PINK",0);
        items.put( "RED",0);
        items.put( "WHITE",0);
        items.put( "YELLOW",0);
        items.put( "WILD",0);

        for (TrainCard card: trainCards){
            Integer count = items.get(card.getType().toString());
            count++;
            items.put(card.getType().toString(), count); ;
        }

        return items;
    }

    public TrainCard getNewestTrainCard() {
        return newestTrainCard;
    }

    public void sortHand() {
        trainCards.sort(TrainCard::compareTo);
    }
}
