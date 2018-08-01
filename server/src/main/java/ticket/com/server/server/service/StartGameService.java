package ticket.com.server.server.service;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.web.Result;

public class StartGameService {

    public static Result startGame(String gameId){
        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        try{
            if(sm.startGame(gameId)){
                result.setSuccess(true);
                result.setMessage("Starting Game");
            }
            else{
                result.setSuccess(false);
                result.setErrorMessage("Not enough players to start game");
            }
        }
        catch(Exception e){
            result.setSuccess(false);
            result.setErrorMessage("Error: Can't start game");
        }
        return result;
    }

}
