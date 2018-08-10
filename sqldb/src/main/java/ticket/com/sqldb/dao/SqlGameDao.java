package ticket.com.sqldb.dao;

import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.model.Game;

public class SqlGameDao implements IGameDAO {

    private IDbFactory factory;

    public SqlGameDao(IDbFactory factory){}

    public Boolean addGame(Game game){
        return true;
    }

    public Game getGame(String gameId){
        return null;
    }

    public Boolean clearGames(){
        return true;
    }

    public Boolean updateGame(String gameId, Game game){
        return true;
    }
}
