package ticket.com.server.server.DAO;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ticket.com.server.server.DB.IDbFactory;
import ticket.com.server.server.DB.JsonDbFactory;
import ticket.com.utility.model.User;

public class JsonUserDAOTest {

    IDbFactory factory;

    @Before
    public void setup(){
        factory = new JsonDbFactory();
    }

    @After
    public void tearDown(){
        IUserDAO userDAO = factory.getUserDAO();
        userDAO.clearUsers();
    }

    @Test
    public void testAddUser(){
        User user1 = new User("Harold", "harry");
        User user2 = new User("Bob", "sagot");
        IUserDAO userDAO = factory.getUserDAO();
        userDAO.addUser(user1);
        userDAO.addUser(user2);

        User found1 = userDAO.getUser("Harold");
        assertTrue(found1 != null);
        User found2 = userDAO.getUser("Bob");
        assertTrue(found2 != null);
    }

}