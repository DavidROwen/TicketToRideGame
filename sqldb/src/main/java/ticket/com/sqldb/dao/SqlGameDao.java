package ticket.com.sqldb.dao;

import java.util.List;

import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.model.Game;

public class SqlGameDao implements IGameDAO {

    private IDbFactory factory;

    public SqlGameDao(IDbFactory factory){
        this.factory = factory;
    }

    @Override
    public Boolean addGame(Game game){
        return true;
    }

    @Override
    public Game getGame(String gameId){
        return null;
    }

    @Override
    public List<Game> getAllGames() {
        return null;
    }

    @Override
    public Boolean clearGames(){
        return true;
    }

    @Override
    public Boolean updateGame(String gameId, Game game){
        return true;
    }
}
