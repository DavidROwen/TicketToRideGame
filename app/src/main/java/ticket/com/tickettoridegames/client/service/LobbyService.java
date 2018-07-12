package ticket.com.tickettoridegames.client.service;

import android.app.Notification;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Result;

public class LobbyService {

    private ClientModel clientModel;

    public LobbyService(){
        clientModel = ClientModel.get_instance();
    }

    // Functions called by client
    public Result startGame(String gameID){
        return new Result(false,"","Placeholder ERROR");
    }

    public Result sendChat(String gameID, String userID, String message){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(ticket.com.tickettoridegames.server.service.ChatService.class.getName(),
                            ticket.com.tickettoridegames.server.service.ChatService.class,
                            ticket.com.tickettoridegames.server.service.ChatService.class.newInstance(),
                            "chat",
                            new Class<?>[]{String.class,String.class,String.class},
                            new Object[]{gameID,userID,message})
            );
            if (result.isSuccess()){
                return result;
            }
            else {
                // Error hmm what to do?
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }

//    public Result leaveGame(Player player) {
//        return new Result(false,"","Placeholder ERROR");
//    }

    // Functions called by server
    public void updateChat(String gameID, Chat chat){
        clientModel.addGameChat(gameID, chat);
    }

    public void addPlayer(String gameID, Player player) {
        clientModel.addPlayerToGame(gameID,player);
    }

    public void removePlayer(String gameID, Player player){
        clientModel.removePlayerFromGame(gameID,player);
    }

}
