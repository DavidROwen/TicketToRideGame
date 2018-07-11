package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.utility.model.Player;

public class LobbyPresenter implements ILobbyPresenter {

    private LobbyService lobbyService;

    public LobbyPresenter(){
        lobbyService = new LobbyService();
    }

    @Override
    public String startGame(String gameID){
        return "PLACEHOLDER";
    }

    @Override
    public void addPlayer(Player player){}

    @Override
    public void sendMessage(String playerID, String message){}
}
