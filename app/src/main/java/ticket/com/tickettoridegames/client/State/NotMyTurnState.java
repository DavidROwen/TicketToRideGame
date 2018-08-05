package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

public class NotMyTurnState extends PlayerState {

    private static NotMyTurnState instance = new NotMyTurnState();
    public static NotMyTurnState getInstance(){
        return instance;
    }
    private NotMyTurnState(){}

    public void drawTrainCard(IMapPresenter presenter, ClientModel cm){
        //can't draw a train card it is not your turn
        //should we send back a message or just leave this blank?
    }

    public void drawDestinationCard(IMapPresenter presenter, ClientModel cm){
        presenter.getMapView().displayMessage("Not your turn, you may not draw destination cards.");
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

    @Override
    public void transition(IMapPresenter presenter, ClientModel clientModel){
        if (clientModel.isMyTurn()) {
            clientModel.setState(MyTurnState.getInstance());
        }
    }

    public void routeClaimed(IMapPresenter presenter, ClientModel cm){
        if (cm.getMyPlayer().getTrains() <= 3){
            cm.setState(LastRoundState.getInstance());
        }
    }
}
