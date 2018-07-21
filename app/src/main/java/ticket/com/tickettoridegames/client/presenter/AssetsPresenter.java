package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.tickettoridegames.utility.TYPE;

public class AssetsPresenter implements IAssetsPresenter, Observer {
    private GamePlayService gamePlayService;
    private ClientModel clientModel;
    private IAssetsView assetsView;

    public AssetsPresenter(IAssetsView view){
        assetsView = view;
        gamePlayService = new GamePlayService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);


        assetsView.setBank(clientModel.getMyActiveGame().getTrainBank());
        
        String userID = clientModel.getUserId();
        assetsView.setHand(clientModel.getMyActiveGame().getPlayer(userID).getTrainCards());
        assetsView.setRoutes(clientModel.getMyActiveGame().getPlayer(userID).getDestinationCards());
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case BANKUPDATE:
                // Someone else drew a card so update your view
                assetsView.setBank(clientModel.getMyActiveGame().getTrainBank());
            default:
                //Why you updated me?
        }
    }

    @Override
    public void drawFromBank(Integer stackID){

        // We use the stack index to indicate what card should change.
    }
}
