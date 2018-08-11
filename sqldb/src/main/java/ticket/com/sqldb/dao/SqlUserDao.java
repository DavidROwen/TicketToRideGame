package ticket.com.sqldb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ticket.com.sqldb.SqlDbFactory;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.IUserDAO;
import ticket.com.utility.model.User;

public class SqlUserDao implements IUserDAO {

    private SqlDbFactory factory;

    public SqlUserDao(IDbFactory factory){
        this.factory = (SqlDbFactory) factory;
    }

    @Override
    public Boolean addUser(User user){
        try {
            PreparedStatement stmt = null;
            try{
                stmt = factory.getConnection().prepareStatement("INSERT into user " +
                        "values ((?),(?),(?))");
                stmt.setString(1, user.getId());
                stmt.setString(2, user.getUsername());
                stmt.setString(3, user.getPassword());
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
    public User getUser(String username){
        PreparedStatement stmt = null;
        User temp = null;
        try {
            try {
                stmt = factory.getConnection().prepareStatement("SELECT * FROM user WHERE user_name= ?");
                stmt.setString(1, username);
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()){
                    temp = new User();
                    temp.setUsername(resultSet.getString("user_name"));
                    temp.setPassword(resultSet.getString("password"));
                    temp.setId(resultSet.getString("user_id"));
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
    public List<User> getAllUsers() {
        PreparedStatement stmt = null;
        List<User> users = new ArrayList<>();
        try {
            try {
                stmt = factory.getConnection().prepareStatement("SELECT * FROM user");
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()){
                    User temp = new User();
                    temp.setUsername(resultSet.getString("user_name"));
                    temp.setPassword(resultSet.getString("password"));
                    temp.setId(resultSet.getString("user_id"));
                    users.add(temp);
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
        return users;
    }

    @Override
    public Boolean clearUsers(){
        try {
            PreparedStatement stmt = null;
            try{
                stmt = factory.getConnection().prepareStatement("DELETE from user");
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
}
