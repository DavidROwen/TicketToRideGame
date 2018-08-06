package ticket.com.tickettoridegames.client.presenter;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.State.PlayerState;
import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.utility.TYPE;
import ticket.com.utility.model.TrainCard;

public class AssetsPresenter implements IAssetsPresenter, Observer {
    private GamePlayService gamePlayService;
    private ClientModel clientModel;

    private IAssetsView assetsView;

    public AssetsPresenter(IAssetsView view){
        assetsView = view;
        gamePlayService = new GamePlayService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);

        updateView();
    }

    @Override
    public void update(Observable observable, Object arg){
        ClientModel clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case BANKUPDATE:
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
                assetsView.setHand(getSortedHand());
                assetsView.setTrainDeckCount(clientModel.getTrainCardsDeck().size());
                break;
        }
    }

    public void updateView(){
        assetsView.setBank(clientModel.getMyActiveGame().getTrainBank());
        assetsView.setHand(getSortedHand());
        assetsView.setRoutes(clientModel.getMyPlayer().getDestinationCards());
        assetsView.setTrainDeckCount(clientModel.getMyActiveGame().getTrainCardsDeck().size());
        assetsView.setRouteDeckCount(clientModel.getMyActiveGame().getDestinationCards().size());
    }

    //makes a copy of players hand before sorting
    private List<TrainCard> getSortedHand() {
        List<TrainCard> playerHand = ClientModel.get_instance().getMyPlayer().getCopyTrainCards();
        playerHand.sort(TrainCard::compareTo);
        return playerHand;
    }


    @Override
    public void drawFromBank(Integer index){
        getCurrentState().drawFromBank(this, index);
    }

    public PlayerState getCurrentState(){
        return clientModel.getCurrentState();
    }

    public IAssetsView getAssetsView() {
        return assetsView;
    }
}
