package ticket.com.tickettoridegames.utility.model;

public class DestinationCard {
    private City startLocation;
    private City endLocation;
    private Integer value;

    public DestinationCard(City startLocation, Integer value) {
        this.startLocation = startLocation;
        this.value = value;
    }

    public City getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(City startLocation) {
        this.startLocation = startLocation;
    }

    public City getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(City endLocation) {
        this.endLocation = endLocation;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
