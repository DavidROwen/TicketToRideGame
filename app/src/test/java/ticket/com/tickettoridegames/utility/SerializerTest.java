package ticket.com.tickettoridegames.utility;

import org.junit.Test;

import ticket.com.utility.web.Command;
import ticket.com.utility.web.Serializer;

public class SerializerTest {
    @Test
    public void toAndFromPrimitives() {
        Integer exp = 4;
        String json = Serializer.toJson(new Command(Integer.class.getName(), "valueOf", new Class<?>[]{int.class}, new Object[]{4}));
        Command command = (Command) Serializer.fromJson(json, Command.class);
        Integer act = (Integer) command.execute();

        assert exp.equals(act);
    }
}
