package ticket.com.server.server.DB;

import ticket.com.server.server.DAO.ICommandDAO;
import ticket.com.server.server.DAO.IGameDAO;
import ticket.com.server.server.DAO.IUserDAO;
import ticket.com.server.server.DAO.JsonCommandDAO;
import ticket.com.server.server.DAO.JsonGameDAO;
import ticket.com.server.server.DAO.JsonUserDAO;

public class JsonDbFactory implements IDbFactory {

    private int commandCount;

    public JsonDbFactory(){
        commandCount = 0;
    }

    public void getInstance(){

    }
    public void startTransaction(){

    }
    public void finishTransaction(Boolean commit){

    }

    public IGameDAO getGameDAO(){
        return new JsonGameDAO();
    }

    public IUserDAO getUserDAO(){
        return new JsonUserDAO();
    }

    public ICommandDAO getCommandDAO(){
        return new JsonCommandDAO();
    }

    public void clearDB(){

    }

    private void openFile(String filename){

    }

    private void writeFile(String filename){

    }

    private void appendChange(String filename, String change){

    }
}
