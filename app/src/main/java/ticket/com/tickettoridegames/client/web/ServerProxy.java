package ticket.com.tickettoridegames.client.web;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class ServerProxy {
    private static final String COMMAND_MANAGER_PATH = "ticket.com.server.server.CommandsManager";

    public static Result sendCommand(Command command) {
        final Type RESULTS_TYPE = new TypeToken<Result>(){}.getType();
        Object object = ClientCommunicator.send(command, RESULTS_TYPE);

        return object != null ? (Result) object : new Result(false, null, "client communicator failed");
    }

    public static Queue<Command> getCommands(String id) {
        //public static Queue<Command> getCommands(String playerId)
        Command command = new Command(COMMAND_MANAGER_PATH, null, "getCommands", new Object[]{id});

        final Type COMMANDS_TYPE = new TypeToken<Queue<Command>>(){}.getType();
        Object results =  ClientCommunicator.send(command, COMMANDS_TYPE);

        return results != null ? (Queue) results : new LinkedBlockingQueue<>(); //ignore error
    }

    public static void setUrl(String url) {
        ClientCommunicator.setUrl(url);
    }
}
