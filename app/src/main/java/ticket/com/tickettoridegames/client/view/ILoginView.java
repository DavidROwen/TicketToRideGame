package ticket.com.tickettoridegames.client.view;

public interface ILoginView {

    String getUserName();

    // we can implement this in part 2 if we want to.
//    void setUserName(String username);

    String getPassword();

    void displayMessage(String message);

}
