package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Result;

public class LobbyService {

    private static ClientModel clientModel;

    public LobbyService(){
        clientModel = ClientModel.get_instance();
    }

    // Functions called by client
    public static Result startGame(String gameID){
        try{
            Result  result = ServerProxy.sendCommand(
                    new Command(ticket.com.tickettoridegames.server.service.StartGameService.class.getName(),
                            ticket.com.tickettoridegames.server.service.StartGameService.class,
                            ticket.com.tickettoridegames.server.service.StartGameService.class.newInstance(),
                            "startGame",
                            new Class<?>[]{String.class},
                            new Object[]{gameID})
            );
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            return new Result(false,"",e.toString());
        }
    }

    public static Result sendChat(String gameID, String userID, String message){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(ticket.com.tickettoridegames.server.service.ChatService.class.getName(),
                            ticket.com.tickettoridegames.server.service.ChatService.class,
                            ticket.com.tickettoridegames.server.service.ChatService.class.newInstance(),
                            "chat",
                            new Class<?>[]{String.class,String.class,String.class},
                            new Object[]{gameID,userID,message})
            );
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }

//    public Result leaveGame(Player player) {
//        return new Result(false,"","Placeholder ERROR");
//    }

    // Functions called by server
    public static void updateChat(String gameID, Chat chat){
        ClientModel.get_instance().addGameChat(gameID, chat);
    }

    public static void removePlayer(String gameID, Player player){
        clientModel.removePlayerFromGame(gameID,player);
    }

    public static void startingGame(String gameId){
        ClientModel.get_instance().startGame(gameId);
    }

}
