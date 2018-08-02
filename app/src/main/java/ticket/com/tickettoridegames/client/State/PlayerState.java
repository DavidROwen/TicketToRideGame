package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.utility.web.Result;

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

    public void drawDestinationCard(IMapPresenter presenter, ClientModel cm){}

    public void changeTurn(ClientModel cm) {}

    public Result claimRoute(IMapPresenter presenter, ClientModel cm, String route) {
        return new Result(false, null, "It is not your turn, you are not able to claim routes.");
    }

    public void drawFromBank(IAssetsPresenter presenter, Integer index) {}
}
