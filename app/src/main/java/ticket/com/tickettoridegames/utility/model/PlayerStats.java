package ticket.com.tickettoridegames.utility.model;

public class PlayerStats {

    private String name;
    private Integer points;
    private Integer numberOfCards;
    private Integer numberOfRoutes;


    public PlayerStats(String name, Integer points, Integer numberOfCards, Integer numberOfRoutes) {
        this.name = name;
        this.points = points;
        this.numberOfCards = numberOfCards;
        this.numberOfRoutes = numberOfRoutes;
    }

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
}
