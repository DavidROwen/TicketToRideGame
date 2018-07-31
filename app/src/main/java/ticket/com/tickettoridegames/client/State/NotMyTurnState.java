package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.utility.web.Result;

public class NotMyTurnState extends PlayerState {

    private static NotMyTurnState instance = new NotMyTurnState();
    public static NotMyTurnState getInstance(){
        return instance;
    }
    private NotMyTurnState(){}

    public void drawTrainCard(ClientModel cm){
        //can't draw a train card it is not your turn
        //should we send back a message or just leave this blank?
    }

    public void drawDestinationCard(ClientModel cm){
        //can't draw it's not your turn
        //send a message back?
    }

    public void changeTurn(ClientModel cm) {
        //check somehow which state to change to..

        //for now just set it to active turn
        if(cm.isMyTurn()){
            cm.setState(MyTurnState.getInstance());
        }
        else{
            cm.setState(NotMyTurnState.getInstance());
        }
    }

    public Result claimRoute(ClientModel cm) {
        return new Result(false, null, "It's not your turn");
    }

    public void drawFromBank(ClientModel cm) {
        //can't do that either
    }
}
