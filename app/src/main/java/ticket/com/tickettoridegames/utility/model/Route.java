package ticket.com.tickettoridegames.utility.model;

import android.graphics.Rect;

public class Route {

    private final String name;
    private boolean owned = false;
    private String ownerId = null;
    private final Integer length;
    private final TrainCard.TRAIN_TYPE type;
    private final City start;
    private final City end;
    private final Integer routeNum; //if there is only one then it will be null

//    public Route() {}

//    public Route(boolean owned, String ownerId, Integer length, City start, City end) {
//        this.owned = owned;
//        this.ownerId = ownerId;
//        this.length = length;
//        this.start = start;
//        this.end = end;
//    }

//    public Route(City start, City end, Integer length, TrainCard.TRAIN_TYPE type) {
//        this.start = start;
//        this.end = end;
//        this.length = length;
//        this.type = type;
//
//    }

    public Route(String name, City start, City end, Integer length, TrainCard.TRAIN_TYPE type, Integer routeNum) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.length = length;
        this.type = type;
        this.routeNum = routeNum;
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

    public City getStart() {
        return start;
    }

    public City getEnd() {
        return end;
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

    public String to_String(){
        String message = start + "->" +
                end + ": " + length.toString() + " points";
        return message;
    }

    public String getName() {
        return name;
    }

    public Boolean claim(String playerID) {
        if(!canClaim()) { return false; }

        owned = true;
        ownerId = playerID;
        return true;
    }

    private boolean canClaim() {
        return !isOwned();
    }
}
