package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.tickettoridegames.utility.TYPE;
import ticket.com.tickettoridegames.utility.model.Game;

public class AssetsPresenter implements IAssetsPresenter, Observer {
    private GamePlayService gamePlayService;
    private ClientModel clientModel;
    private IAssetsView assetsView;

    public AssetsPresenter(IAssetsView view){
        assetsView = view;
        gamePlayService = new GamePlayService();
        clientModel = ClientModel.get_instance();
//        clientModel.addObserver(this);
        clientModel.getMyActiveGame().addObserver(this);

        assetsView.setBank(clientModel.getMyActiveGame().getTrainBank());

        String userID = clientModel.getUserId();
        assetsView.setHand(clientModel.getMyActiveGame().getPlayer(userID).getTrainCards());
        assetsView.setRoutes(clientModel.getMyActiveGame().getPlayer(userID).getDestinationCards());
    }

    @Override
    public void update(Observable observable, Object arg){
//        clientModel = (ClientModel) observable;
        Game game = (Game) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case BANKUPDATE:
                // Someone else drew a card so update your view
                assetsView.setBank(game.getTrainBank());
                break;
            case NEWROUTE:
//                assetsView.setRoutes(ClientModel.getDestinationCards());
                break;
            case NEWTRAINCARD:
                assetsView.setHand(clientModel.getMyPlayer().getTrainCards());
                break;
        }
    }

    @Override
    public void drawFromBank(Integer stackID){

        // We use the stack index to indicate what card should change.
    }

    @Override
    public void pickupCard(Integer index) {
        gamePlayService.pickupTrainCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId(), index);
    }
}
