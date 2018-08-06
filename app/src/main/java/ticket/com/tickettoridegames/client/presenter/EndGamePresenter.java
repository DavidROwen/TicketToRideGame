package ticket.com.tickettoridegames.client.presenter;

import java.util.List;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.view.IEndGameView;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.PlayerStats;

public class EndGamePresenter implements IEndGamePresenter{

    public EndGamePresenter(IEndGameView view){
        updateScore(ClientModel.get_instance().getMyActiveGame());

        view.setPlayerStats(ClientModel.get_instance().getMyActiveGame().getPlayerStats());
        view.setWinner(calculateWinner());
    }

    @Override
    public void updateScore(Game game){
        for (Player player: game.getPlayers().values()){
            game.addDestinationCardPoints(player);
        }
    }

    @Override
    public String calculateWinner(){
        Integer hiScore = 0;
        List<PlayerStats> stats = ClientModel.get_instance().getMyActiveGame().getPlayerStats();
        String winner = "";
        for (PlayerStats player:stats) {
            if(player.getPoints() > hiScore){
                winner = player.getName();
            }
            if (player.getPoints().equals(hiScore)){
                winner = "Tie between "+winner+" and "+player.getName();
            }
        }
        return winner;
    }
}
