package ticket.com.tickettoridegames.client.presenter;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.view.IEndGameView;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.PlayerStats;

public class EndGamePresenter implements IEndGamePresenter{
    private ClientModel clientModel;
    private IEndGameView endGameView;
    private String winner;

    public EndGamePresenter(IEndGameView view){
        endGameView = view;
        clientModel = ClientModel.get_instance();

        endGameView.setPlayerStats(ClientModel.get_instance().getMyActiveGame().getPlayerStats());
        endGameView.setWinner(calculateWinner());
    }

    @Override
    public void updateScore(Game game){
        Set<String> names = game.getPlayerNames();
        Map<String, Player> players = game.getPlayers();

        for(String name:names){
            Player player = players.get(name);
            game.addDestinationCardPoints(player);
        }
    }

    @Override
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
