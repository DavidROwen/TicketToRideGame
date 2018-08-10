package ticket.com.utility.db.dao;

import ticket.com.utility.model.User;

public interface IUserDAO {

    Boolean addUser(User user);

    User getUser(String username);

    void clearUsers();
}
