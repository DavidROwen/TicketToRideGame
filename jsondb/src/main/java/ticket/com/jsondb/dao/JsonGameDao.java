package ticket.com.jsondb.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.model.Game;

public class JsonGameDao implements IGameDAO {

    private File file;
    private JsonDao helper;

    public JsonGameDao(File file){
        this.file = file;
        helper = new JsonDao();
    }

    public Boolean addGame(Game game){

        List<Game> games;
        try{
            games = helper.getCurrentJsonGame(file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        if(games == null){
            games = new ArrayList<>();
        }
        games.add(game);
        String json = helper.toJson(games);
        return helper.writeToFile(file, json);
    }

    public Game getGame(String gameId){

        List<Game> games = null;
        try{
            games = helper.getCurrentJsonGame(file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if(games == null){
            //error reading file
            return null;
        }
        for(Game game : games){
            if(game.getId().equals(gameId)){
                return game;
            }
        }
        //not found
        return null;
    }

    public List<Game> getAllGames(){

        List<Game> games = null;
        try{
            games = helper.getCurrentJsonGame(file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return games;
    }

    public Boolean clearGames(){
        return helper.clearFile(file);
    }

    public Boolean updateGame(String gameId, Game game){
        List<Game> games;
        try{
            games = helper.getCurrentJsonGame(file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        if(games == null){
            return false;
        }
        boolean removed = false;
        for(Game cgame : games){
            if(cgame.getId().equals(gameId)){
                removed = games.remove(cgame);
            }
        }
        if(removed){
            games.add(game);
            String json = helper.toJson(games);
            return helper.writeToFile(file, json);
        }
        else {
            return false;
        }
    }
}
