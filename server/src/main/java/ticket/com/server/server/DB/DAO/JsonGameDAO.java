package ticket.com.server.server.DB.DAO;


import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.model.Game;

public class JsonGameDAO implements IGameDAO {

    private String filename;

    public JsonGameDAO(String file){
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
