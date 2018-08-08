package ticket.com.server.server.DB;

public interface IDbFactory {
    //Integer commandCount;

    void getInstance();
    void startTransaction();
    void finishTransaction(Boolean commit);
    //IGameDAO getGameDAO();
    //IUserDao getUserDAO();
    //ICommandDAO getCommandDAO();
}
