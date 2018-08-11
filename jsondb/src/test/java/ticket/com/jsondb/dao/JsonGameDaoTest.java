package ticket.com.jsondb.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


import ticket.com.jsondb.JsonDbFactory;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.model.Game;

public class JsonGameDaoTest {

    IDbFactory factory;

    @Before
    public void setup(){
        factory = new JsonDbFactory();
    }

    @After
    public void tearDown(){
        IGameDAO gameDao = factory.getGameDAO();
        gameDao.clearGames();
    }

    @Test
    public void testAddUser(){
        Game game1 = new Game("test1", 3);
        game1.initGame();

        Game game2 = new Game("test2", 4);
        IGameDAO gameDao = factory.getGameDAO();

        gameDao.addGame(game1);
        gameDao.addGame(game2);

        Game found1 = gameDao.getGame(game1.getId());
        Game found2 = gameDao.getGame(game2.getId());

        assertTrue(found1 != null);
        assertTrue(found2 != null);
    }

    @Test
    public void testUpdateUser(){
        Game game1 = new Game("test1", 3);
        game1.initGame();

        Game game2 = new Game("test2", 4);
        IGameDAO gameDao = factory.getGameDAO();

        gameDao.addGame(game1);
        gameDao.addGame(game2);

        Game found1 = gameDao.getGame(game1.getId());
        Game found2 = gameDao.getGame(game2.getId());

        assertTrue(found1 != null);
        assertTrue(found2 != null);

        Game game3 = new Game("test3", 5);
        game3.initGame();

        boolean worked = gameDao.updateGame(game1.getId(), game3);

        assertTrue(worked);

        Game found3 = gameDao.getGame(game3.getId());
        assertTrue(found3 != null);
    }
}
