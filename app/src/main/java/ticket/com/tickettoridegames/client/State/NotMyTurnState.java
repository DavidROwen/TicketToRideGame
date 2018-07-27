package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;

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
        cm.setState(MyTurnState.getInstance());
    }

    public void claimRoute(ClientModel cm) {
        //can't do that
    }

    public void drawFromBank(ClientModel cm) {
        //can't do that either
    }
}
