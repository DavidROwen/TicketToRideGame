package ticket.com.utility.db;

import ticket.com.utility.db.dao.ICommandDAO;
import ticket.com.utility.db.dao.IGameDAO;
import ticket.com.utility.db.dao.IUserDAO;

public interface IDbFactory {
    //Integer commandCount;

    IDbFactory getInstance();

    void startTransaction();

    void finishTransaction(Boolean commit);

    IGameDAO getGameDAO();

    IUserDAO getUserDAO();

    ICommandDAO getCommandDAO();

    void clear();
}
