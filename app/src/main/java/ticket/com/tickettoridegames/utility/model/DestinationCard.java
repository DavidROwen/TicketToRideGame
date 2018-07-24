package ticket.com.tickettoridegames.utility.model;

public class DestinationCard {
    private City location;
    private City location2;
    private Integer value;

    public DestinationCard(City location, City location2, Integer value) {
        this.location = location;
        this.location2 = location2;
        this.value = value;
    }

    public City getLocation() {
        return location;
    }

    public City getLocation2() {
        return location2;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
