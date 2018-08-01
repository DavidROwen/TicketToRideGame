package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.utility.model.Chat;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

/**
 * This service is used by the LobbyPresenter to handle chat and start the games.
 */
public class LobbyService {
    public static final String START_GAME_SERVICE_PATH = "ticket.com.server.server.service.StartGameService";
    public static final String CHAT_GAME_SERVICE_PATH = "ticket.com.server.server.service.ChatService";


    public LobbyService(){}

    // Functions called by client
    /**
     * This function creates and sends a startGame command to the server.
     *
     * @param gameID represents a valid gameID for the current lobby
     * @return Result object that stores the status of the request and a related message
     *
     * @pre gameID != null
     * @pre gameID is for a real game
     * @pre the game with gameID is not already started
     *
     * @post return result is not null
     * @post return result has success set
     * @post if request was not successful result.success is false and result.errorMessage is set
     * @post if request was successful result.success if true and result.message is set
     */
    public static Result startGame(String gameID){
        try{
            Result  result = ServerProxy.sendCommand(
                    new Command(START_GAME_SERVICE_PATH,
                            null,
                            "startGame",
                            new Object[]{gameID}
                            )
            );
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return new Result(false,"",e.toString());
        }
    }

    /**
     * This function sends a message from the current user to all other users in the current lobby
     *
     * @param gameID represents a valid gameID for the current lobby
     * @param userID the userID for the current user and sender of the chat
     * @param message the message to send other players in the game
     * @return Result object that stores the status of the request and a related message
     *
     * @pre gameID != null
     * @pre gameID is for a real game
     * @pre the game with gameID is not already started
     * @pre userID != null
     * @pre userID is for a real user
     * @pre message != null
     * @pre message is not empty
     *
     * @post return result is not null
     * @post return result has success set
     * @post if request was not successful result.success is false and result.errorMessage is set
     * @post if request was successful result.success if true and result.message is set
     */
    public static Result sendChat(String gameID, String userID, String message){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(CHAT_GAME_SERVICE_PATH,
                            null,
                            "chat",
                            new Object[]{gameID,userID,message}
                            )
            );
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }

    // Functions called by server
    /**
     * This function updates the chat in the client model with chat from another user
     *
     * @param gameID The gameID of the game that the chat belongs in
     * @param chat a chat object including user and message
     *
     * @pre gameID != null
     * @pre gameID is for a real game
     * @pre chat is not null
     * @pre chat is a valid chat object
     * @pre chat has a username set
     * @pre chat has a message set
     *
     * @pre game.chatList contains the chat
     */
    public static void updateChat(String gameID, Chat chat){
        ClientModel.get_instance().addGameChat(gameID, chat);
    }

    /**
     * This function updates the games started status to setup a transition to the game view.
     *
     * @param gameId The gameID of the game that should start
     *
     * @pre gameID != null
     * @pre gameID is for a real game
     * @pre the game with gameID is not already started
     *
     * @post game.isStarted == true
     */
    public static void startingGame(String gameId){
        ClientModel.get_instance().startGame(gameId);
    }

}
