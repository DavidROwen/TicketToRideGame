package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public interface IJoinPresenter {

    void createGame(Game newGame);

    void joinGame(Player player);

}
