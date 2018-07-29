package ticket.com.tickettoridegames.client.presenter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ticket.com.tickettoridegames.client.State.PlayerState;
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
    
    public MapPresenter(IMapView view) {
        mapView = view;
        gamePlayService = new GamePlayService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);

        if(clientModel.getMyPlayer().getTempDeck().size() != 0){
            List<DestinationCard> cards = clientModel.getMyPlayer().getTempDeck();
            Set<DestinationCard> destinationCards = new HashSet<>(cards);
            mapView.displayDestinationCards(destinationCards);
            mapView.disableTurn();
        }
        else {
            mapView.disablePickRoutes();
        }
        mapView.setClaimedRoutes(clientModel.getClaimedRoutes());
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case TURNCHANGED:
                // This will use state in the future
                if (clientModel.isMyTurn()){
                    mapView.enableTurn();
                }
                else {
                    mapView.disableTurn();
                }
                break;
            case NEWTEMPDECK:
                String gameId = clientModel.getMyActiveGame().getId();
                String output = "options: ";

                List<DestinationCard> cards = clientModel.getMyPlayer().getTempDeck();
                Set<DestinationCard> destinationCards = new HashSet<>(cards);
                mapView.displayDestinationCards(destinationCards);
                break;
            case ROUTECLAIMED:
                mapView.setClaimedRoutes(clientModel.getClaimedRoutes());
            default:
                //updated data we don't care about
                break;
        }
    }

    //todo get rid of this function
    @Override
    public void passOff(){
        // Use this function for phase 2 pass off

        // Change turn
        clientModel.changeTurn(clientModel.getMyActiveGame().getId());

        // Change face up deck cards
        mapView.displayMessage("Prev trainCard at index 1: " + clientModel.getMyActiveGame().getTrainBank().get(1).getType());
        clientModel.getMyActiveGame().pickupTrainCard(clientModel.getMyPlayer().getId(), 1);

        // Route claiming.

        // Player Points change

        // Trains remaining change
    }

    @Override
    public void drawTrainCard() {
        getCurrentState().drawTrainCard(clientModel);
    }

    /**
     * Draws 3 destination cards for you to pick from
     *
     * @pre It is the given clients turn. (Can't be called when it is not your turn
     * @pre You don't have any cards in you're tempDeck
     * @post 3 destination cards should be added to your temp deck
     */
    @Override
    public void drawDestinationCards(){
        if (clientModel.getMyPlayer().getTempDeck().size() == 0) {
            getCurrentState().drawDestinationCard(clientModel);
        }
        else {
            mapView.displayMessage("You have already picked cards.");
        }
    }

    @Override
    public void changeTurn(){
        getCurrentState().changeTurn(clientModel);
        mapView.displayMessage("It's now " + clientModel.getMyActiveGame().playerUpString() + "'s turn");
    }

    @Override
    public void claimRoute(Route route){
        Integer length = 5;
        TrainCard.TRAIN_TYPE type = TrainCard.TRAIN_TYPE.BLACK;
        if (route == null){
            mapView.displayMessage("No route selected.");
            //Route routeStub = new Route(new City("a"), new City("b"), length, type);
            //clientModel.getMyActiveGame().claimRoute(clientModel.getMyPlayer().getId(), routeStub);
        }
        else {
            type = route.getType();
            length = route.getLength();
            getCurrentState().claimRoute(clientModel, route);
//            clientModel.claimRoute(clientModel.getMyPlayer().getId(), route);
//            clientModel.getMyActiveGame().claimRoute(clientModel.getMyPlayer().getId(), route);
        }
        mapView.displayMessage("Tried to claim route, type: " + type.toString() + " length: " + length
                + " prevTrains: " + clientModel.getMyPlayer().getTrains());
    }

    @Override
    public void setDestinationCards(LinkedList<DestinationCard> claimedCards, LinkedList<DestinationCard> discardedCards){
        if (claimedCards.size() < 2){
            mapView.displayMessage("Too few routes picked");
            List<DestinationCard> cards = clientModel.getMyPlayer().getTempDeck();
            Set<DestinationCard> newCards = new HashSet<>(cards);
            mapView.displayDestinationCards(newCards);
        }
        else {
            gamePlayService.claimDestinationCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId(), claimedCards);
            gamePlayService.returnDestinationCard(clientModel.getMyActiveGame().getId(), discardedCards);
            clientModel.clearTempDeck();
            mapView.disablePickRoutes();
        }
    }

    private PlayerState getCurrentState(){
        return clientModel.getCurrentState();
    }

}
