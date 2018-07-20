package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.Player;

public interface ILobbyView {

    void resetPlayers(Set<String> players);

    void displayChat(Chat message);

    String sendChat(String message);

    void setChat(List<Chat> chats);

    void addPlayerName(String player);

    // Not necessary for phase 1
    // void changeView();

    void displayMessage(String toast);
}
