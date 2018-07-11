package ticket.com.tickettoridegames.client.web;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.PriorityQueue;

import ticket.com.tickettoridegames.server.CommandsManager;
import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Result;

public class ServerProxy {
    public static Result sendCommand(Command command) {
        final Type RESULTS_TYPE = new TypeToken<Result>(){}.getType();
        Object object = ClientCommunicator.send(command, RESULTS_TYPE);

        return object != null ? (Result) object : new Result(false, null, "client communicator failed");
    }

    public static PriorityQueue<Command> getCommands() {
        Command command = new Command(CommandsManager.class, null, "getCommands", null);

        final Type COMMANDS_TYPE = new TypeToken<PriorityQueue<Command>>(){}.getType();
        Object results =  ClientCommunicator.send(command, COMMANDS_TYPE);

        return results != null ? (PriorityQueue) results : new PriorityQueue<>(); //ignore error
    }
}
