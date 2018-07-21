package ticket.com.tickettoridegames.client.view;

import java.util.Set;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public interface IAssetsView {

    public void setHand(Set<TrainCard> hand);

    public void setBank(Set<TrainCard> trainBank);

    public void  getBankChoice(TrainCard trainCard);

    public void setRoutes(Set<DestinationCard> destinationCards);

    public void displayMessage(String message);
}
