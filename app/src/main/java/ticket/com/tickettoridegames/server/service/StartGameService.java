package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.web.Result;

public class StartGameService {

    public Result startGame(String gameId){
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
