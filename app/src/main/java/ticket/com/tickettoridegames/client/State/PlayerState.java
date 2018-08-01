package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.utility.web.Result;

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

    public Result claimRoute(ClientModel cm, String route) {
        return new Result(false, null, "Claim hasn't been set up for cur state yet");
    }

    public void drawFromBank(IAssetsPresenter presenter, Integer index) {}
}
