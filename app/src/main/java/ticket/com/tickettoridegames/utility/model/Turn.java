package ticket.com.tickettoridegames.utility.model;

public class Turn {

    private boolean canDrawDestinations = true;
    private boolean canDrawTrains = true;
    private boolean canClaimRoute = true;
    private boolean canDrawBank = true;

    /*
    The player who is the most experienced traveler goes first. Play then proceeds clockwise around the table, each player taking one turn at a time until
    the game ends. On his turn, a player must perform one (and only one) of the following three actions:

    Draw Train Car Cards – The player may draw 2 Train Car cards. He may take any one of the face-up cards or he may draw the top card from the
    deck (this is a blind draw). If he draws a face up card, he immediately turns a replacement card face-up from the deck. He then draws his second
    card, either from the face up cards or from the top of the deck. (See Train Car Cards for special rules for Locomotive cards).
    Claim a Route – The player may claim a route on the board by playing a set of Train Car cards that match the color and length of the route and
    then placing one of his colored trains on each space of this route. He then records his score by moving his Scoring Marker the appropriate number
    of spaces (see Route Scoring Table) along the Scoring Track on the board.
    Draw Destination Tickets – The player draws 3 Destination Tickets from the top of the deck. He must keep at least one of them, but he may keep
    two or all three if he chooses. Any returned cards are placed on the bottom of the deck.
    */
    public Turn() {
    }

    public boolean isCanDrawDestinations() {
        return canDrawDestinations;
    }

    public void setCanDrawDestinations(boolean canDrawDestinations) {
        this.canDrawDestinations = canDrawDestinations;
    }

    public boolean isCanDrawTrains() {
        return canDrawTrains;
    }

    public void setCanDrawTrains(boolean canDrawTrains) {
        this.canDrawTrains = canDrawTrains;
    }

    public boolean isCanClaimRoute() {
        return canClaimRoute;
    }

    public void setCanClaimRoute(boolean canClaimRoute) {
        this.canClaimRoute = canClaimRoute;
    }

    public boolean isCanDrawBank() {
        return canDrawBank;
    }

    public void setCanDrawBank(boolean canDrawBank) {
        this.canDrawBank = canDrawBank;
    }
}
