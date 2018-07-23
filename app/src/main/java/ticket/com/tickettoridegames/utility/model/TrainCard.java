package ticket.com.tickettoridegames.utility.model;

public class TrainCard {
    public enum TRAIN_TYPE {RED, BLUE, GREEN, PINK, ORANGE, WHITE, BLACK, YELLOW, WILD}
    public static final Integer NUM_TYPES = 9;

    private TRAIN_TYPE type;
    private Integer variable = 1;

    public TrainCard(TRAIN_TYPE type) {
        this.type = type;
    }

    public TRAIN_TYPE getType() {
        return type;
    }

    public void setType(TRAIN_TYPE type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return ((TrainCard)obj).type == this.type;
    }

    @Override
    public int hashCode() {
        return type.ordinal(); //returns position in enum
    }
}
