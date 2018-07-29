package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.utility.model.Route;

public class MyTurnState extends PlayerState {

    private static MyTurnState instance = new MyTurnState();
    private GamePlayService gamePlayService;
    public static MyTurnState getInstance(){
        return instance;
    }
    private MyTurnState(){
        gamePlayService = new GamePlayService();
    }

    public void drawTrainCard(ClientModel cm){
        gamePlayService.drawTrainCard(cm.getUserId(), cm.getCurrentGameID());
    }

    public void drawDestinationCard(ClientModel cm){
        gamePlayService.drawDestinationCard(cm.getUserId(), cm.getCurrentGameID());
    }

    public void changeTurn(ClientModel cm) {
        //todo add a GamePlayService function here
        cm.changeTurn(cm.getCurrentGameID());
        cm.setState(NotMyTurnState.getInstance());
    }

    public void claimRoute(ClientModel cm, Route route) {
        //todo add a GamePlayService function to call here
        cm.claimRoute(cm.getUserId(), route);
    }

    public void drawFromBank(ClientModel cm) {}

}
