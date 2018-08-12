package ticket.com.server.server.service;

import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class JoinService {

    public static Result join(String userId, String gameId){

        System.out.println("Join game request received: UserID:"+userId+" GameID:"+gameId);

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

//        ServerModel.stAddPlayerToGame(gameId, userId);
        DatabaseManager.getInstance().addCommand(
                new Command(ServerModel.class.getName(), null, "stAddPlayerToGame", new Object[]{gameId, userId}),
                gameId
        );

        return result;
    }
}
