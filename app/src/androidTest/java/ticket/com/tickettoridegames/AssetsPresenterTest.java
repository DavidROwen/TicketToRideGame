package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.AssetsPresenter;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

import static org.junit.Assert.assertEquals;

public class AssetsPresenterTest {
    private String otherId;
    private String userId;
    private String gameId;

    private Poller poller;
    private GamePlayService service;

    private AssetsViewStub view;
    private AssetsPresenter presenter;

    @Test
    public void testHand() {
        initToGameplay();

        //init
        service.initGame(gameId);
        while(view.hand.size() != 4){}

        //add
        service.drawTrainCard(userId, gameId);
        while(view.hand.size() != 5){}
        service.pickupTrainCard(userId, gameId, 0);
        while(view.hand.size() != 6){}

        //remove
        //also removing cards
    }

    @Test
    public void testBank() {
        initToGameplay();

        //init
        service.initGame(gameId);
        while(view.trainBank.size() != 5){}

        //replace
        TrainCard prev0 = view.trainBank.get(0);
        service.pickupTrainCard(userId, gameId, 0);
        while(view.hand.size() != 5){}
        TrainCard cur0 = view.trainBank.get(1);
        System.out.println("prev: " + prev0.getType() + " cur: " + cur0.getType());
    }

    @Test
    public void testRoutes() {
    }

    @Test
    public void testAddTrainCard() {

    }

    private void initToGameplay() {
        new UtilityService().clearServer();

        //login
        User otherPlayer = new User("username", "password");
        Result registerResult = new LoginService().register(otherPlayer);
        otherId = registerResult.getMessage();

        User user = new User("username2", "password2"); //register auto logs in //so it resets user
        Result registerResult1 = new LoginService().register(user);
        userId = registerResult1.getMessage();

        //poller
        poller = new Poller(); //get it going

        //create game
        Result joinResult = JoinService.createGame(otherId, "gameName", 2);
        gameId = joinResult.getMessage();
        while(ClientModel.get_instance().getGames().isEmpty()){}

        //join game
        Player player1 = new Player(otherPlayer.getUsername(), otherPlayer.getId());
        JoinService.joinGame(otherId, gameId);

        Player player2 = new Player(user.getUsername(), user.getId());
        JoinService.joinGame(userId, gameId);
        while(ClientModel.get_instance().getMyActiveGame() == null){}

        //start game
        LobbyService.startGame(gameId);
        while(!ClientModel.get_instance().isGameStarted(gameId)){}

        service = new GamePlayService();

        view = new AssetsViewStub();
        presenter = new AssetsPresenter(view);
    }

    private class AssetsViewStub implements IAssetsView {
        public List<TrainCard> hand;
        public List<TrainCard> trainBank;
        public Set<DestinationCard> routes;

        @Override
        public void setHand(List<TrainCard> hand) {
            this.hand = hand;
        }

        @Override
        public void setBank(List<TrainCard> trainBank) {
            this.trainBank = trainBank;
        }

        @Override
        public TrainCard getBankChoice(TrainCard trainCard) {
            //todo should return an index
            return null;
        }

        @Override
        public void setRoutes(Set<DestinationCard> destinationCards) {
            this.routes = destinationCards;
        }

        @Override
        public void displayMessage(String message) {
            System.out.println("message: " + message);
        }

        @Override
        public void pickupCard(Integer index) {

        }

        @Override
        public void addRoute(Set<DestinationCard> destinationCards) {

        }

        @Override
        public void setTrainDeckCount(Integer size){}

        @Override
        public void setRouteDeckCount(Integer size){}

    }
}
