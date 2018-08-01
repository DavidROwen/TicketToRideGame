package ticket.com.utility.model;

public class Route {
    public final String NAME;
    private boolean owned = false;
    private String ownerId = null;
    public final Integer LENGTH;
    public final TrainCard.TRAIN_TYPE TYPE;
    public final City START;
    public final City END;

    public Route(String name, City start, City end, Integer length, TrainCard.TRAIN_TYPE type) {
        this.NAME = name;
        this.START = start;
        this.END = end;
        this.LENGTH = length;
        this.TYPE = type;
    }

    @Override
    public boolean equals(Object obj) {
        Route other = (Route)obj;
        return (this.NAME.equals(other.NAME));
    }

    public String to_String(){
        String message = START + "->" +
                END + ": " + LENGTH.toString() + " points";
        return message;
    }

    public Boolean claim(String playerID) {
        if(!canClaim()) { return false; }

        owned = true;
        ownerId = playerID;
        return true;
    }

    public Boolean canClaim() {
        return !owned;
    }

    public Boolean isOwned() {
        return owned;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getStartCity(){return START.getName();}

    public String getEndCity(){return END.getName();}
}