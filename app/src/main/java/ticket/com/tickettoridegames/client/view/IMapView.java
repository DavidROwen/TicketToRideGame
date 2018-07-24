package ticket.com.tickettoridegames.client.view;

import java.util.Set;

import ticket.com.tickettoridegames.utility.model.DestinationCard;

public interface IMapView {

    void displayDestinationCards(Set<DestinationCard> destinationCards);

    void getDestinationCardChoices();

    void displayMessage(String message);

}
