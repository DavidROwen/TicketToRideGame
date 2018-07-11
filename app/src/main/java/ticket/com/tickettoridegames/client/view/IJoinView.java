package ticket.com.tickettoridegames.client.view;

import java.util.List;

import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public interface IJoinView {

    List<Game> getGames();

    void addGame(Game newGame);

    List<Player> getPlayers(String gameID);

    void setPlayers(List<Player> players);

    void setPlayerCount(String gameID);

    Integer getPlayerCount(String gameID);

    void displayMessage(String message);

}
