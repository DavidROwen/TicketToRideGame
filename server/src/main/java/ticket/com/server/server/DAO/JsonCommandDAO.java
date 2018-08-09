package ticket.com.server.server.DAO;

import java.io.File;

import ticket.com.utility.web.Command;

public class JsonCommandDAO implements ICommandDAO {

    private File file;

    public JsonCommandDAO(File file){
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
