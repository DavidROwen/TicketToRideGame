package ticket.com.server.server.DAO;

import ticket.com.utility.model.User;

public interface IUserDAO {

    Boolean addUser(User user);

    User getUser(String playerId);

    void clearUsers();
}
