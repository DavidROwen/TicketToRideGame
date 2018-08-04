package ticket.com.utility.model;

public class TrainCard implements Comparable<TrainCard>{
    public enum TRAIN_TYPE {PINK, WHITE, BLUE, YELLOW, ORANGE, BLACK, RED, GREEN, WILD}

    private final TRAIN_TYPE type;

    public TrainCard(TRAIN_TYPE type) {
        this.type = type;
    }

    public TRAIN_TYPE getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        return ((TrainCard) obj).getType().equals(this.type);
    }

    @Override
    public int hashCode() {
        return type.ordinal(); //returns position in enum
    }

    @Override
    public int compareTo(TrainCard card){
        return type.compareTo(card.getType());
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
