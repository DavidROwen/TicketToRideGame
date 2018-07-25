package ticket.com.tickettoridegames.client.presenter;

import java.util.LinkedList;
import java.util.List;
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
                break;
            case NEWTEMPDECK:
                String gameId = clientModel.getMyActiveGame().getId();
                String output = "options: ";
                for(DestinationCard card : clientModel.getMyPlayer().getTempDeck()) {
                    output += card.toString() + " ";
                }
                output += "\n";

                //return card 0
                LinkedList<DestinationCard> returnedCards = new LinkedList<>();
                DestinationCard card0 = clientModel.getMyPlayer().getTempDeck().get(0);
                returnedCards.add(card0);
                gamePlayService.returnDestinationCard(gameId, returnedCards);
                output += "returning: " + card0.to_String()  + "\n";

                //claim cards
                LinkedList<DestinationCard> claimedCards = new LinkedList<>();
                DestinationCard card1 = clientModel.getMyPlayer().getTempDeck().get(1);
                claimedCards.add(card1);
                DestinationCard card2 = clientModel.getMyPlayer().getTempDeck().get(2);
                claimedCards.add(card2);
                gamePlayService.returnDestinationCard(gameId, claimedCards);
                output += "claiming: " + card1.to_String() + " " + card2.to_String() + "\n";

                mapView.displayMessage(output);
                break;
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
        mapView.displayMessage("Prev trainCard at index 1: " + clientModel.getMyActiveGame().getTrainBank().get(1).getType());
        clientModel.getMyActiveGame().pickupTrainCard(clientModel.getMyPlayer().getId(), 1);

        // Route claiming.

        // Player Points change

        // Trains remaining change
    }

    @Override
    public void drawTrainCard() {
        gamePlayService.drawTrainCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId());
    }

    @Override
    public void drawDestinationCards(){
        String gameId = clientModel.getMyActiveGame().getId();
        //get cards
        gamePlayService.drawDestinationCard(clientModel.getUserId(), gameId);
    }

    @Override
    public void changeTurn(){
        clientModel.changeTurn(clientModel.getMyActiveGame().getId());
        mapView.displayMessage("It's now " + clientModel.getMyActiveGame().playerUpString() + "'s turn");
    }

    @Override
    public void claimRoute(Route route){
        Integer length = 5;
        TrainCard.TRAIN_TYPE type = TrainCard.TRAIN_TYPE.BLACK;
        mapView.displayMessage("Tried to claim route, type: " + type.toString() + " length: " + length
                + " prevTrains: " + clientModel.getMyPlayer().getTrains());

        Route routeStub = new Route(new City("a"), new City("b"), length, type);
        clientModel.getMyActiveGame().claimRoute(clientModel.getMyPlayer().getId(), routeStub);
//        clientModel.getMyActiveGame().claimRoute(clientModel.getMyPlayer().getId(), route);
    }

    @Override
    public void claimDestinationCard(LinkedList<DestinationCard> destinationCards){
        gamePlayService.claimDestinationCard(clientModel.getUserId(), clientModel.getMyActiveGame().getId(), destinationCards);
    }

    @Override
    public void returnDestinationCard(LinkedList<DestinationCard> destinationCards){
        gamePlayService.returnDestinationCard(clientModel.getMyActiveGame().getId(), destinationCards);
    }
}
