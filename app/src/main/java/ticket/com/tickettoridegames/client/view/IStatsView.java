package ticket.com.tickettoridegames.client.view;

import java.util.List;

import ticket.com.utility.model.Chat;
import ticket.com.utility.model.PlayerAction;
import ticket.com.utility.model.PlayerStats;

public interface IStatsView {

    void displayChat(Chat message);

    void setChat(List<Chat> chats);

    void setHistory(List<PlayerAction> gameHistory);

    void displayHistory(PlayerAction pa);

    void setPlayerStats(List<PlayerStats> playerStats);

    void setLongestTrainAward(String player);

    void displayMessage(String message);

}
