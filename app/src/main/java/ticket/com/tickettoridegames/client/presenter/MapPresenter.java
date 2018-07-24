package ticket.com.tickettoridegames.client.presenter;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.IMapView;
import ticket.com.tickettoridegames.utility.TYPE;
import ticket.com.tickettoridegames.utility.model.City;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public class MapPresenter implements IMapPresenter, Observer {

    private GamePlayService gamePlayService;
    private ClientModel clientModel;
    private IMapView mapView;

    public MapPresenter(IMapView view){
        mapView = view;
        gamePlayService = new GamePlayService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case TURNCHANGED:
                if (clientModel.isMyTurn()){
                    // set the button here
                    mapView.enableTurn();
                }
                else {
                    mapView.disableTurn();
                }
            default:
                //Why you updated me?
        }
    }

    @Override
    public void passOff(){
        // Use this function for phase 2 pass off

        // Change turn
        clientModel.changeTurn(clientModel.getMyActiveGame().getId());

        // Change face up deck cards
        clientModel.getMyActiveGame().pickupTrainCard(clientModel.getMyPlayer().getId(), 1);

        // Route claiming.
        Route route = new Route(new City("a"), new City("b"), 2, TrainCard.TRAIN_TYPE.BLACK);
        clientModel.getMyActiveGame().claimRoute(clientModel.getMyPlayer().getId(), route);
        // Player Points change

        // Trains remaining change
    }

    @Override
    public void drawTrainCard() {
        new GamePlayService().drawTrainCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId());
    }

    @Override
    public void drawDestinationCards(){
        new GamePlayService().drawDestinationCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId());
    }

    @Override
    public void changeTurn(){
        clientModel.changeTurn(clientModel.getMyActiveGame().getId());
    }

    @Override
    public void claimRoute(Route route){
        clientModel.getMyActiveGame().claimRoute(clientModel.getMyPlayer().getId(), route);
        // Player Points change

        // Trains remaining change
    }

    @Override
    public void claimDestinationCard(LinkedList<DestinationCard> destinationCards){
        new GamePlayService().claimDestinationCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId(), destinationCards);
    }

    @Override
    public void returnDestinationCard(LinkedList<DestinationCard> destinationCards){
        new GamePlayService().returnDestinationCard(clientModel.getMyActiveGame().getId(), destinationCards);
    }
}
