package ticket.com.sqldb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ticket.com.sqldb.SqlDbFactory;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Serializer;

public class SqlGameDao implements IGameDAO {

    private SqlDbFactory factory;

    public SqlGameDao(IDbFactory factory){
        this.factory = (SqlDbFactory) factory;
    }

    @Override
    public Boolean addGame(Game game){
        try {
            PreparedStatement stmt = null;
            try{
                stmt = factory.getConnection().prepareStatement("INSERT into game " +
                        "values ((?),(?))");
                stmt.setString(1, game.getId());
                stmt.setString(2, Serializer.toJson(game));
                stmt.executeUpdate();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        System.out.println("SQL: added game to db "+game.toString());
        return true;
    }

    @Override
    public Game getGame(String gameId){
        PreparedStatement stmt = null;
        Game temp = null;
        try {
            try {
                stmt = factory.getConnection().prepareStatement("SELECT * FROM game WHERE game_id= ?");
                stmt.setString(1, gameId);
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()){
                    temp = (Game) Serializer.fromJson(resultSet.getString("game"), Game.class);
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return temp;
    }

    @Override
    public Boolean updateGame(String gameId, Game game){
        try {
            PreparedStatement stmt = null;
            try{
                stmt = factory.getConnection().prepareStatement("UPDATE game SET game=? WHERE game_id=?");
                stmt.setString(1, Serializer.toJson(game));
                stmt.setString(2, game.getId());
                stmt.executeUpdate();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Game> getAllGames() {
        PreparedStatement stmt = null;
        List<Game> games = new ArrayList<>();
        try {
            try {
                stmt = factory.getConnection().prepareStatement("SELECT * FROM game");
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()){
                    Game temp = (Game) Serializer.fromJson(resultSet.getString("game"), Game.class);
                    games.add(temp);
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return games;
    }

    @Override
    public Boolean clearGames(){
        try {
            PreparedStatement stmt = null;
            try{
                stmt = factory.getConnection().prepareStatement("DELETE from game");
                stmt.executeUpdate();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        System.out.println("SQL: all games cleared from db");
        return true;
    }
}
