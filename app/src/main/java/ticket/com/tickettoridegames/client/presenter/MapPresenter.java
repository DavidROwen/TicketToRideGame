package ticket.com.tickettoridegames.client.presenter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ticket.com.tickettoridegames.client.State.MyTurnState;
import ticket.com.tickettoridegames.client.State.PlayerState;
import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.IMapView;
import ticket.com.tickettoridegames.utility.TYPE;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.web.Result;

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
            //mapView.disableTurn();
        }
        mapView.setClaimedRoutes(clientModel.getClaimedRoutes());
        //temp fix
        checkTurn();
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case TURNCHANGED:
                checkTurn();
                break;
            case NEWTEMPDECK:
                List<DestinationCard> cards = clientModel.getMyPlayer().getTempDeck();
                Set<DestinationCard> destinationCards = new HashSet<>(cards);
                mapView.displayDestinationCards(destinationCards);
                break;
            case ROUTECLAIMED:
                mapView.claimRoute(clientModel.getNewestClaimedRoute().first, clientModel.getNewestClaimedRoute().second);
                break;
            default:
                break;
        }
    }

    private void checkTurn(){
        if (clientModel.isMyTurn()){
            clientModel.setState(MyTurnState.getInstance());
            mapView.displayMessage("It's your turn");
        }
        else {
            String playerTurn = "It's " + clientModel.getTurnUsername() + "'s Turn";
            mapView.displayMessage(playerTurn);
        }
    }

    //todo get rid of this function
    @Override
    public void passOff(){
        // Change turn
        clientModel.changeTurn(clientModel.getMyActiveGame().getId());
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
        if (clientModel.getMyPlayer().hasTempDeck()) {
            mapView.displayMessage("Drawing Destination Cards");
            getCurrentState().drawDestinationCard(clientModel);
        }
        else {
            mapView.displayMessage("You have already picked cards.");
        }
    }

    @Override
    public void changeTurn(){
        if(clientModel.isMyTurn()){
            mapView.displayMessage("Ending Turn");
            getCurrentState().changeTurn(clientModel);
        }
        else {
            mapView.displayMessage("It's not your turn");
        }
    }

    @Override
    public void claimRoute(String route){
        Result result = getCurrentState().claimRoute(clientModel, route);
        if(result.isSuccess()) {
            mapView.displayMessage("Successfully claimed: " + route);
        } else {
            mapView.displayMessage("Failed to claim route: " + route
                    + "\n" + result.getErrorMessage());
        }
    }

    @Override
    public void setDestinationCards(LinkedList<DestinationCard> claimedCards, LinkedList<DestinationCard> discardedCards, boolean firstCall){
        int minimumCount;
        if(firstCall){
            minimumCount = 2;
        }
        else{
            minimumCount = 1;
        }
        if (claimedCards.size() < minimumCount){
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

    public void setColorChoice(TrainCard.TRAIN_TYPE color){}

}
