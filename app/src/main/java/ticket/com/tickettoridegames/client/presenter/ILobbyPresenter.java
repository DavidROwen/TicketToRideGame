package ticket.com.tickettoridegames.client.presenter;

public interface ILobbyPresenter {

    void startGame();

    void leaveGame();

    void sendMessage(String message);
}
