package ticket.com.jsondb.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.web.Command;

public class JsonCommandDao implements ICommandDAO {

    private File file;
    private JsonDao helper;

    public JsonCommandDao(File file){
        this.file = file;
        helper = new JsonDao();
    }

    //BROKEN
    public Boolean addCommand(Command command){
        List<Command> commands;
        try {
            commands = (List<Command>) helper.getCurrentJsonCustom(file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        if(commands == null){
            commands = new ArrayList<>();
        }
        commands.add(command);
        String json = helper.toJsonCustom(commands);
        return helper.writeToFile(file, json);
    }

    public Command getCommand(String gameId){
        //hard to do unless we add a variable to the commands class
        return null;
    }

    public Boolean clearCommands(){
        return helper.clearFile(file);
    }
}
