package ticket.com.tickettoridegames.client.view;

public interface ILoginView {

    String getLoginUserName();

    String getRegisterUserName();

    String getLoginPassword();

    String getRegisterPassword();

    String getRegisterConfirmation();

    void changeView();

    void displayMessage(String message);
}
