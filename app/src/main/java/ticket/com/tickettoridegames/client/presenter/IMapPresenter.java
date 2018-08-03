package ticket.com.tickettoridegames.client.presenter;

import java.util.LinkedList;

import ticket.com.tickettoridegames.client.view.IMapView;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.TrainCard;

public interface IMapPresenter {
    void drawTrainCard();

    void drawDestinationCards();

    void changeTurn();

    void claimRoute(String route);

    void setDestinationCards(LinkedList<DestinationCard> claimedCards, LinkedList<DestinationCard> discardedCards, boolean firstCall);

    void setColorChoice(TrainCard.TRAIN_TYPE color);

    IMapView getMapView();

    void setClaimedRoutes();

    void claimGreyRoute(String routeName, TrainCard.TRAIN_TYPE typeChoice);
}
