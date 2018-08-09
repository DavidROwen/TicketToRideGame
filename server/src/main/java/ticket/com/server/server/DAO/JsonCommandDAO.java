package ticket.com.server.server.DAO;

import java.io.File;

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
