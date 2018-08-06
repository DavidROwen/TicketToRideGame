package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Set;

import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.TrainCard;


public interface IAssetsView {

    void setHand(List<TrainCard> hand);

    void setBank(List<TrainCard> trainBank);

    TrainCard getBankChoice(TrainCard trainCard);

    void setRoutes(Set<DestinationCard> destinationCards);

    void addRoute(Set<DestinationCard> destinationCards);

    void displayMessage(String message);

    void setTrainDeckCount(Integer size);

    void setRouteDeckCount(Integer size);

    void endGame();
}
