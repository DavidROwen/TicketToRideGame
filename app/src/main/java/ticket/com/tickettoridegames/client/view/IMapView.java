package ticket.com.tickettoridegames.client.view;

import java.util.Set;

import ticket.com.tickettoridegames.utility.model.DestinationCard;

public interface IMapView {

    void displayMessage(String message);

    void enableTurn();

    void disableTurn();

    void displayDestinationCards(Set<DestinationCard> destinationCards);

    void getDestinationCardChoices();
}
