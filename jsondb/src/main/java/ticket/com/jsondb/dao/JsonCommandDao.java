package ticket.com.jsondb.dao;

import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.web.Command;

public class JsonCommandDao implements ICommandDAO {

    private String filename;

    public JsonCommandDao(String file){
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
