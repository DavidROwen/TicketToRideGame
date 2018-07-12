package ticket.com.tickettoridegames.client.presenter;

public interface ILobbyPresenter {

    void startGame(String gameID);

    void sendMessage(String gameID, String playerID, String message);

//    void addPlayer(String gameID, String playerID);
}
