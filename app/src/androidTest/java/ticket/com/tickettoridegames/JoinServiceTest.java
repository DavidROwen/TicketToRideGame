package ticket.com.tickettoridegames;

import org.junit.Test;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

import static org.junit.Assert.assertEquals;

public class JoinServiceTest {
    private String otherId;
    private String userId;
    private String gameId;

    User otherPlayer;
    User user;

    private Poller poller;
    private GamePlayService service;

    @Test
    public void testCreateGame() {
        initToJoin();

        Result joinResult = JoinService.createGame(otherId, "gameName", 2);
        gameId = joinResult.getMessage();
        while(ClientModel.get_instance().getGames().get(gameId) == null){}
    }

    @Test
    public void testJoinGame() {
        initToJoin();

        Result joinResult = JoinService.createGame(otherId, "gameName", 2);
        gameId = joinResult.getMessage();
        while(ClientModel.get_instance().getGames().get(gameId) == null){}

        JoinService.joinGame(otherId, gameId);
        JoinService.joinGame(userId, gameId);
        while(ClientModel.get_instance().getGames().get(gameId).getPlayersId().size() != 2){}
    }

    private void initToJoin() {
        new UtilityService().clearServer();

        //login
        otherPlayer = new User("username", "password");
        Result registerResult = new LoginService().register(otherPlayer);
        otherId = registerResult.getMessage();

        user = new User("username2", "password2"); //register auto logs in //so it resets user
        Result registerResult1 = new LoginService().register(user);
        userId = registerResult1.getMessage();

        //poller
        poller = new Poller(); //get it going
    }
}
