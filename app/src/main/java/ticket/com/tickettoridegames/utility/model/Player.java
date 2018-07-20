package ticket.com.tickettoridegames.utility.model;

import android.util.ArraySet;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Player {
    private String username;
    private String id;

    // Game Data
    private String color;
    private Integer trains;
    private Integer points;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
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

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public Set<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void addDestinationCards(DestinationCard...cards) {
        destinationCards.addAll(Arrays.asList(cards));
    }

    public void addTrainCard(TrainCard card) {
        trainCards.add(card);
    }

    public Boolean removeTrainCards(TrainCard...cards) {
        if(!hasTrainCards(cards)) { return false; }

        for(TrainCard card : cards) {
            trainCards.remove(card);
        }

        return true;
    }

    public Boolean hasTrainCards(TrainCard...cards) {
        //convert it to strings for comparison
        List<String> myTypes = new LinkedList<>();
        List<String> cardsTypes = new LinkedList<>();
        for(TrainCard card : trainCards) { myTypes.add(card.getType().toString()); }
        for(TrainCard card : cards) { cardsTypes.add(card.getType().toString()); }

//        List<TrainCard> setCards = new LinkedList<>(Arrays.asList(cards)); //couldn't figure out
        return myTypes.containsAll(cardsTypes);
    }

}
