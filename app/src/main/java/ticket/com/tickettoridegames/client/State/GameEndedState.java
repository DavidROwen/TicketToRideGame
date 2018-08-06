package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

public class GameEndedState extends  PlayerState{

    private static GameEndedState instance = new GameEndedState();
    public static GameEndedState getInstance(){
        return instance;
    }
    private GameEndedState(){}

    public void enter(ClientModel cm){}

    public void exit(ClientModel cm){}

    public void drawTrainCard(IMapPresenter presenter, ClientModel cm){}

    public void drawDestinationCard(IMapPresenter presenter, ClientModel cm){}

    public void changeTurn(ClientModel cm) {}

    public Result claimRoute(IMapPresenter presenter, ClientModel cm, String routeName, TrainCard.TRAIN_TYPE routeType) {
        return null;
    }

    public void drawFromBank(IAssetsPresenter presenter, Integer index) {}

    public boolean canClaim(String routeName) {
        return false;
    }

    public void transition(IMapPresenter presenter, ClientModel clientModel){ }

    public void routeClaimed(IMapPresenter presenter, ClientModel cm){}

    @Override
    public void updateMapButtons(IMapPresenter presenter) {
        presenter.getMapView().enableRoutes(false);
        presenter.getMapView().enableTrainCardDeck(false);
        presenter.getMapView().enableDestinationCardDeck(false);
    }
}
