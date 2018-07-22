package ticket.com.tickettoridegames.utility.service;

import java.util.List;

import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Route;

public interface IGameService {
    void initGame(String gameId);

    //for the deck
    void drawTrainCard(String playerId, String gameId);

    //for face up cards
    void pickupTrainCard(String playerId, String gameId, Integer index);

    void drawDestinationCard(String playerId, String gameId);

    void claimDestinationCard(String playerId, String gameId, List<DestinationCard> cards);

    void returnDestinationCard(String gameId, List<DestinationCard> cards);

    void claimRoute(String gameId, String playerId, Route route);
}
