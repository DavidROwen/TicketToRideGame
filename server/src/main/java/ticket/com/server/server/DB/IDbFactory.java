package ticket.com.server.server.DB;

import ticket.com.server.server.DAO.ICommandDAO;
import ticket.com.server.server.DAO.IGameDAO;
import ticket.com.server.server.DAO.IUserDAO;

public interface IDbFactory {
    //Integer commandCount;

    void getInstance();
    void startTransaction();
    void finishTransaction(Boolean commit);
    IGameDAO getGameDAO();
    IUserDAO getUserDAO();
    ICommandDAO getCommandDAO();
}
