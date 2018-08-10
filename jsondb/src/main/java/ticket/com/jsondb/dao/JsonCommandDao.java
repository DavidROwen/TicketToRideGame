package ticket.com.jsondb.dao;

import java.io.File;

import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.web.Command;

public class JsonCommandDao implements ICommandDAO {

    private File file;

    public JsonCommandDao(File file){
        this.file = file;
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
