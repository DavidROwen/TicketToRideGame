package ticket.com.tickettoridegames.client.web;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import ticket.com.tickettoridegames.utility.web.Command;

public class Poller {
    public static List<Object> poll() {
        PriorityQueue<Command> commands = ServerProxy.getCommands();
        return execute(commands);
    }

    private static List<Object> execute(PriorityQueue<Command> commands) {
        List<Object> results = new ArrayList<>();

        Command next;
        do {
            next = commands.poll();
            if(next != null) {
                Object result = next.execute();
                results.add(result);
            }
        }while(next != null);

        return results;
    }
}
