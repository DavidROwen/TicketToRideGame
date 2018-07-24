package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.Chat;

public interface ILobbyView {

    void resetPlayers(Set<String> players);

    void displayChat(Chat message);

    void setChat(List<Chat> chats);

    void addPlayerName(String player);

    void changeView();

    void displayMessage(String toast);
}
