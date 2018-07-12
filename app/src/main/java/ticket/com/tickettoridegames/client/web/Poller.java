package ticket.com.tickettoridegames.client.web;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.utility.web.Command;

public class Poller {
    public static List<Object> poll() {
        String userId = ClientModel.get_instance().getUserId();
        Queue<Command> commands = ServerProxy.getCommands(userId);
        return execute(commands);
    }

    private static List<Object> execute(Queue<Command> commands) {
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

    public static void main(String[] args) {
        while(true) {
            poll();
            try {
                Thread.sleep(POLL_INTERVAL_MS); //todo test this
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Integer POLL_INTERVAL_MS = 2000;
}
