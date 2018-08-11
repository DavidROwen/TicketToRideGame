package ticket.com.sqldb.dao;

import java.util.List;

import ticket.com.utility.db.IDbFactory;
import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.web.Command;

public class SqlCommandDao implements ICommandDAO {

    private IDbFactory factory;

    public SqlCommandDao(IDbFactory factory){
        this.factory = factory;
    }

    @Override
    public Boolean addCommand(Command command){
        return true;
    }

    @Override
    public Command getCommand(String gameId){
        return null;
    }

    @Override
    public List<Command> getAllCommands() {
        return null;
    }

    @Override
    public Boolean clearCommands(){
        return true;
    }

}
