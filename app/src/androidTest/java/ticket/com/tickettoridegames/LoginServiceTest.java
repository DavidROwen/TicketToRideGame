package ticket.com.tickettoridegames;

import org.junit.Test;

import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.utility.utility.model.User;
import ticket.com.utility.web.Result;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LoginServiceTest {
    @Test
    public void testRegisterNormal() {
        User user = new User("user", "password");

        try {
            //prepare server
            Result result = UtilityService.class.newInstance().clearServer();
            assertTrue(result.isSuccess());

            //try new user
            Result firstTry = LoginService.class.newInstance().register(user);
            assertTrue(firstTry.isSuccess());

            //try already registered user
            Result secondTry = LoginService.class.newInstance().register(user);
            assertFalse(secondTry.isSuccess());
        } catch (InstantiationException e) {
            assert false;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            assert false;
            e.printStackTrace();
        }
    }
}
