package ticket.com.tickettoridegames.client.model;

import java.util.List;
import java.util.Observable;

import ticket.com.tickettoridegames.utility.model.Game;
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

    static private User currentUser;
    static private List<Game> gameList;

    private ClientModel() { }

    public void setUser(User user){
        currentUser = user;
        // notify the login presenter
    }

    public String getUserId(){
        return currentUser.getId();
    }

    public void setGames(List<Game> games){
        gameList = games;
        // notify lobby presenter
    }

    public List<Game> getGames(){
        return gameList;
    }

    public boolean addGameToList(Game game){
        if (gameList.contains(game)){
            // Game is already in the list
            return false;
        }
        gameList.add(game);
        // notify lobby presenter
        return true;
    }
}
