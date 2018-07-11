package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.utility.model.Player;

public interface ILobbyPresenter {

    String startGame(String gameID);

    void addPlayer(Player player);

    void sendMessage(String playerID, String message);
}
