package ticket.com.tickettoridegames;

import org.junit.Test;

import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

import static org.junit.Assert.assertTrue;

public class clientLoginServiceTest {

    @Test
    public void testLogin(){
        User user = new User("user", "password");

        // Client Login Service
        LoginService loginService = new LoginService();

        Result result = loginService.register(user);
        System.out.println(result.toString());
        assertTrue(result.isSuccess());

        result = loginService.login(user);
        System.out.println(result.toString());
        assertTrue(result.isSuccess());
    }
}
