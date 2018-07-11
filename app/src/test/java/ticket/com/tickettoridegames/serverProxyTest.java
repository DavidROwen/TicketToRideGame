package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.PriorityQueue;

import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.server.CommandsManager;
import ticket.com.tickettoridegames.server.service.CreateGameService;
import ticket.com.tickettoridegames.server.service.Tester;
import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Result;

public class serverProxyTest {
    @Test
    public void simpleSendTest() {
        Result result = ServerProxy.sendCommand(
                new Command(Tester.class, null, "add", new Object[]{3, 4})
        );

        assert result.isSuccess() && result.getMessage().equals("7");
    }

    @Test
    public void getCommandsTest() {

        Result result = ServerProxy.sendCommand(
                new Command(CommandsManager.class, CommandsManager.instance(), "addCommand",
                        new Object[]{new Command(Tester.class, null, "add", new Object[]{3, 4})}
                )
        );

        PriorityQueue<Command> commands = ServerProxy.getCommands();

        assert !commands.isEmpty() && commands.peek().getMethodName().equals("add");
    }

    @Test
    public void getEmptyCommands() {
        PriorityQueue<Command> commands = ServerProxy.getCommands();

        assert commands.isEmpty();
    }

    @Test
    public void mockCreateGame() {
        //public Result createGame(String gameName, int numberOfPlayers);
        Result result;
        try {
            result = ServerProxy.sendCommand(
                    new Command(CreateGameService.class.getName(), CreateGameService.class, CreateGameService.class.newInstance(), "createGame",
                            new Class<?>[]{String.class, int.class}, new Object[]{"game", 3})
            );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            result = null;
        }

        assert result != null && result.isSuccess();
    }
}
