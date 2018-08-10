package ticket.com.server.server.DB.DAO;

import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.web.Command;

public class JsonCommandDAO implements ICommandDAO {

    private String filename;

    public JsonCommandDAO(String file){
        this.filename = file;
    }

    public Boolean addCommand(Command command){
        //placeholder
        return true;
    }


    public Command getCommand(String gameId){
        return null;
    }

    public void clearCommands(){

    }
}
