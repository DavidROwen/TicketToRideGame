package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Pair;
import ticket.com.utility.model.Route;
import ticket.com.utility.model.TrainCard;

public interface IMapView {

    void displayMessage(String message);

    void enableTurn();

    void disableTurn();

    void displayDestinationCards(Set<DestinationCard> destinationCards);

    void setFirstCall(boolean val);

    void displayColorOptions(Map<String, Integer> playerCardCount);

    void claimRoute(Route route, Integer color);

    void disablePickRoutes();

    void setClaimedRoutes(List<Pair<Route, Integer>> routes);
}
