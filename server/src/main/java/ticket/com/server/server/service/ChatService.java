package ticket.com.server.server.service;

import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class ChatService {

    public static Result chat(String gameId, String playerId, String message){

        System.out.println("Chat request received: PlayerID:"+playerId+" GameID:"+gameId+" Message:"+message);

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();
        try{
            sm.addChatToGame(gameId, playerId, message);
            result.setSuccess(true);
            result.setMessage("Chat added");
        }
        catch(Exception e){
            result.setSuccess(false);
            result.setMessage("Error adding message to chat");
        }

//        ServerModel.stAddChatToGame(gameId, playerId, message);
        DatabaseManager.getInstance().addCommand(
                new Command(ServerModel.class.getName(), null, "stAddChatToGame", new Object[]{gameId, playerId, message}),
                gameId
        );
        return result;
    }

}
