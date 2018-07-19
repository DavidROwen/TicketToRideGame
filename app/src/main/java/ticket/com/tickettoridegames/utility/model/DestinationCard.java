package ticket.com.tickettoridegames.utility.model;

public class DestinationCard {
    private City location;
    private Integer value;

    public DestinationCard(City location, Integer value) {
        this.location = location;
        this.value = value;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
