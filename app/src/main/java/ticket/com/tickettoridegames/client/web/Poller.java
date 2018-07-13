package ticket.com.tickettoridegames.client.web;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.utility.web.Command;

public class Poller {

    private static Integer POLL_INTERVAL_MS = 2000;
    private static Poller instance;

    public static Poller getInstance(){
        if (instance == null){
            instance = new Poller();
        }
        return instance;
    }


    public static void poll() {
        if (ClientModel.get_instance().getUser() != null) {
            String userId = ClientModel.get_instance().getUserId();
            Queue<Command> commands = ServerProxy.getCommands(userId);
            if (commands.size() > 0) {
                execute(commands);
            }
        }
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

    private Poller(){
        PollTask task = new PollTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object());
    }

    private static class PollTask extends AsyncTask<Object, Void, Object> {

        @Override
        protected void onPostExecute(Object o) {}

        @Override
        protected Object doInBackground(Object... obj) {
            while(true) {
                poll();
                try {
                    Thread.sleep(POLL_INTERVAL_MS); //todo test this
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
}
