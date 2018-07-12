package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.view.ILobbyView;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.web.Result;

public class LobbyPresenter implements ILobbyPresenter, Observer {

    private LobbyService lobbyService;
    private ClientModel clientModel;
    private ILobbyView lobbyView;

    public LobbyPresenter(ILobbyView view){
        lobbyView = view;
        lobbyService = new LobbyService();
    }

    @Override
    public Result startGame(String gameID){
        if (gameID == null || gameID.equals("")){
            return new Result(false,"","Invalid game ID");
        }
        else {
            Result result = lobbyService.startGame(gameID);
            if (result.isSuccess()){
                return result;
            }
            else {
                // Error happened address as necessary.
                return result;
            }
        }
    }

//    @Override
//    public Result addPlayer(String gameID, String userID){
//        return new Result(false,"","Placeholder ERROR");
//    }

    @Override
    public Result sendMessage(String gameID, String userID, String message){
        if (gameID == null || gameID.equals("")){
            return new Result(false,"","Invalid game ID");
        }
        else if (userID == null || userID.equals("")){
            return new Result(false,"","Invalid player ID");
        }
        else {
            Result result = lobbyService.sendChat(gameID,userID,message);
            if (result.isSuccess()){
                return result;
            }
            else {
                // Error happened address as necessary.
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
