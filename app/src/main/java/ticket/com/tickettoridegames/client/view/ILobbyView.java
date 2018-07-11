package ticket.com.tickettoridegames.client.view;

import java.util.List;

import ticket.com.tickettoridegames.utility.model.Player;

public interface ILobbyView {

    void setPlayers(List<Player> players);

    void displayChat(String message);

    void sendChat(String message);

    void displayMessage(String toast);
}
