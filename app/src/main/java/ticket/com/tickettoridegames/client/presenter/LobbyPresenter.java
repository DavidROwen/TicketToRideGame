package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.view.ILobbyView;
import ticket.com.tickettoridegames.utility.TYPE;
import ticket.com.tickettoridegames.utility.web.Result;


public class LobbyPresenter implements ILobbyPresenter, Observer {

    private LobbyService lobbyService;
    private ClientModel clientModel;
    private ILobbyView lobbyView;

    public LobbyPresenter(ILobbyView view){
        lobbyView = view;
        lobbyService = new LobbyService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);
    }

    @Override
    public void startGame(){
        String gameID = clientModel.getUser().getGameId();
        if (gameID == null || gameID.equals("")){
            lobbyView.displayMessage("Invalid game ID");
        }
        else {
            //if (clientModel.getGames().get(gameID).getPlayersId().size() > 1) {
                //lobbyView.displayMessage("Successfully create game.");
                Result result = lobbyService.startGame(gameID);
                if (result.isSuccess()) {
                    lobbyView.displayMessage("Successfully started game.");
                } else {
                    // Error happened address as necessary.
                    lobbyView.displayMessage("Failed to start game. " + result.getErrorMessage());
                }
            //}
            //else {
                //lobbyView.displayMessage("Too few players to start game.");
            //
        }
    }

    @Override
    public void sendMessage(String message){
        String userID = clientModel.getUserId();
        String gameID = clientModel.getUser().getGameId();
        if (gameID == null || gameID.equals("")){
            lobbyView.displayMessage("Invalid game ID");
        }
        else if (userID == null || userID.equals("")){
            lobbyView.displayMessage("Invalid player ID");
        }
        else {
            Result result = lobbyService.sendChat(gameID,userID,message);
            if (result.isSuccess()){
                // don't do anything right?
            }
            else {
                // Error happened address as necessary.
                lobbyView.displayMessage("Error sending message. ");
            }
        }
    }

//    @Override
//    public Result addPlayer(String gameID, String userID){
//        return new Result(false,"","Placeholder ERROR");
//    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case START:
                if (clientModel.isGameStarted(clientModel.getCurrentGameID())) {
                    lobbyView.displayMessage("Game starting.");
                }
                break;
            case NEWCHAT:
                lobbyView.displayChat(clientModel.getNewestChat(clientModel.getCurrentGameID()));
                break;
            case ALLCHAT:
                lobbyView.setChat(clientModel.getGameChat(clientModel.getCurrentGameID()));
                break;
            default:
                lobbyView.displayMessage("Update Error");
                break;
        }
        // update view here
        resetPlayersList();
    }

    private void resetPlayersList() {
        lobbyView.resetPlayers(clientModel.getGamePlayersName(clientModel.getCurrentGameID()));
    }
}
