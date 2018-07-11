package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.web.Result;

public interface IJoinPresenter {

    Result createGame(String gameName, int numberOfPlayer);

    Result joinGame(String gameID);

}
