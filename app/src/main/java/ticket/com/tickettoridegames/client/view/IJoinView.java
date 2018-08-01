package ticket.com.tickettoridegames.client.view;

import java.util.Map;

import ticket.com.utility.model.Game;

public interface IJoinView {

    void setGames(Map<String, Game> games);

    String getNewPlayerColor();

    String getNewGameName();

    Integer getNewPlayerCount();

    void changeView(Boolean isStarted);

    void displayMessage(String message);

}
