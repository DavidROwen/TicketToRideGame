package ticket.com.server.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.model.Game;
import ticket.com.utility.web.Command;


/**
 * CommandsManager stores commands for each active user on the server, and sends them to each client when the poller quires it
 *
 * @invariant any Command object passed to a function corresponds to a given method command on the client
 */
public class CommandsManager {

    /**
     * private instance that creates a singleton pattern
     */
    private static CommandsManager instance;

    /**
     * private map the stores a queue of commands. A players ID is the key
     */
    private Map<String, LinkedBlockingQueue<Command>> commands;

    /**
     * Returns an instance of CommandsManager, if the instance is not created yet it calls the constructor
     * and returns the newly created instance
     *
     * @pre none
     * @post you now have a CommandsManager instance
     * @return CommandsManager instance
     */
    public static CommandsManager instance() {
        if(instance == null) { instance = new CommandsManager(); }
        return instance;
    }

    /**
     * a private constructor only called in the instance() method if the instance is currently null
     * (private functions so the pre conditions should always be fulfilled)
     *
     * @pre instance == null
     * @post Commands Manager object is now created
     */
    private CommandsManager() {
        this.commands = new HashMap<>();
    }

    /**
     * Checks if the queue for a given player is empty or not
     * (private functions so the pre conditions should always be fulfilled)
     *
     * @pre playerId is a type String
     * @post retval == true iff commands.get(playerId) != 0
     * @post the queues are unchanged
     * @param playerId the id tied to a player
     * @return true or false depending on if the queue is empty or not
     */
    private static boolean isEmpty(String playerId){
        Queue<Command> queue = instance().commands.get(playerId);
        if(queue == null){
            //queue does not exist yet therefore is empty
            return true;
        }
        else{
            return queue.isEmpty();
        }
    }

    /**
     * Returns the queue of commands that corresponds to a given playerId
     * players queue is cleared after commands are retrieved
     *
     * @pre playerId is of type String
     * @post retval == an empty queue iff isEmpty(playerId) == true
     * @post retval == a queue of commands iff isEmpty(playerId) == false
     * @post isEmpty(playerId) == true iff retval == a queue of commands
     * @post if a players queue had commands in it, they were sent to the client who polled them
     * @param playerId an id tied to a player/user
     * @return a queue of commands. Either an empty or filled queue given the id
     */
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

    /**
     * Copies a queue of commands over into a new queue to be sent to the client
     * (private function so the pre conditions should always be fulfilled)
     *
     * @pre isEmpty(playerId) == false
     * @pre passedCommands is of type Queue<Command> and is empty
     * @pre playerId is of String type
     * @post passedCommands now has all the commands that queue with the given playerId has
     * @param passedCommands an empty command queue
     * @param playerId an id corresponding to a user/player
     */
    private static void copyCommandsOver(Queue<Command> passedCommands, String playerId) {
        Command next;
        do {
            next = instance().commands.get(playerId).poll();
            if(next != null) { passedCommands.add(next); }
        }while(next != null);
    }

    /**
     * Adds a command to the queue of a specific player
     *
     * @pre playerId is of type String
     * @pre command is a valid Command
     * @post isEmpty(playerId) == false
     * @post commands.get(playerId).size() == old(commands.get(playerId).size()) + 1
     * @param command a created command
     * @param playerId an id corresponding to a user/player
     */
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

    /**
     * Adds a given command to the queues of all players in a particular game
     *
     * @pre gameId is a valid gameId tied to game on the server
     * @pre command is a valid command object
     * @post the command is the queue of each player in the game corresponding to the gameId
     * @post isEmpty(*each playerId in the game:gameId*) == false
     * @post commands.get(*each playerId in game:gameId*).size() == old(commands.get(*each player in game:gameId*).size() + 1
     * @param command a created client command
     * @param gameId an id corresponding to a given user/player
     */
    public static void addCommandAllPlayers(Command command, String gameId) {
        Game game = ServerModel.getInstance().getGameById(gameId);
        for(String curPlayerId : game.getPlayersId()) {
            addCommand(command, curPlayerId);
        }
    }

    /**
     * clears the queue of a given player
     * (private function so pre conditions should always be fulfilled)
     *
     * @pre playerId is of type String
     * @pre getCommands(playerId) was called prior
     * @post commands.get(playerId).size() == 0
     * @post isEmpty(playerId) == true;
     * @param playerId an id corresponding to a user/player
     */
    private static void clear(String playerId) {
        instance().commands.get(playerId).clear();
        System.out.println("User: " + playerId + "queue cleared");
    }
}
