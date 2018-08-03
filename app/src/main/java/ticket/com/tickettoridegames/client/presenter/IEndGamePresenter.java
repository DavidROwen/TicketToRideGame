package ticket.com.tickettoridegames.client.presenter;

import ticket.com.utility.model.Game;

public interface IEndGamePresenter {

    String calculateWinner();

    void updateScore(Game game);
}
