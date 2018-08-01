package ticket.com.tickettoridegames.client.view;

import java.util.List;

import ticket.com.utility.model.PlayerStats;

public interface IEndGameView {

    void setPlayerStats(List<PlayerStats> playerStats);

    //void setLongestTrainAward(String player);

    void setWinner(String player);
}
