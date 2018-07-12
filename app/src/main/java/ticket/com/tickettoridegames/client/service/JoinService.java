package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Result;

public class JoinService {

    ClientModel clientModel;

    public JoinService(){
        clientModel = ClientModel.get_instance();
    }

    /**
     * Creates a new game that will be sent to the server.
     *
     *
     */
    public Result createGame(String userId, String gameName, int numberOfPlayers){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(ticket.com.tickettoridegames.server.service.CreateGameService.class.getName(),
                            ticket.com.tickettoridegames.server.service.CreateGameService.class,
                            ticket.com.tickettoridegames.server.service.CreateGameService.class.newInstance(),
                            "createGame",
                            new Class<?>[]{String.class, String.class, int.class},
                            new Object[]{userId, gameName, numberOfPlayers})
            );
            if (result.isSuccess()){
                return result;
            }
            else {
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }

    /**
     * Adds the given user to the selected game.
     *
     * @param gameID
     * @param userID
     */
    public Result joinGame(String userID, String gameID){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(ticket.com.tickettoridegames.server.service.JoinService.class.getName(),
                            ticket.com.tickettoridegames.server.service.JoinService.class,
                            ticket.com.tickettoridegames.server.service.JoinService.class.newInstance(),
                            "join",
                            new Class<?>[]{String.class, String.class,},
                            new Object[]{userID, gameID})
            );
            if (result.isSuccess()){
                return result;
            }
            else {
                // Request failed handle that here
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }


    /**
     * @description Called by server when anyone creates a new game.
     *
     * @param game the new game we are adding to the client model and view.
     */
    public void addGame(Game game){
        clientModel.addGameToList(game);
    }

    public void updateGame(Game game){
        clientModel.updateGame(game);
    }
}
