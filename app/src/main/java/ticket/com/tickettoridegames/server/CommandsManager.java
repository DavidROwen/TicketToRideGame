package ticket.com.tickettoridegames.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.Game;
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

    private static boolean isEmpty(String playerId){
        Queue<Command> queue = instance().commands.get(playerId);
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

    //gets the queue of commands for a given user, removing them in the process
    public static Queue<Command> getCommands(String playerId) {
        if(isEmpty(playerId)) { //doesn't break if empty
            //no commands to be sent, return null
            return new LinkedBlockingQueue<>();
        }
        else{
            Queue<Command> passedCommands = new LinkedBlockingQueue<>();
            copyCommandsOver(passedCommands, playerId);
            System.out.println("User: " + playerId + " Queue of " + passedCommands.size() + " commands send from manager");
            clear(playerId); //nothing to clear
            return passedCommands;
        }
    }

    //copies commands over to be passedCommands, removing them from commands
    private static void copyCommandsOver(Queue<Command> passedCommands, String playerId) {
        Command next;
        do {
            next = instance().commands.get(playerId).poll();
            if(next != null) { passedCommands.add(next); }
        }while(next != null);
    }

    //adds a command to a users queue. If the user does not have a queue yet it creates one
    public static void addCommand(Command command, String playerId) {
        LinkedBlockingQueue<Command> pq = instance().commands.get(playerId);
        if(pq == null){
            pq = new LinkedBlockingQueue<>();
            pq.add(command);
            instance().commands.put(playerId, pq);
        }
        else{
            pq.add(command);
        }
        System.out.println("Command added to queue of user: " + playerId + " Commands in queue: " + pq.size());
    }

    //adds a command to a users queue. If the user does not have a queue yet it creates one
    public static void addCommandAllPlayers(Command command, String gameId) {
        Game game = ServerModel.getInstance().getGameById(gameId);
        for(String curPlayerId : game.getPlayersId()) {
            addCommand(command, curPlayerId);
        }
    }

    //clear a users queue. used after sending a queue of commands.
    private static void clear(String playerId) {
        instance().commands.get(playerId).clear();
        System.out.println("User: " + playerId + "queue cleared");
    }

    private Map<String, LinkedBlockingQueue<Command>> commands;
}
