package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

public class DrewOneTrainState extends PlayerState {

    private static DrewOneTrainState instance = new DrewOneTrainState();
    private GamePlayService gamePlayService;
    public static DrewOneTrainState getInstance(){
        return instance;
    }
    private DrewOneTrainState(){
        gamePlayService = new GamePlayService();
    }

    public void enter(ClientModel cm){}

    public void exit(ClientModel cm){}

    public void drawTrainCard(IMapPresenter presenter, ClientModel cm){
        gamePlayService.drawTrainCard(cm.getUserId(), cm.getCurrentGameID());
        gamePlayService.switchTurn(cm.getCurrentGameID());
        cm.setState(NotMyTurnState.getInstance());
    }

    public void drawDestinationCard(IMapPresenter presenter, ClientModel cm){}

    public void changeTurn(ClientModel cm) {}

    @Override
    public boolean canClaim(String routeName) {
        return false;
    }

    @Override
    public Result claimRoute(IMapPresenter presenter, ClientModel cm, String routeName, TrainCard.TRAIN_TYPE routeType) {
        return new Result(false, null, "Cannot claim a route after drawing a card.");
    }

    public void drawFromBank(IAssetsPresenter presenter, Integer index) {
        ClientModel clientModel = ClientModel.get_instance();
        Game game = clientModel.getMyActiveGame();

        // check these before modifying the deck to prevent race conditions
        boolean wildCard = game.isBankCardWild(index);

        // send command to server
        if (!wildCard) {
            gamePlayService.pickupTrainCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId(), index);
            gamePlayService.switchTurn(game.getId());
            clientModel.setState(NotMyTurnState.getInstance());
        }
        else {
            presenter.getAssetsView().displayMessage("You are not allowed to draw a locomotive on your second draw.");
        }
    }

    @Override
    public void transition(IMapPresenter presenter, ClientModel clientModel){
        if (!clientModel.isMyTurn()) {
            clientModel.setState(NotMyTurnState.getInstance());
            presenter.getMapView().displayMessage("It's " + ClientModel.get_instance().getMyActiveGame().getTurnUsername() + "'s turn");
            presenter.getMapView().disableButtons();
        }
    }

    public void routeClaimed(IMapPresenter presenter, ClientModel cm){}
}