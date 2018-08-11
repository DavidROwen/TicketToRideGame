package ticket.com.sqldb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ticket.com.sqldb.SqlDbFactory;
import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Serializer;

public class SqlCommandDao implements ICommandDAO {

    private SqlDbFactory factory;

    public SqlCommandDao(IDbFactory factory){
        this.factory = (SqlDbFactory) factory;
    }

    @Override
    public Boolean addCommand(Command command){
        try {
            PreparedStatement stmt = null;
            try{
                stmt = factory.getConnection().prepareStatement("INSERT into command " +
                        "values ((?))");
                stmt.setString(1, Serializer.toJson(command));
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
    public List<Command> getAllCommands() {
        PreparedStatement stmt = null;
        List<Command> commands = new ArrayList<>();
        try {
            try {
                stmt = factory.getConnection().prepareStatement("SELECT * FROM command");
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()){
                    Command temp = (Command) Serializer.fromJson(resultSet.getString("command"), Command.class);
                    commands.add(temp);
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
        return commands;
    }

    @Override
    public Boolean clearCommands() {
        try {
            PreparedStatement stmt = null;
            try {
                stmt = factory.getConnection().prepareStatement("DELETE from command");
                stmt.executeUpdate();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
