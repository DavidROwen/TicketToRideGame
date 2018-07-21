package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public interface IAssetsView {

    public void setHand(List<TrainCard> hand);

    public void setBank(List<TrainCard> trainBank);

    public TrainCard getBankChoice(TrainCard trainCard);

    public void setRoutes(Set<DestinationCard> destinationCards);

    public void displayMessage(String message);
}
