package ticket.com.server.server.DAO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ticket.com.utility.model.Game;

public class JsonGameDAO implements  IGameDAO {

    private File file;

    public JsonGameDAO(File file){
        this.file = file;
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
