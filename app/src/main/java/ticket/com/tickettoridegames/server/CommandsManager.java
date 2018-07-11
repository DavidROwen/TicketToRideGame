package ticket.com.tickettoridegames.server;

import java.util.PriorityQueue;

import ticket.com.tickettoridegames.utility.web.Command;

public class CommandsManager {
    private static CommandsManager instance;

    public static CommandsManager instance() {
        if(instance == null) { instance = new CommandsManager(); }
        return instance;
    }

    private CommandsManager() {
        this.commands = new PriorityQueue<>();
    }

    private PriorityQueue<Command> commands;

    public static PriorityQueue<Command> getCommands() { //todo divide them by person maybe
        PriorityQueue<Command> passedCommands = new PriorityQueue<>();
        copyCommandsOver(passedCommands);

        return passedCommands;
    }

    private static void copyCommandsOver(PriorityQueue<Command> passedCommands) {
        Command next;
        do {
            next = instance().commands.poll();
            if(next != null) { passedCommands.add(next); }
        }while(next != null);
    }

    public static void addCommand(Command command) {
        instance().commands.add(command);
    }

    public static void clear() {
        instance().commands.clear();
    }
}
