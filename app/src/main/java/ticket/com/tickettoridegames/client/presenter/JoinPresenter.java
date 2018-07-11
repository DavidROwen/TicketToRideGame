package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public class JoinPresenter implements IJoinPresenter {

    private JoinService joinService;

    public JoinPresenter(){
        joinService = new JoinService();
    }

    @Override
    public void createGame(Game newGame){}

    @Override
    public void joinGame(Player player) {}

}
