package ticket.com.tickettoridegames.utility.model;

public class Route {

    private boolean owned;
    private String ownerId;
    private Integer length;
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
}
