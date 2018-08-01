package ticket.com.tickettoridegames;

import org.junit.Test;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

public class LobbyServiceTest {
    private String gameId;
    private String otherId;
    private String userId;
    private User otherPlayer;
    private User user;
    private Poller poller;

    @Test
    public void testInitToLobby() {
        initToLobby();
    }

    @Test
    public void testStartGame() {
        initToLobby();

        LobbyService.startGame(gameId);
        while(!ClientModel.get_instance().isGameStarted(gameId)){}
    }

    private void initToLobby() {
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

        //join
        Result joinResult = JoinService.createGame(otherId, "gameName", 2);
        gameId = joinResult.getMessage();
        while(ClientModel.get_instance().getGames().get(gameId) == null){}

        JoinService.joinGame(otherId, gameId);
        JoinService.joinGame(userId, gameId);
        while(ClientModel.get_instance().getGames().get(gameId).getPlayersId().size() != 2){}
    }
}
