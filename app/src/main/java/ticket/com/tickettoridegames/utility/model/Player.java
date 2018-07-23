package ticket.com.tickettoridegames.utility.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.server.CommandsManager;
import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.web.Command;

public class Player {
    public enum COLOR {RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE}

    private String username;
    private String id;

    // Game Data
    private COLOR color;
    private Integer trains;
    private Integer points;

    private List<DestinationCard> tempDeck = new LinkedList<>();
    private List<TrainCard> trainCards = new LinkedList<>();
    private Set<DestinationCard> destinationCards = new HashSet<>();
    private Set<Route> claimedRoutes = new HashSet<>();

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
        destinationCards.addAll(Arrays.asList(card));
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

    public Integer getRouteCount(){
        return claimedRoutes.size();
    }

    public List getTempDeck(){return tempDeck;}

    public void setTempDeck(List<DestinationCard> deck){
        tempDeck = deck;
    }

    public PlayerStats getStats() {
        PlayerStats stats = new PlayerStats();

        stats.setName(getUsername());
//        stats.setNumberOfCards(getCardCount());
        stats.setNumberOfCards(trainCards.size());
        stats.setNumberOfRoutes(getRouteCount());
        stats.setPoints(getPoints());

        return stats;
    }
}
