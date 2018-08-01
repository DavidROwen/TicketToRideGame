package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.view.IStatsView;
import ticket.com.utility.TYPE;
import ticket.com.utility.web.Result;

public class StatsPresenter implements IStatsPresenter , Observer {

    private ClientModel clientModel;
    private IStatsView statsView;

    public StatsPresenter(IStatsView view){
        statsView = view;
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);
        //clientModel.getMyActiveGame().addObserver(this);

        statsView.setChat(ClientModel.get_instance().getMyActiveGame().getChatList());
        statsView.setPlayerStats(ClientModel.get_instance().getMyActiveGame().getPlayerStats());
        statsView.setHistory(ClientModel.get_instance().getMyActiveGame().getGameHistory());
    }

    private String getActiveGameId(){
        return clientModel.getMyActiveGame().getId();
    }

    @Override
    public void update(Observable observable, Object arg){
        ClientModel clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        //Update the stats, chats and history here.
        switch(type){
            case NEWCHAT:
                statsView.displayChat(clientModel.getNewestChat(getActiveGameId()));
                break;
            case ROUTECLAIMED:
                statsView.setPlayerStats(clientModel.getPlayerStats());
                break;
            case ALLHISTORY:
                statsView.setHistory(clientModel.getHistory());
                break;
            case HISTORYUPDATE:
                statsView.displayHistory(clientModel.getNewestGameHistory());
                break;
            case NEWTRAINCARD:
                statsView.setPlayerStats(clientModel.getPlayerStats());
                break;
            default:
                // We got an update that we don't care about.
                break;
        }
    }

    @Override
    public void sendMessage(String message){
        String userID = clientModel.getUserId();
        String gameID = clientModel.getUser().getGameId();
        if (gameID == null || gameID.equals("")){
            statsView.displayMessage("Invalid game ID, failed to send.");
        }
        else if (userID == null || userID.equals("")){
            statsView.displayMessage("Invalid player ID, failed to send.");
        }
        else if (message == null || message.isEmpty()){
            statsView.displayMessage("Invalid message, failed to send.");
        }
        else {
            Result result = LobbyService.sendChat(gameID,userID,message);
            if (!result.isSuccess()){
                // Error happened address as necessary.
                statsView.displayMessage("Error sending message. ");
            }
        }
    }

}
