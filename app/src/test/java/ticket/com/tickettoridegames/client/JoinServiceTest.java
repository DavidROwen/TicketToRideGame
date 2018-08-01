package ticket.com.tickettoridegames.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class JoinServiceTest {

    @Before
    public void setup(){
        UtilityService utilityService = new UtilityService();

        // Clear the server tables
        Result result = utilityService.clearServer();
        // System.out.println(result.toString());
        assertTrue(result.isSuccess());
    }


    @Test
    public void testCreateJoinGame(){
        User user = new User("user", "password");

        // Client Login Service
        LoginService loginService = new LoginService();
        JoinService joinService = new JoinService();

        // Register user
        Result result = loginService.register(user);
        // System.out.println(result.toString());
        Assert.assertTrue(result.isSuccess());
        String userID = result.getMessage();

        result = JoinService.createGame(userID,"gamename", 2);
        // System.out.println(result.toString());
        assertTrue(result.isSuccess());
        String gameID = result.getMessage();

        result = JoinService.joinGame(userID, gameID);
        //System.out.println(result.toString());
        assertTrue(result.isSuccess());

        result = JoinService.joinGame(userID, "asdfasasf");
        //System.out.println(result.toString());
        assertFalse(result.isSuccess());

        result = JoinService.joinGame("asfasdfasdfasdf", gameID);
        //System.out.println(result.toString());
        assertFalse(result.isSuccess());
    }

}
