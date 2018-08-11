package ticket.com.sqldb.tests;

import org.junit.After;
import org.junit.Test;

import java.util.List;

import ticket.com.sqldb.SqlDbFactory;
import ticket.com.utility.model.Game;
import ticket.com.utility.web.Command;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class commandDaoTest {

    @After
    public void teardown(){
        SqlDbFactory factory = new SqlDbFactory();

        factory.startTransaction();
        boolean status = factory.getCommandDAO().clearCommands();
        factory.finishTransaction(status);
    }

    @Test
    public void testAddGetCommands(){

        SqlDbFactory factory = new SqlDbFactory();

        factory.startTransaction();
        boolean status = factory.getCommandDAO().clearCommands();
        factory.finishTransaction(status);

        assertTrue(status);

//        Command command1 = new Command();
//        command1.setClassName("MyClass");
//        command1.setMethodName("thatThingYouDo");

        Command command1 = new Command(Game.class.getName(), null, "drawDestinationCards", new Object[]{});
//        Command comm2 = new Command(Game.class.getName(), null, "initGame", null);



        factory.startTransaction();
        status = factory.getCommandDAO().addCommand(command1);
        factory.finishTransaction(status);

        assertTrue(status);

        factory.startTransaction();
        List<Command> commands = factory.getCommandDAO().getAllCommands();
        factory.finishTransaction(true);

        assertNotNull(commands);
        assertTrue(commands.size() == 1);
        Command command2 = commands.get(0);
        assertTrue(command1.getClassName().equals(command2.getClassName()));
        assertTrue(command1.getMethodName().equals(command2.getMethodName()));
//        assertTrue(command1.getID().equals(command2.getID()));

        factory.startTransaction();
        status = factory.getCommandDAO().clearCommands();
        factory.finishTransaction(status);

        assertTrue(status);

        factory.startTransaction();
        commands = factory.getCommandDAO().getAllCommands();
        factory.finishTransaction(true);

        assertNotNull(commands);
        assertTrue(commands.size() == 0);
    }

}
