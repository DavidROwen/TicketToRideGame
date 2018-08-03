package ticket.com.tickettoridegames.client.view;

import java.util.Map;

import ticket.com.utility.model.Game;

public interface IJoinView {

    String getNewPlayerColor();

    String getNewGameName();

    Integer getNewPlayerCount();

    void changeView(Boolean isStarted);

    void displayMessage(String message);

    void addGame(Game game);
}
