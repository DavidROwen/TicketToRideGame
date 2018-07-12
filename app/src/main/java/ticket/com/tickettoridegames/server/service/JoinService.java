package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.web.Result;

public class JoinService {

    public Result join(String userId, String gameId){

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        try{
            if(sm.addPlayerToGame(userId, gameId)){
                result.setSuccess(true);
                result.setMessage("Added to Game");
            }
            else{
                result.setSuccess(false);
                result.setMessage("Max number of players reached");
            }
        }
        catch (Exception e){
            result.setSuccess(false);
            result.setErrorMessage("Failed to Join Game");
        }

        return result;
    }
}
