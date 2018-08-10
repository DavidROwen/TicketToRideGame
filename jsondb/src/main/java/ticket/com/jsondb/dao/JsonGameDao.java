package ticket.com.jsondb.dao;

import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.model.Game;

public class JsonGameDao implements IGameDAO {
    private String filename;

    public JsonGameDao(String file){
        this.filename = file;
    }

    public Boolean addGame(Game game){
        //placeholder
        return true;
    }

    public Game getGame(String gameId){
        //placeholder
        return null;
    }

    public Boolean clearGames(){
        //placeholder
        return null;
    }

    public Boolean updateGame(String gameId, Game game){
        //placeholder
        return true;
    }
}
