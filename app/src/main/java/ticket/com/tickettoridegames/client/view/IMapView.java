package ticket.com.tickettoridegames.client.view;

import java.util.Map;
import java.util.Set;

import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.TrainCard;

public interface IMapView {

    void displayMessage(String message);

    void enableRoutes(Boolean enable);

    void enableTrainCardDeck(Boolean enable);

    void enableDestinationCardDeck(Boolean enable);

    void displayDestinationCards(Set<DestinationCard> destinationCards);

    void setFirstCall(boolean val);

    void displayColorOptions(Map<String, Integer> playerCardCount, String routeName);

    void claimRoute(String routeName, Integer colorValue);

    void disablePickRoutes();

    void endGame();
}
