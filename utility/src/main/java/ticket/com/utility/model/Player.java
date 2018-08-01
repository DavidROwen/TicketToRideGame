package ticket.com.utility.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ticket.com.utility.web.Result;

import static ticket.com.utility.model.Game.LENGTH_TO_POINTS;

public class Player {
    public enum COLOR {RED, YELLOW, GREEN, BLUE, BLACK}

    private String username;
    private String id;

    // Game Data
    private COLOR color;
    private Integer trains = 45;
    private Integer points = 0;

    private List<DestinationCard> tempDeck = new LinkedList<>();
    private List<TrainCard> trainCards = new LinkedList<>();
    private Set<DestinationCard> destinationCards = new HashSet<>();

    public Player(String username, String id) {
        this.username = username;
        this.id = id;
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
    }

    public Boolean removeTrainCards(TrainCard...cards) {
        if(!hasTrainCards(cards)) { return false; }

        for(TrainCard card : cards) {
            if(!trainCards.remove(card)) {
                trainCards.remove(new TrainCard(TrainCard.TRAIN_TYPE.WILD));
            }
        }

        return true;
    }

    //assuming all the same kind in parameter
    public Boolean hasTrainCards(TrainCard...cards) {
        TrainCard[] adjustedCards = adjustForWilds(cards);

        //convert it to strings for comparison
        List<String> myTypes = new LinkedList<>();
        List<String> cardsTypes = new LinkedList<>();
        for(TrainCard card : trainCards) { myTypes.add(card.getType().toString()); }
        for(TrainCard card : adjustedCards) { cardsTypes.add(card.getType().toString()); }

//        List<TrainCard> setCards = new LinkedList<>(Arrays.asList(adjustedCards)); //couldn't figure out
        return myTypes.containsAll(cardsTypes);
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
        return trainCards;
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
        if (color != null) {
            switch (color) {
                case RED:
                    return -65535;
                case BLUE:
                    return -16776961;
                case YELLOW:
                    return -256;
                case GREEN:
                    return -16711936;
                case BLACK:
                    return -16777216;
                default:
                    return 0;
            }
        }
        else {
            return 0;
        }
    }

    public Result canClaim(TrainCard.TRAIN_TYPE type, Integer length) {
        //if(type == TrainCard.TRAIN_TYPE.GREY) { return new Result(false, null, "An alternative to grey trainType should have been selected"); }
        if(!hasTrainCards(getNeededCards(type,length))) {
            return new Result(false, null, "Player doesn't have the right cards");
        }
        else if(length > trains) {
            return new Result(false, null, "Player doesn't have enough trains");
        }
        else {
            return new Result(true, null, null);
        }
    }

    public void claimRoute(TrainCard.TRAIN_TYPE type, Integer length) {
        if(!canClaim(type, length).isSuccess()) { return; }

        removeTrainCards(getNeededCards(type, length));
        points += LENGTH_TO_POINTS[length-1];
        trains -= length;
    }

    private TrainCard[] getNeededCards(TrainCard.TRAIN_TYPE type, Integer length) {
        TrainCard[] neededCards = new TrainCard[length];
        Arrays.fill(neededCards, new TrainCard(type));
        return neededCards;
    }

    public void subtractPoints(Integer points) { this.points -= points; }
}
