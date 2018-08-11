package ticket.com.utility.db.dao;

import java.util.List;

import ticket.com.utility.model.User;

public interface IUserDAO {

    Boolean addUser(User user);

    User getUser(String username);

    List<User> getAllUsers();

    Boolean clearUsers();
}
