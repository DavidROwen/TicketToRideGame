package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.utility.web.Result;

public interface ILobbyPresenter {

    Result startGame(String gameID);

//    Result addPlayer(String gameID, String playerID);

    Result sendMessage(String gameID, String playerID, String message);
}
