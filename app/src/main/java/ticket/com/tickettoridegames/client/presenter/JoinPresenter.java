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

    public JoinPresenter(IJoinView view){
        joinView = view;
        joinService = new JoinService();
        clientModel = ClientModel.get_instance();
    }

    @Override
    public void createGame(String gameName, int numberOfPlayers){
        if (gameName.equals("")){
            joinView.displayMessage("Invalid game name");
        }
        else if (numberOfPlayers < 2 || numberOfPlayers > 5){
            joinView.displayMessage("Invalid number of players");
        }
        else {
            String userId = clientModel.getUserId();
            Result result = joinService.createGame(userId, gameName, numberOfPlayers);
            if (result.isSuccess()){
                // View should update on observer
            }
            else {
                // Request failed
                joinView.displayMessage("Error creating game. " + result.getErrorMessage());
            }
        }
    }

    @Override
    public void joinGame(String gameId) {
        if (gameId.equals("")) {
            joinView.displayMessage("Invalid game id.");
        } else {
            String userId = clientModel.getUserId();
            Result result = joinService.joinGame(userId, gameId);
            if (result.isSuccess()){
                // View should update based on observer
            }
            else {
                // Request failed
                joinView.displayMessage("Error joining game. " + result.getErrorMessage());
            }
        }
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        // update view here
    }

}
