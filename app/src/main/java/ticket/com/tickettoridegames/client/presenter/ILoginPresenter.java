package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.utility.web.Result;

public interface ILoginPresenter {

    Result login(String username, String password);

    Result register(String username, String password, String passwordConfirm);

}
