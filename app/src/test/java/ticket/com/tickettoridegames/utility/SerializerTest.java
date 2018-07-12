package ticket.com.tickettoridegames.utility;

import org.junit.Test;

import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Serializer;

public class SerializerTest {

    @Test
    public void toAndFromPrimitives() {
        Integer exp = Integer.valueOf(4);
        String json = Serializer.toJson(new Command(Integer.class.getName(), null, null, "valueOf", new Class<?>[]{int.class}, new Object[]{4}));
        Command command = (Command) Serializer.fromJson(json, Command.class);
        Integer act = (Integer) command.execute();

        assert exp.equals(act);
    }

    private Class<?> getType(String className) {
        if(className.length() > 10) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            switch(className) {
                case "int":
                    return int.class;
                default:
                    return null;//assume primitive
            }
        }
    }
}
