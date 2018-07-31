package ticket.com.tickettoridegames.utility.model;

public class Turn {

    private boolean canDrawDestinations = true;
    private boolean canDrawTrains = true;
    private boolean canClaimRoute = true;
    private boolean canDrawBank = true;


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
