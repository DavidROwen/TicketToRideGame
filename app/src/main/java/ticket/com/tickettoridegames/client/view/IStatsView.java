package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.PlayerAction;

public interface IStatsView {

    void displayChat(Chat message);

    String sendChat(String message);

    void setChat(List<Chat> chats);

    void setHistory(List<PlayerAction> gameHistory);

    void getPlayers(Set<String> players);

    void displayMessage(String message);

}
