package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public interface IAssetsView {

    void setHand(List<TrainCard> hand);

    void setBank(List<TrainCard> trainBank);

    TrainCard getBankChoice(TrainCard trainCard);

    void setRoutes(Set<DestinationCard> destinationCards);

    void addRoute(Set<DestinationCard> destinationCards);

    void displayMessage(String message);

    void pickupCard(Integer index);

    void setCardsInDeck(Integer size);
}
