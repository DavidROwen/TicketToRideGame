package ticket.com.tickettoridegames.client.web;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.utility.web.Command;

public class Poller {
    private PollTask pollerTask;

    public Poller(){
        pollerTask = new PollTask();
        pollerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object()); //starts doInBackground
    }

    public void stop() {
        pollerTask.cancel(true); //true may interrupt the task
    }

    private static Integer POLL_INTERVAL_MS = 2000;

    private static class PollTask extends AsyncTask<Object, Command, Void> {
        @Override
        protected void onProgressUpdate(Command... commands) { //executes on UI thread
            super.onProgressUpdate(commands);
            Queue<Command> queue = new LinkedList<>(Arrays.asList(commands)); //convert to queue
            Poller.execute(queue);
        }

        @Override
        protected Void doInBackground(Object... obj) {
            while(true) {
                if (isCancelled()) break;

                try {
                    Thread.sleep(POLL_INTERVAL_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<Command> commands = new ArrayList<>(poll()); //prepare to pass
                publishProgress(commands.toArray(new Command[commands.size()]));
            }

            return null;
        }
    }

    private static Queue<Command> poll() {
        if (ClientModel.get_instance().getUser() != null) {
            String userId = ClientModel.get_instance().getUserId();
            return ServerProxy.getCommands(userId);
        }
        else {
//            System.out.println("tried to poll but user still hasn't been defined");
            return new LinkedBlockingQueue<>();
        }
    }

    private static void execute(Queue<Command> commands) {
//        List<Object> results = new ArrayList<>();

        Command next;
        do {
            next = commands.poll();
            if(next != null) {
                Object result = next.execute();
//                results.add(result);
            }
        }while(next != null);

//        return results;
    }
}
