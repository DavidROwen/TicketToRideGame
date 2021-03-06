package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.GamePlayActivity;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

public class MyTurnState extends PlayerState {

    private static MyTurnState instance = new MyTurnState();
    private GamePlayService gamePlayService;
    public static MyTurnState getInstance(){
        return instance;
    }
    private MyTurnState(){
        gamePlayService = new GamePlayService();
    }

    public void drawTrainCard(IMapPresenter presenter, ClientModel cm){
        gamePlayService.drawTrainCard(cm.getUserId(), cm.getCurrentGameID());
        cm.setState(DrewOneTrainState.getInstance());
    }

    public void drawDestinationCard(IMapPresenter presenter, ClientModel cm){
        gamePlayService.drawDestinationCard(cm.getUserId(), cm.getCurrentGameID());
    }

    public void changeTurn(ClientModel cm) {
        gamePlayService.switchTurn(cm.getCurrentGameID());
        cm.setState(NotMyTurnState.getInstance());
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
            GamePlayService.switchTurn(cm.getCurrentGameID());
            if (cm.getMyPlayer().getTrains() <= 3){
                cm.setState(LastRoundState.getInstance());
            }
            else {
                cm.setState(NotMyTurnState.getInstance());
            }
        }
        return result;
    }

    public void drawFromBank(IAssetsPresenter presenter, Integer index) {
        ClientModel clientModel = ClientModel.get_instance();
        Game game = clientModel.getMyActiveGame();

        // check these before modifying the deck to prevent race conditions
        boolean wildCard = game.isBankCardWild(index);

        // send command to server
        GamePlayService.pickupTrainCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId(), index);

        // end turn based on drawing a bank wild
        if (wildCard) {
            GamePlayService.switchTurn(game.getId());
            clientModel.setState(NotMyTurnState.getInstance());
        }
        else {
            clientModel.setState(DrewOneTrainState.getInstance());
        }
    }

    @Override
    public void transition(IMapPresenter presenter, ClientModel clientModel){
        if (!clientModel.isMyTurn()) {
            clientModel.setState(NotMyTurnState.getInstance());
        }
    }

    public void routeClaimed(IMapPresenter presenter, ClientModel cm){
        if (cm.getMyPlayer().getTrains() <= 3){
            cm.setState(LastRoundState.getInstance());
        }
    }

    @Override
    public void updateMapButtons(IMapPresenter presenter) {
        presenter.getMapView().enableRoutes(true);
        presenter.getMapView().enableTrainCardDeck(true);
        presenter.getMapView().enableDestinationCardDeck(true);
    }
}
