package ticket.com.server.server.service;

import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.web.Command;
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

//        ServerModel.stStartGame(gameId);
        DatabaseManager.getInstance().addCommand(
                new Command(ServerModel.class.getName(), null, "stStartGame", new Object[]{gameId}),
                gameId
        );

        return result;
    }

    public static Result leaveGame(String gameId, String playerId){
        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();
        try{
            if(sm.removePlayerFromGame(gameId, playerId)){
                result.setSuccess(true);
                result.setMessage("Removed Player");
            }
            else{
                result.setSuccess(false);
                result.setErrorMessage("Error removing player");
            }
        }
        catch(Exception e){
            result.setSuccess(false);
            result.setErrorMessage(e.toString());
        }

//        ServerModel.stAddPlayerToGame(gameId, playerId);
        DatabaseManager.getInstance().addCommand(
                new Command(ServerModel.class.getName(), null, "stAddPlayerToGame", new Object[]{gameId, playerId}),
                gameId
        );

        return result;
    }

}
