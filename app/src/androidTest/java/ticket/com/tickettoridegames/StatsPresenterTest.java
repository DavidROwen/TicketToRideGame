package ticket.com.tickettoridegames;

import org.junit.Test;

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
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.PlayerAction;
import ticket.com.tickettoridegames.utility.model.PlayerStats;
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

    }

    @Test
    public void testHistory() {

    }

    @Test
    public void testStats() {
        initToGameplay();
        //initialize
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
        private List<Chat> chats;
        private List<PlayerAction> history;
        private List<PlayerStats> stats;

        @Override
        public void displayChat(Chat message) {
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
