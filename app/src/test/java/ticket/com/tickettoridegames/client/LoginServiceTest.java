package ticket.com.tickettoridegames.client;

import org.junit.Test;

import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginServiceTest {

    @Test
    public void testLoginRegister(){
        User user = new User("user", "password");

        // Client Login Service
        LoginService loginService = new LoginService();
        UtilityService utilityService = new UtilityService();

        Result result = utilityService.clearServer();
        // System.out.println(result.toString());
        assertTrue(result.isSuccess());

        result = loginService.register(user);
        // System.out.println(result.toString());
        assertTrue(result.isSuccess());

        result = loginService.login(user);
        // System.out.println(result.toString());
        assertTrue(result.isSuccess());

        result = utilityService.clearServer();
        // System.out.println(result.toString());
        assertTrue(result.isSuccess());

        result = loginService.login(user);
        // System.out.println(result.toString());
        assertFalse(result.isSuccess());
    }
}
