package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;

public class PlayerState {

    public void enter(ClientModel cm){
        //nothing for now
        //override in subclass if needed
    }

    public void exit(ClientModel cm){
        //nothing for now
        //override in subclass if needed
    }

    public void drawTrainCard(ClientModel cm){}

    public void drawDestinationCard(ClientModel cm){}

    public void changeTurn(ClientModel cm) {}

    public void claimRoute(ClientModel cm) {}

    public void drawFromBank(ClientModel cm) {}
}
