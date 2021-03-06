package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

//Version of drewOneTrainState
public class LastDrawState extends PlayerState {

    private GamePlayService gamePlayService;

    private static LastDrawState instance = new LastDrawState();
    public static LastDrawState getInstance(){
        return instance;
    }

    private LastDrawState(){
        gamePlayService = new GamePlayService();
    }

    public void enter(ClientModel cm){}

    public void exit(ClientModel cm){}

    public void drawTrainCard(IMapPresenter presenter, ClientModel cm){
        gamePlayService.drawTrainCard(cm.getUserId(), cm.getCurrentGameID());
        gamePlayService.endGame(cm.getMyActiveGame().getId());
        cm.setState(GameEndedState.getInstance());
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
        ClientModel cm = ClientModel.get_instance();
        Game game = cm.getMyActiveGame();

        // check these before modifying the deck to prevent race conditions
        boolean wildCard = game.isBankCardWild(index);

        // send command to server
        if (!wildCard) {
            gamePlayService.pickupTrainCard(cm.getUserId(), cm.getMyActiveGame().getId(), index);
            gamePlayService.endGame(cm.getMyActiveGame().getId());
        }
        else {
            presenter.getAssetsView().displayMessage("You are not allowed to draw a locomotive on your second draw.");
        }
    }

    public void transition(IMapPresenter presenter, ClientModel clientModel){
        if (!clientModel.isMyTurn()) {
            clientModel.setState(GameEndedState.getInstance());
            presenter.getMapView().displayMessage("The game is over!");
        }
    }

    public void routeClaimed(IMapPresenter presenter, ClientModel cm){}

    @Override
    public void updateMapButtons(IMapPresenter presenter) {
        presenter.getMapView().enableRoutes(false);
        presenter.getMapView().enableTrainCardDeck(true);
        presenter.getMapView().enableDestinationCardDeck(false);
    }
}
