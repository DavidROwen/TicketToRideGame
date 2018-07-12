package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public interface IJoinView {

    Map<String, Game> getGames();

    void setGames(Map<String, Game> games);

    void addGame(Game newGame);

    List<Player> getPlayers(String gameID);

    void setPlayers(List<Player> players);

    void setPlayerCount(String gameID);

    public String getNewPlayerColor();

    String getNewGameName();

    Integer getNewPlayerCount();

    void changeView();

    void displayMessage(String message);

}
