package ticket.com.tickettoridegames.client.presenter;

public interface ILoginPresenter {

    void login(String username, String password);

    void register(String username, String password, String passwordConfirm);

    void setUrl(String url);
}
