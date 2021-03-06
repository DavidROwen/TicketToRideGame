package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.view.ILobbyView;
import ticket.com.utility.TYPE;
import ticket.com.utility.model.Chat;
import ticket.com.utility.web.Result;

public class LobbyPresenter implements ILobbyPresenter, Observer {

    private LobbyService lobbyService;
    private ClientModel clientModel;
    private ILobbyView lobbyView;

    public LobbyPresenter(ILobbyView view){
        lobbyView = view;
        lobbyService = new LobbyService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);

        updateView();
    }

    @Override
    public void updateView(){
        resetPlayersList();
        setChat();
    }

    private void setChat(){
        if (clientModel.getMyActiveGame() != null){
            lobbyView.setChat(clientModel.getMyActiveGame().getChatList());
        }
    }

    @Override
    public void startGame(){
        String gameID = clientModel.getUser().getGameId();
        if (gameID == null || gameID.equals("")){
            lobbyView.displayMessage("Invalid game ID");
        }
        else {
            Result result = LobbyService.startGame(gameID);
            if (result.isSuccess()) {
                GamePlayService.initGame(gameID);
                lobbyView.displayMessage("Successfully started game.");
                //don't add lobbyView.changeView(), it's already in update
            } else {
                // Error happened address as necessary.
                lobbyView.displayMessage("Failed to start game. " + result.getErrorMessage());
            }
        }
    }

    @Override
    public void leaveGame(){
        String gameID = clientModel.getUser().getGameId();
        if (gameID == null || gameID.equals("")){
            lobbyView.displayMessage("Invalid game ID");
        }
        else {
            Result result = LobbyService.leaveGame(gameID, clientModel.getMyPlayer().getId());
            if (result.isSuccess()) {
                lobbyView.displayMessage("Successfully left game.");
            } else {
                // Error happened address as necessary.
                lobbyView.displayMessage("Failed to leave game. " + result.getErrorMessage());
            }
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
            Result result = LobbyService.sendChat(gameID,userID,message);
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
        if(arg == null) { return; } //no functionality for it
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case START:
                if (clientModel.isGameStarted(clientModel.getCurrentGameID())) {
                    lobbyView.displayMessage("Game starting.");
                    lobbyView.changeView();
                }
                break;
            case NEWCHAT:
                lobbyView.displayChat(clientModel.getNewestChat(clientModel.getCurrentGameID()));
                break;
            case ALLCHAT:
                lobbyView.setChat(clientModel.getGameChat(clientModel.getCurrentGameID()));
                break;
            case PLAYER_ADDED:
                lobbyView.addPlayerName(clientModel.getUser().getUsername());
                break;
            case REMOVED_PLAYER:
                if (clientModel.getMyActiveGame() == null){
                    lobbyView.leaveGame();
                }
            default:
                // update we don't care about
                break;
        }
        // update view here
        resetPlayersList();
    }

    public void resetPlayersList() {
        lobbyView.resetPlayers(clientModel.getGamePlayersName(clientModel.getCurrentGameID()));
    }
}
