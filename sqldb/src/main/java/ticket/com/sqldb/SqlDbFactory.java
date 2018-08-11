package ticket.com.sqldb;

import java.sql.*;

import ticket.com.sqldb.dao.SqlCommandDao;
import ticket.com.sqldb.dao.SqlGameDao;
import ticket.com.sqldb.dao.SqlUserDao;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.db.dao.IUserDAO;

public class SqlDbFactory implements IDbFactory{

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IDbFactory getInstance(){

        return new SqlDbFactory();
    }

    public SqlDbFactory(){
        createTables();
    }

    private Connection conn;

    @Override
    public void startTransaction(){
        try {
            final String CONNECTION_URL = "jdbc:sqlite:tickettoride.sqlite";

            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public Connection getConnection(){
        return conn;
    }

    @Override
    public void finishTransaction(Boolean commit){
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void createTables(){
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists user");
                stmt.executeUpdate("drop table if exists game");
                stmt.executeUpdate("drop table if exists command");
                stmt.executeUpdate("create table user ( " +
                        "    user_id text NOT NULL UNIQUE, " +
                        "    user_name text NOT NULL UNIQUE, " +
                        "    password text NOT NULL )");
                stmt.executeUpdate("create table game ( " +
                        "    game_id text NOT NULL UNIQUE,\n" +
                        "    game blob NOT NULL )");
                stmt.executeUpdate("create table command (  " +
                        "    command text NOT NULL UNIQUE )");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public IGameDAO getGameDAO(){
        return new SqlGameDao(this);
    }

    @Override
    public IUserDAO getUserDAO(){
        return new SqlUserDao(this);
    }

    @Override
    public ICommandDAO getCommandDAO(){
        return new SqlCommandDao(this);
    }

    public static void main(String[] args) {
        SqlDbFactory db = new SqlDbFactory();

        db.startTransaction();
        db.createTables();
        db.finishTransaction(true);
    }

    @Override
    public void clear(){
        getCommandDAO().clearCommands();
        getGameDAO().clearGames();
        getUserDAO().clearUsers();
    }
}
