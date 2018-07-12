package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.web.Result;

public class CreateGameService {

    public Result createGame(String userId, String gameName, int numberOfPlayers){

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        Game newGame = new Game(gameName,numberOfPlayers);
        try {
            sm.addNewGame(newGame, userId);
            result.setSuccess(true);
            result.setMessage(newGame.getId());
        }
        catch (Exception e){
            result.setSuccess(false);
            result.setErrorMessage("Error creating game. Exception:" + e.toString());
        }

        return result;
    }
}
