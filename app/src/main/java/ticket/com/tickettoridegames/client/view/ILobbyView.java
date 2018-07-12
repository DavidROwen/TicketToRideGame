package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.Player;

public interface ILobbyView {

    void setPlayers(Set<String> players);

    void displayChat(String message);

    void sendChat(String message);

    void setChat(List<Chat> chats);

    // Not necessary for phase 1
    // void changeView();

    void displayMessage(String toast);
}
