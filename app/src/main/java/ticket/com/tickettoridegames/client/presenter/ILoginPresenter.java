package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.utility.web.Result;

public interface ILoginPresenter {

    void login(String username, String password);

    void register(String username, String password, String passwordConfirm);

}
