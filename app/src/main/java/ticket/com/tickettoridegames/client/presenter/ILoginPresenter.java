package ticket.com.tickettoridegames.client.presenter;

public interface ILoginPresenter {

    String login(String username, String password);

    String register(String username, String password, String passwordConfirm);

}
