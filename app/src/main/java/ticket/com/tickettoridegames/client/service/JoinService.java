package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class JoinService {
    public static final String CREATE_GAME_SERVICE_PATH = "ticket.com.server.server.service.CreateGameService";
    public static final String JOIN_GAME_SERVICE_PATH = "ticket.com.server.server.service.JoinService";
    private static ClientModel clientModel = ClientModel.get_instance();

    public JoinService(){
        clientModel = ClientModel.get_instance();
    }

    /**
     * Creates a new game that will be sent to the server.
     *
     *
     */
    public static Result createGame(String userId, String gameName, int numberOfPlayers){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(CREATE_GAME_SERVICE_PATH,
                            null, null,
                            "createGame",
                            new Class<?>[]{String.class, String.class, int.class},
                            new Object[]{userId, gameName, numberOfPlayers})
            );
            return result;
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
    public static Result joinGame(String userID, String gameID){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(JOIN_GAME_SERVICE_PATH,
                            null,
                            "join",
                            new Object[]{userID, gameID}
                            )
            );
            return result;
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
    public static void addGame(Game game){
        clientModel.addGameToList(game);
    }

    public static void updateGame(Game game){
        clientModel.updateGame(game);
    }

    public static void addPlayer(String gameID, Player player) {
        clientModel.addPlayerToGame(gameID,player);
    }
}
