package ticket.com.tickettoridegames.utility.service;

import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;

public interface IGameService {
    void addChat(Chat chat, String gameId);

    void returnDestinationCard(String gameId, DestinationCard card);

    void initGame(String gameId);

    void drawTrainCard(String playerId, String gameId);

    void drawDestinationCard(String playerId, String gameId);
}
