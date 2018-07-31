package ticket.com.tickettoridegames.client.presenter;

import java.util.LinkedList;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public interface IMapPresenter {

    void passOff();

    void drawTrainCard();

    void drawDestinationCards();

    void changeTurn();

    void claimRoute(String route);

    void setDestinationCards(LinkedList<DestinationCard> claimedCards, LinkedList<DestinationCard> discardedCards);

    void setColorChoice(TrainCard.TRAIN_TYPE color);
}
