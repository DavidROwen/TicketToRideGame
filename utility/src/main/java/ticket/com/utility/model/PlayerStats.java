package ticket.com.utility.model;

public class PlayerStats {

    private String name;
    private Integer points;
    private Integer numberOfCards;
    private Integer numberOfRoutes;
    private Integer numberOfPieces;
    private Integer color;

    public PlayerStats(String name, Integer points, Integer numberOfCards, Integer numberOfRoutes, Integer pieces, Integer color) {
        this.name = name;
        this.points = points;
        this.numberOfCards = numberOfCards;
        this.numberOfRoutes = numberOfRoutes;
        this.numberOfPieces = pieces;
        this.color = color;
    }

    public PlayerStats(){}

    public String getName() {
        return name;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
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
