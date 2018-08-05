package ticket.com.tickettoridegames.client.presenter;

public interface IJoinPresenter {

    void createGame(String gameName, int numberOfPlayer, String color);

    void joinGame(String gameID);

    void updateView();
}
