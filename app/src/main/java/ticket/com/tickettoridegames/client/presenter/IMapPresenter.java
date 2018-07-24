package ticket.com.tickettoridegames.client.presenter;

import java.util.LinkedList;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Route;

public interface IMapPresenter {

    void passOff();

    void drawTrainCard();

    void drawDestinationCards();

    void changeTurn();

    void claimRoute(Route route);

    void claimDestinationCard(LinkedList<DestinationCard> destinationCards);

    void returnDestinationCard(LinkedList<DestinationCard> destinationCards);
}
