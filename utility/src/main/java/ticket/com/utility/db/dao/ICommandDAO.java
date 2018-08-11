package ticket.com.utility.db.dao;

import java.util.List;

import ticket.com.utility.web.Command;

public interface ICommandDAO {

    Boolean addCommand(Command command);

    List<Command> getAllCommands();

    Boolean clearCommands();
}
