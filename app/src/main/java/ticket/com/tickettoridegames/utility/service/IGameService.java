package ticket.com.tickettoridegames.utility.service;

import java.util.LinkedList;

import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.web.Result;

public interface IGameService {
    void initGame(String gameId);

    //for the deck
    void drawTrainCard(String playerId, String gameId);

    //for face up cards
    void pickupTrainCard(String playerId, String gameId, Integer index);

    void drawDestinationCard(String playerId, String gameId);

    void claimDestinationCard(String playerId, String gameId, LinkedList<DestinationCard> cards);

    void returnDestinationCard(String gameId, LinkedList<DestinationCard> cards);

    Result claimRoute(String gameId, String playerId, String route);

    void switchTurn(String gameId);
}
