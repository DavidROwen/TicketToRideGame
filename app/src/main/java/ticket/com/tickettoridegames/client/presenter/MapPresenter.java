package ticket.com.tickettoridegames.client.presenter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ticket.com.tickettoridegames.client.State.MyTurnState;
import ticket.com.tickettoridegames.client.State.NotMyTurnState;
import ticket.com.tickettoridegames.client.State.PlayerState;
import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.IMapView;
import ticket.com.utility.TYPE;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Pair;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

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
            //mapView.disableButtons();
        }

        checkIsMyTurn();
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            case TURN_NUMBER_CHANGED:
                checkIsMyTurn();
                break;
            case NEWTEMPDECK:
                List<DestinationCard> cards = clientModel.getMyPlayer().getTempDeck();
                Set<DestinationCard> destinationCards = new HashSet<>(cards);
                mapView.displayDestinationCards(destinationCards);
                break;
            case ROUTECLAIMED:
                mapView.claimRoute(clientModel.getNewestClaimedRoute().first, clientModel.getNewestClaimedRoute().second);
                GamePlayService.checkHand(ClientModel.get_instance().getMyPlayer().getId(), //todo testing
                        ClientModel.get_instance().getMyActiveGame().getId());
                getCurrentState().routeClaimed(this, clientModel);
                break;
            case MAP_DREW_TRAINCARD:
                TrainCard card = ClientModel.get_instance().getMyPlayer().getNewestTrainCard();
                mapView.displayMessage("You drew a " + card.toString());
                GamePlayService.checkHand(ClientModel.get_instance().getMyPlayer().getId(), //todo testing
                        ClientModel.get_instance().getMyActiveGame().getId());
                break;
            case GAME_OVER:
                if (clientModel.getMyActiveGame().getGameOver()){
                    mapView.endGame();
                }
            default:
                break;
        }
    }

    @Override
    public void checkIsMyTurn(){
        getCurrentState().transition(this, ClientModel.get_instance());

//        if(getCurrentState() instanceof MyTurnState || ) {
//            mapView.displayMessage("It's your turn");
//            mapView.enableButtons();
//        } else if(getCurrentState() instanceof NotMyTurnState) {
//            mapView.displayMessage("It's " + ClientModel.get_instance().getMyActiveGame().getTurnUsername() + "'s turn");
//            mapView.disableButtons();
//        }
    }

    @Override
    public void drawTrainCard() {
        getCurrentState().drawTrainCard(this , clientModel);
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
            getCurrentState().drawDestinationCard(this, clientModel);
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
    public void claimRoute(String routeName){
        if(ClientModel.get_instance().getMyActiveGame().isRouteWild(routeName) && getCurrentState().canClaim(routeName)) {
            Map<String, Integer> playerCards = clientModel.getMyPlayer().getColorCardCounts();
            mapView.displayColorOptions(playerCards, routeName);
        } else {
            Result result = getCurrentState().claimRoute(this, clientModel, routeName,
                    clientModel.getMyActiveGame().getType(routeName));
            displayClaimResult(result, routeName);
        }
    }

    @Override
    public void claimGreyRoute(String routeName, TrainCard.TRAIN_TYPE typeChoice) {
        Result result = getCurrentState().claimRoute(this, clientModel, routeName, typeChoice);
        displayClaimResult(result, routeName);
    }

    private void displayClaimResult(Result result, String routeName) {
        if (result.isSuccess()) {
            mapView.displayMessage(result.getMessage());
            changeTurn();
        } else {
            mapView.displayMessage("Failed to claim route: " + routeName
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
            if(firstCall) {
                mapView.setFirstCall(false);
            }
            else{
                changeTurn();
            }
        }
    }

    @Override
    public void setClaimedRoutes() {
        List<Pair<String, Integer>> claimedRoutes = clientModel.getClaimedRoutes();

        for(Pair<String, Integer> each : claimedRoutes) {
            mapView.claimRoute(each.first, each.second);
        }
    }

    private PlayerState getCurrentState(){
        return clientModel.getCurrentState();
    }

    public IMapView getMapView() {
        return mapView;
    }
}
