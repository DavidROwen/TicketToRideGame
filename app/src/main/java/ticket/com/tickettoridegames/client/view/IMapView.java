package ticket.com.tickettoridegames.client.view;

import java.util.Set;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public interface IMapView {

    void displayMessage(String message);

    void enableTurn();

    void disableTurn();

    void displayDestinationCards(Set<DestinationCard> destinationCards);

    void notifyDestinationButtonPress();

    void notifyPassOffButtonPress();

    void notifyDrawTrainButtonPress();

    Set<DestinationCard> sendDestinationCardChoices();

    void drawTrainCard(TrainCard trainCard);

    Route placeTrains();
}
