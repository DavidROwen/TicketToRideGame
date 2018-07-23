package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.AssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.StatsPresenter;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.tickettoridegames.client.view.IStatsView;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.tickettoridegames.utility.TYPE;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.City;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.PlayerAction;
import ticket.com.tickettoridegames.utility.model.PlayerStats;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

import static org.junit.Assert.assertEquals;

public class StatsPresenterTest {
    private String otherId;
    private String userId;
    private String gameId;

    private Poller poller;
    private GamePlayService service;

    private StatsViewStub view;
    private StatsPresenter presenter;

    @Test
    public void testChat() {
        initToGameplay();

        String mes1 = "from user";
        String mes2 = "from other";
        LobbyService.sendChat(gameId, userId, mes1);
        LobbyService.sendChat(gameId, otherId, mes2);
        while(view.chats == null);
        while(view.chats.size() != 2);
        while(!view.chats.get(0).getMessage().equals(mes1));
        while(!view.chats.get(1).getMessage().equals(mes2));
    }

    @Test
    public void testHistory() {
        initToGameplay();

        service.initGame(gameId);
        while(view.history.size() == 0);
        Integer initSize = view.history.size();

        service.pickupTrainCard(userId, gameId, 0);
        while(view.history.size() == initSize);

        //check for null elements
        for(PlayerAction cur : view.history) {
            cur.toString();
        }
    }

    @Test
    public void testStats() {
        initToGameplay();

        //card count
        service.initGame(gameId);
        while(view.stats == null){}
        while(view.stats.size() != 2){}
        while(view.stats.get(0).getNumberOfCards() != 4){}
        while(view.stats.get(1).getNumberOfCards() != 4){}

        service.drawTrainCard(otherId,gameId);
        service.drawTrainCard(userId,gameId);
        service.drawTrainCard(userId,gameId);
        while(view.stats.get(0).getNumberOfCards() != 5){} //otherId
        while(view.stats.get(1).getNumberOfCards() != 6){} //userId

        //points
        //from uncompleted destination cards
        //from completed destination cards
        //from claiming a route
        getARedCard();
        Route redRoute = new Route(new City("a"), new City("b"), 1, TrainCard.TRAIN_TYPE.RED);
        service.claimingRoute(userId, redRoute);
        while(view.stats.get(1).getPoints() ==  0);
        while(view.stats.get(1).getNumberOfPieces() == 45);
    }

    private void getARedCard() {
        while (!ClientModel.get_instance().getMyPlayer().hasTrainCards(new TrainCard(TrainCard.TRAIN_TYPE.RED))) {
            Integer prevAmount = ClientModel.get_instance().getMyPlayer().getTrainCards().size();
            service.drawTrainCard(userId, gameId);
            while (ClientModel.get_instance().getMyPlayer().getTrainCards().size() == prevAmount) ;
        }
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

        view = new StatsViewStub();
        presenter = new StatsPresenter(view);
    }

    private class StatsViewStub implements IStatsView {
        private List<Chat> chats = new LinkedList<>();
        private List<PlayerAction> history = new LinkedList<>();
        private List<PlayerStats> stats = new LinkedList<>();

        @Override
        public void displayChat(Chat message) {
            chats.add(message);
        }

        @Override
        public void setChat(List<Chat> chats) {
            this.chats = chats;
        }

        @Override
        public void setHistory(List<PlayerAction> gameHistory) {
            this.history = gameHistory;
        }

        @Override
        public void displayHistory(PlayerAction pa) {
            history.add(pa);
        }

        @Override
        public void setPlayerStats(List<PlayerStats> playerStats) {
            this.stats = playerStats;
        }

        @Override
        public void setLongestTrainAward(String player) {

        }

        @Override
        public void displayMessage(String message) {

        }
    }
}
