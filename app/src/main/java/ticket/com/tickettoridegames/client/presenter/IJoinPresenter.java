package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.web.Result;

public interface IJoinPresenter {

    void createGame(String gameName, int numberOfPlayer);

    void joinGame(String gameID);

}
