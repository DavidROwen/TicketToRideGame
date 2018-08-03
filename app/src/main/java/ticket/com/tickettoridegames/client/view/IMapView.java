package ticket.com.tickettoridegames.client.view;

import java.util.Map;
import java.util.Set;

import ticket.com.utility.model.DestinationCard;

public interface IMapView {

    void displayMessage(String message);

    void enableTurn();

    void disableTurn();

    void displayDestinationCards(Set<DestinationCard> destinationCards);

    void setFirstCall(boolean val);

    void displayColorOptions(Map<String, Integer> playerCardCount);

    void claimRoute(String routeName, Integer colorValue);

    void disablePickRoutes();
}
