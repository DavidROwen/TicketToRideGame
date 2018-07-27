package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;

public class MyTurnState extends PlayerState {

    private static MyTurnState instance = new MyTurnState();
    public static MyTurnState getInstance(){
        return instance;
    }
    private MyTurnState(){}

    public void drawTrainCard(ClientModel cm){}

    public void drawDestinationCard(ClientModel cm){}

    public void changeTurn(ClientModel cm) {}

    public void claimRoute(ClientModel cm) {}

    public void drawFromBank(ClientModel cm) {}

}
