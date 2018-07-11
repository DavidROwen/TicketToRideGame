package ticket.com.tickettoridegames.server;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import ticket.com.tickettoridegames.utility.web.Command;

public class CommandsManager {
    private static CommandsManager instance;

    public static CommandsManager instance() {
        if(instance == null) { instance = new CommandsManager(); }
        return instance;
    }

    private CommandsManager() {
        this.commands = new HashMap<>();
    }

    private Map<String, PriorityQueue<Command>> commands;

    private static boolean isEmpty(String playerId){
        PriorityQueue<Command> queue = instance().commands.get(playerId);
        if(queue == null){
            //queue does not exist yet therefore is empty
            return true;
        }
        else{
            if(queue.isEmpty()){
                //queue is empty
                return true;
            }
            //queue is not empty
            return false;
        }
    }

    //gets the queue of commands for a given user
    public static PriorityQueue<Command> getCommands(String playerId) {
        if(isEmpty(playerId)) {
            //no commands to be sent, return null
            return null;
        }
        else{
            PriorityQueue<Command> passedCommands = new PriorityQueue<>();
            copyCommandsOver(passedCommands, playerId);
            clear(playerId);
            return passedCommands;
        }
    }

    //copies commands over to be sent. Makes it easier to clear a queue.
    private static void copyCommandsOver(PriorityQueue<Command> passedCommands, String playerId) {
        Command next;
        do {
            next = instance().commands.get(playerId).poll();
            if(next != null) { passedCommands.add(next); }
        }while(next != null);
    }

    //adds a command to a users queue. If the user does not have a queue yet it creates one
    public static void addCommand(Command command, String playerId) {
        PriorityQueue<Command> pq = instance().commands.get(playerId);
        if(pq == null){
            pq = new PriorityQueue<>();
            pq.add(command);
            instance().commands.put(playerId, pq);
        }
        else{
            pq.add(command);
        }
    }

    //clear a users queue. used after sending a queue of commands.
    private static void clear(String playerId) {
        instance().commands.get(playerId).clear();
    }
}
