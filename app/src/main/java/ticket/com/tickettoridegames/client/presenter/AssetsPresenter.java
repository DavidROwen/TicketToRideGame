package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.State.PlayerState;
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
        clientModel.addObserver(this);
        //clientModel.getMyActiveGame().addObserver(this);

        assetsView.setBank(clientModel.getMyActiveGame().getTrainBank());

        String userID = clientModel.getUserId();
        assetsView.setHand(clientModel.getMyActiveGame().getPlayer(userID).getTrainCards());
        assetsView.setRoutes(clientModel.getMyActiveGame().getPlayer(userID).getDestinationCards());
        assetsView.setTrainDeckCount(clientModel.getMyActiveGame().getTrainCardsDeck().size());
        assetsView.setRouteDeckCount(clientModel.getMyActiveGame().getDestinationCards().size());
    }

    @Override
    public void update(Observable observable, Object arg){
//        clientModel = (ClientModel) observable;
        ClientModel clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case BANKUPDATE:
                // Someone else drew a card so update your view
                assetsView.setBank(clientModel.getTrainBank());
                assetsView.setTrainDeckCount(clientModel.getTrainCardsDeck().size());
                break;
            case NEWROUTE:
                assetsView.setRouteDeckCount(clientModel.getDestinationCards().size());
                break;
            case NEW_DESTINATION_CARD:
                assetsView.setRoutes(clientModel.getMyPlayer().getDestinationCards());
                assetsView.setRouteDeckCount(clientModel.getDestinationCards().size());
                break;
            case NEWTRAINCARD:
                assetsView.setHand(clientModel.getMyPlayer().getTrainCards());
                assetsView.setTrainDeckCount(clientModel.getTrainCardsDeck().size());
                break;
        }
    }

    @Override
    public void drawFromBank(Integer index){
        // We use the index to indicate what card should change.
        if (true) {
            pickupCard(index);
        }
    }

    @Override
    public void pickupCard(Integer index) {
        getCurrentState().drawFromBank(this, index);
    }

    public PlayerState getCurrentState(){
        return clientModel.getCurrentState();
    }

    public IAssetsView getAssetsView() {
        return assetsView;
    }
}
