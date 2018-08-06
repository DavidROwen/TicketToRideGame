package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

//version of notMyTurnState
public class LastRoundState extends PlayerState {

    private static LastRoundState instance = new LastRoundState();
    public static LastRoundState getInstance(){
        return instance;
    }
    private LastRoundState(){}

    public void drawTrainCard(IMapPresenter presenter, ClientModel cm){
        //can't draw a train card it is not your turn
        //should we send back a message or just leave this blank?
    }

    public void drawDestinationCard(IMapPresenter presenter, ClientModel cm){
        presenter.getMapView().displayMessage("Not your turn, you may not draw destination cards.");
    }

    public void changeTurn(ClientModel cm) {
        if(cm.isMyTurn()){
            cm.setState(LastTurnState.getInstance());
        }
        else {
            cm.setState(LastRoundState.getInstance());
        }
    }

    @Override
    public boolean canClaim(String routeName) {
        return false;
    }

    @Override
    public Result claimRoute(IMapPresenter presenter, ClientModel cm, String routeName, TrainCard.TRAIN_TYPE routeType) {
        return new Result(false, null, "It's not your turn");
    }

    public void drawFromBank(IAssetsPresenter presenter, Integer index) {
        presenter.getAssetsView().displayMessage("It's not your turn.");
    }

    public void transition(IMapPresenter presenter, ClientModel clientModel){
        if (clientModel.isMyTurn()){
            clientModel.setState(LastTurnState.getInstance());
            presenter.getMapView().displayMessage("It's your turn");
        }
        else {
            presenter.getMapView().displayMessage("It's " + ClientModel.get_instance().getMyActiveGame().getTurnUsername() + "'s turn");
        }
    }

    public void routeClaimed(IMapPresenter presenter, ClientModel cm){}

    @Override
    public void updateMapButtons(IMapPresenter presenter) {
        presenter.getMapView().enableRoutes(false);
        presenter.getMapView().enableTrainCardDeck(false);
        presenter.getMapView().enableDestinationCardDeck(false);
    }
}
