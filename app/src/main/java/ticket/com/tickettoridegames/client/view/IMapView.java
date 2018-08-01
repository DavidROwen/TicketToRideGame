package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Pair;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public interface IMapView {

    void displayMessage(String message);

    void enableTurn();

    void disableTurn();

    void displayDestinationCards(Set<DestinationCard> destinationCards);

    void setFirstCall(boolean val);

    void displayColorOptions();

    void placeTrains(Route route, TrainCard.TRAIN_TYPE color);

    void claimRoute(Route route, Integer color);

    void disablePickRoutes();

    void setClaimedRoutes(List<Pair<Route, Integer>> routes);
}
