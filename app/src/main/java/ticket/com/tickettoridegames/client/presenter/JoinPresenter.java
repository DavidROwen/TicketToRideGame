package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.view.IJoinView;
import ticket.com.utility.TYPE;
import ticket.com.utility.web.Result;

public class JoinPresenter implements IJoinPresenter, Observer {

    private JoinService joinService;
    private ClientModel clientModel;
    private IJoinView joinView;

    public JoinPresenter(IJoinView view){
        joinView = view;
        joinService = new JoinService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);
    }

    @Override
    public void createGame(String gameName, int numberOfPlayers, String color){
        if (gameName.equals("")){
            joinView.displayMessage("Invalid game name");
        }
        else if (numberOfPlayers < 2 || numberOfPlayers > 5){
            joinView.displayMessage("Invalid number of players");
        }
        else {
            String userId = clientModel.getUserId();
            Result result = JoinService.createGame(userId, gameName, numberOfPlayers);
            if (!result.isSuccess()){
                // Request failed
                joinView.displayMessage("Error creating game. " + result.getErrorMessage());
            }
            else {
                joinView.displayMessage("Game created!");
            }
        }
    }

    @Override
    public void joinGame(String gameId) {
        if (gameId == null || gameId.equals("")) {
            joinView.displayMessage("Invalid game id.");
        } else {
            String userId = clientModel.getUserId();
            Result result;
            if(clientModel.getMyActiveGame() == null) { //only join one game
                result = JoinService.joinGame(userId, gameId);
            } else if (clientModel.getMyActiveGame().getId() != gameId){
                result = new Result(false, "", "Already in a game.");
            } else {
                result = new Result(true, "game is already joined", null);
            }
            if (result.isSuccess()){
                clientModel.getUser().setGameId(gameId);
                joinView.displayMessage("Game Joined!");
                joinView.changeView(clientModel.getMyActiveGame() != null
                        && clientModel.getMyActiveGame().isStarted()
                ); //true: game activity, false: lobby activity
            }
            else {
                // Request failed
                joinView.displayMessage("Error joining game. " + result.getErrorMessage());
            }
        }
    }

    @Override
    public void update(Observable observable, Object arg){
        if(arg == null) { return; }

        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;

        switch(type) {
            case NEW_GAME_ADDED:
                joinView.addGame(clientModel.getGames().get(clientModel.getNewestGameId()));
                break;
            case PLAYER_ADDED:
                joinView.addPlayer(clientModel.getNewestPlayerAdded().first, clientModel.getNewestPlayerAdded().second);
                break;
        }
//        if (!clientModel.getCurrentGameID().isEmpty()){
//            joinView.changeView();
//        }
    }

}
