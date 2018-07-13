package ticket.com.tickettoridegames.client.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public interface IJoinView {

    Map<String, Game> getGames();               //are all games ever needed shouldnt it just be get chosen game

    void addGame(Game newGame);                      //unnecessary

    Set<String> getPlayers(String gameID);           //unnecessary

    void setPlayers(List<Player> players);           //unnecessary

    void setPlayerCount(String gameID);              //unnecessary




    void setGames(Map<String, Game> games);

    String getChosenGame(String gameID);

    public String getNewPlayerColor();

    String getNewGameName();

    Integer getNewPlayerCount();

    void changeView();

    void displayMessage(String message);

}
