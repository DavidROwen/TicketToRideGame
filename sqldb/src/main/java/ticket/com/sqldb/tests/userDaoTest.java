package ticket.com.sqldb.tests;

import org.junit.After;
import org.junit.Test;

import java.util.List;

import ticket.com.sqldb.SqlDbFactory;
import ticket.com.utility.model.User;

import static junit.framework.TestCase.assertTrue;

public class userDaoTest {

    @After
    public void teardown(){
        SqlDbFactory factory = new SqlDbFactory();

        factory.startTransaction();
        boolean status = factory.getUserDAO().clearUsers();
        factory.finishTransaction(status);
    }

    @Test
    public void testAddGetUsers(){

        SqlDbFactory factory = new SqlDbFactory();

        factory.startTransaction();
        boolean status = factory.getUserDAO().clearUsers();
        factory.finishTransaction(status);

        assertTrue(status);

        User user1 = new User();
        user1.setId("1010101010");
        user1.setPassword("pass");
        user1.setUsername("user");

        factory.startTransaction();
        status = factory.getUserDAO().addUser(user1);
        factory.finishTransaction(status);

        assertTrue(status);

        factory.startTransaction();
        User user2 = factory.getUserDAO().getUser(user1.getUsername());
        factory.finishTransaction(true);

        assertTrue(user2.getUsername().equals(user1.getUsername()));
        assertTrue(user2.getPassword().equals(user1.getPassword()));
        assertTrue(user2.getId().equals(user1.getId()));

        factory.startTransaction();
        List<User> users = factory.getUserDAO().getAllUsers();
        factory.finishTransaction(true);

        assertTrue(users.size() == 1);

        factory.startTransaction();
        status = factory.getUserDAO().clearUsers();
        factory.finishTransaction(status);

        assertTrue(status);

        factory.startTransaction();
        users = factory.getUserDAO().getAllUsers();
        factory.finishTransaction(true);

        assertTrue(users.size() == 0);

    }
}
