package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.view.IJoinView;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.web.Result;

public class JoinPresenter implements IJoinPresenter, Observer {

    private JoinService joinService;
    private ClientModel clientModel;
    private IJoinView joinView;

    public JoinPresenter(){
        joinService = new JoinService();
        clientModel = ClientModel.get_instance();
    }

    @Override
    public Result createGame(String gameName, int numberOfPlayers){
        if (gameName.equals("")){
            return new Result(false,"","Invalid game name");
        }
        else if (numberOfPlayers < 2 || numberOfPlayers > 5){
            return new Result(false,"","Invalid number of players");
        }
        else {
            String userId = clientModel.getUserId();
            Result result = joinService.createGame(userId, gameName, numberOfPlayers);
            if (result.isSuccess()){
                return result;
            }
            else {
                // Request failed
                return result;
            }
        }
    }

    @Override
    public Result joinGame(String gameId) {
        if (gameId.equals("")) {
            return new Result(false, "", "Invalid game id.");
        } else {
            String userId = clientModel.getUserId();
            Result result = joinService.joinGame(userId, gameId);
            if (result.isSuccess()){
                return result;
            }
            else {
                // Request failed
                return result;
            }
        }
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        // update view here
    }

}
