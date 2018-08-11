package ticket.com.server.server;

import java.util.LinkedList;
import java.util.List;

import ticket.com.utility.web.Command;

public class MultiCommand {
    private List<Class<?>> returnTypes = new LinkedList<>();
    private List<Command> commands = new LinkedList<>();

    public MultiCommand(List<Command> commands, List<Class<?>> returnTypes) {
        if(!canUseCommands(commands, returnTypes)) {
            System.out.println("ERROR: Multicommand was not initialized");
            return;
        }
        this.commands = commands;
        this.returnTypes = returnTypes;
    }

    private boolean canUseCommands(List<Command> commands, List<Class<?>> returnTypes) {
        if(commands.size() != returnTypes.size()) {
            System.out.println("ERROR: commands and returnTypes have different sizes");
            return false;
        }
        return true;
    }

    //always adds prev commands result to next commands params
    //unless it's null
    public Object execute() {
        Object curReturn = null;
        for(int i = 0; i < commands.size(); i++) {
            curReturn = commands.get(i).execute();
            if(i+1 < commands.size()) { //not on last command
                //setup next command for execution
                commands.get(i + 1).setInstance(curReturn);
                commands.get(i + 1).setInstanceType(returnTypes.get(i));
            }
        }
        return curReturn;
    }
}
