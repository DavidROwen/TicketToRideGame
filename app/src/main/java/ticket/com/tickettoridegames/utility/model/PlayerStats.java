package ticket.com.tickettoridegames.utility.model;

public class PlayerStats {

    private String name;
    private Integer points;
    private Integer numberOfCards;
    private Integer numberOfRoutes;
    private Integer numberOfPieces;


    public PlayerStats(String name, Integer points, Integer numberOfCards, Integer numberOfRoutes, Integer pieces) {
        this.name = name;
        this.points = points;
        this.numberOfCards = numberOfCards;
        this.numberOfRoutes = numberOfRoutes;
        this.numberOfPieces = pieces;
    }

    public PlayerStats(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(Integer numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public Integer getNumberOfRoutes() {
        return numberOfRoutes;
    }

    public void setNumberOfRoutes(Integer numberOfRoutes) {
        this.numberOfRoutes = numberOfRoutes;
    }

    public Integer getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(Integer numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }
}
