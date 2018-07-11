package ticket.com.tickettoridegames.client.model;

import java.util.Observable;

public class ClientModel extends Observable {

    // Singleton Class
    private ClientModel _instance;

    public ClientModel get_instance() {
        if (_instance == null) {
            _instance = new ClientModel();
        }
        return _instance;
    }

    private ClientModel() { }


}
