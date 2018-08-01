package ticket.com.tickettoridegames.client.presenter;

import java.util.List;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.view.IEndGameView;
import ticket.com.utility.model.PlayerStats;

public class EndGamePresenter {
    private ClientModel clientModel;
    private IEndGameView endGameView;
    private String winner;

    public EndGamePresenter(IEndGameView view){
        endGameView = view;
        clientModel = ClientModel.get_instance();

        endGameView.setPlayerStats(ClientModel.get_instance().getMyActiveGame().getPlayerStats());
        endGameView.setWinner(calculateWinner());
    }

    public String calculateWinner(){
        Integer score = 0;
        List<PlayerStats> stats = ClientModel.get_instance().getMyActiveGame().getPlayerStats();
        for (PlayerStats player:stats) {
            if(player.getPoints() > score){
                winner = player.getName();
            }
        }
        return winner;
    }
}
