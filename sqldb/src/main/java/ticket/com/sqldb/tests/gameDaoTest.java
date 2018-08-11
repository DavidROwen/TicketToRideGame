package ticket.com.sqldb.tests;

import org.junit.After;
import org.junit.Test;

import java.util.List;

import ticket.com.sqldb.SqlDbFactory;
import ticket.com.utility.model.Game;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class gameDaoTest {

    @After
    public void teardown(){
        SqlDbFactory factory = new SqlDbFactory();

        factory.startTransaction();
        boolean status = factory.getGameDAO().clearGames();
        factory.finishTransaction(status);
    }

    @Test
    public void testAddStoreGame(){

        SqlDbFactory factory = new SqlDbFactory();

        factory.startTransaction();
        boolean status = factory.getGameDAO().clearGames();
        factory.finishTransaction(status);

        assertTrue(status);

        Game game1 = new Game();
        game1.setId("idolo");
        game1.setName("its a game!");

        factory.startTransaction();
        status = factory.getGameDAO().addGame(game1);
        factory.finishTransaction(status);

        assertTrue(status);

        factory.startTransaction();
        Game game2 = factory.getGameDAO().getGame(game1.getId());
        factory.finishTransaction(true);

        assertNotNull(game2);
        assertTrue(game1.getId().equals(game2.getId()));
        assertTrue(game1.getName().equals(game2.getName()));

        factory.startTransaction();
        List<Game> games = factory.getGameDAO().getAllGames();
        factory.finishTransaction(true);

        assertNotNull(games);
        assertTrue(games.size() == 1);

        Game game3 = new Game();
        game3.setId(game1.getId());
        game3.setName("Its a new name!");
        factory.startTransaction();
        status = factory.getGameDAO().updateGame(game1.getId(), game3);
        factory.finishTransaction(status);

        assertTrue(status);

        factory.startTransaction();
        Game game4 = factory.getGameDAO().getGame(game1.getId());
        factory.finishTransaction(true);

        assertNotNull(game4);
        assertTrue(game4.getName().equals(game3.getName()));
    }

}
