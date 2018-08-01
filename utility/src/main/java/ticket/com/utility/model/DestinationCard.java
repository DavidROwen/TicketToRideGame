package ticket.com.utility.model;

public class DestinationCard {
    private City location;
    private City location2;
    private Integer value;
    private boolean completed = false;

    public DestinationCard(City location, City location2, Integer value) {
        this.location = location;
        this.location2 = location2;
        this.value = value;
    }

    public boolean isCompleted(){return completed;}

    public void setCompleted(){
        completed = true;
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

    public String to_String(){
        String message = location.toString() + "->" +
                location2.toString() + ": " + value.toString();
        return message;
    }
}
