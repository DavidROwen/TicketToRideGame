package ticket.com.server.server.service;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.model.Game;
import ticket.com.utility.web.Result;

public class CreateGameService {

    public static Result createGame(String userId, String gameName, int numberOfPlayers){

        System.out.println("Create game request received: UserID:"+userId+" GameName:"+gameName+" Number of Players:"+numberOfPlayers);

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        try {
            Game newGame = new Game(gameName,numberOfPlayers);
            sm.addNewGame(newGame, userId);
            result.setSuccess(true);
            result.setMessage(newGame.getId());
        }
        catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Error creating game. Exception:" + e.toString());
        }

        return result;
    }
}
