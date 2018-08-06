package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

//Version of myTurnState
public class LastTurnState extends PlayerState {

    private static LastTurnState instance = new LastTurnState();
    public static LastTurnState getInstance(){
        return instance;
    }
    private LastTurnState(){
        gamePlayService = new GamePlayService();
    }
    private GamePlayService gamePlayService;


    public void drawTrainCard(IMapPresenter presenter, ClientModel cm){
        gamePlayService.drawTrainCard(cm.getUserId(), cm.getCurrentGameID());
        cm.setState(LastDrawState.getInstance());
    }

    public void drawDestinationCard(IMapPresenter presenter, ClientModel cm){
        gamePlayService.drawDestinationCard(cm.getUserId(), cm.getCurrentGameID());
        gamePlayService.endGame(cm.getMyActiveGame().getId());
        cm.setState(GameEndedState.getInstance());
        presenter.getMapView().endGame();
    }

    public void changeTurn(ClientModel cm) {
        gamePlayService.switchTurn(cm.getCurrentGameID());
        gamePlayService.endGame(cm.getMyActiveGame().getId());
        cm.setState(GameEndedState.getInstance());
    }

    @Override
    public boolean canClaim(String routeName) {
        return ClientModel.get_instance().getMyActiveGame().getMap().canClaim(
                routeName, ClientModel.get_instance().getMyPlayer().getId(),
                ClientModel.get_instance().getMyActiveGame().getNumberOfPlayers()
        ).isSuccess();
    }

    @Override
    public Result claimRoute(IMapPresenter presenter, ClientModel cm, String routeName, TrainCard.TRAIN_TYPE routeType) {
        Result result = GamePlayService.claimRoute(cm.getMyActiveGame().getId(), cm.getMyPlayer().getId(),
                routeName, routeType);
        if (result.isSuccess()){
            gamePlayService.endGame(cm.getMyActiveGame().getId());
            cm.setState(GameEndedState.getInstance());
        }
        return result;
    }

    public void drawFromBank(IAssetsPresenter presenter, Integer index) {
        ClientModel cm = ClientModel.get_instance();
        Game game = cm.getMyActiveGame();

        // check these before modifying the deck to prevent race conditions
        boolean wildCard = game.isBankCardWild(index);

        // send command to server
        gamePlayService.pickupTrainCard(cm.getUserId(), cm.getMyActiveGame().getId(), index);

        // end turn based on drawing a bank wild
        if (wildCard) {
            gamePlayService.endGame(cm.getMyActiveGame().getId());
            cm.setState(GameEndedState.getInstance());
        }
        else {
            cm.setState(LastDrawState.getInstance());
        }
    }

    @Override
    public void transition(IMapPresenter presenter, ClientModel clientModel){
        if(!ClientModel.get_instance().isMyTurn()) {
            clientModel.setState(GameEndedState.getInstance());
            presenter.getMapView().displayMessage("The game is over!");
        }
        else {
            updateMapButtons(presenter);
        }
    }

    public void routeClaimed(IMapPresenter presenter, ClientModel cm){
        gamePlayService.endGame(cm.getMyActiveGame().getId());
        cm.setState(GameEndedState.getInstance());
    }

    @Override
    public void updateMapButtons(IMapPresenter presenter) {
        presenter.getMapView().enableRoutes(true);
        presenter.getMapView().enableTrainCardDeck(true);
        presenter.getMapView().enableDestinationCardDeck(true);
    }
}
