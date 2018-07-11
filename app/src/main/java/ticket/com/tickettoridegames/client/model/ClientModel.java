package ticket.com.tickettoridegames.client.model;

import java.util.Observable;

import ticket.com.tickettoridegames.utility.model.User;

public class ClientModel extends Observable {

    // Singleton Class
    static private ClientModel _instance;

    static public ClientModel get_instance() {
        if (_instance == null) {
            _instance = new ClientModel();
        }
        return _instance;
    }

    static private User current_user;

    private ClientModel() { }

    public void loginUser(User user){
        current_user = user;
        // notify the observers here
    }

    public String getUserId(){
        return current_user.getId();
    }

}
