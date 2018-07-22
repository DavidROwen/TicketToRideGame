package ticket.com.tickettoridegames.utility.model;

public class Route {

    private boolean owned;
    private String ownerId;
    private Integer length;
    private TrainCard.TRAIN_TYPE type;
    private City start;
    private City end;

    public Route() {}

    public Route(boolean owned, String ownerId, Integer length, City start, City end) {
        this.owned = owned;
        this.ownerId = ownerId;
        this.length = length;
        this.start = start;
        this.end = end;
    }

    public Route(City start, City end, Integer length, TrainCard.TRAIN_TYPE type) {
        this.start = start;
        this.end = end;
        this.length = length;
        this.type = type;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public City getStart() {
        return start;
    }

    public void setStart(City start) {
        this.start = start;
    }

    public City getEnd() {
        return end;
    }

    public void setEnd(City end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        //start and end are ambiguous
        Route other = (Route)obj;
        return (this.start == other.end || this.start == other.start)
                && (this.end == other.start || this.end == other.end
        );
    }

    public TrainCard.TRAIN_TYPE getType() {
        return type;
    }
}
