package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class LoginService {
    public static final String LOGIN_GAME_SERVICE_PATH = "/Users/aaron/Documents/AndroidStudioProjects/TicketToRideGame/server/src/main/java/ticket/com/server/server/service/LoginService.java";
    public static final String REGISTER_GAME_SERVICE_PATH = "/Users/aaron/Documents/AndroidStudioProjects/TicketToRideGame/server/src/main/java/ticket/com/server/server/service/RegisterService.java";

    private ClientModel clientModel;

    public LoginService(){
        clientModel = ClientModel.get_instance();
    }

    public Result login(User user){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(LOGIN_GAME_SERVICE_PATH,
                            ticket.com.server.server.service.LoginService.class,
                            ticket.com.server.server.service.LoginService.class.newInstance(),
                            "login",
                            new Class<?>[]{String.class, String.class},
                            new Object[]{user.getUsername(), user.getPassword()})
            );
            if (result.isSuccess()){
                // Parse/get the user from the response here.
                // result.message should be set as the userID from the server.
                user.setId(result.getMessage());
                clientModel.setUser(user);
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }

    public Result register(User user){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(REGISTER_GAME_SERVICE_PATH,
                            ticket.com.server.server.service.RegisterService.class,
                            ticket.com.server.server.service.RegisterService.class.newInstance(),
                            "register",
                            new Class<?>[]{String.class, String.class},
                            new Object[]{user.getUsername(), user.getPassword()})
            );
            if (result.isSuccess()){
                // Parse/get the user from the response here.
                // result.message should be set as the userID from the server.
                user.setId(result.getMessage());
                clientModel.setUser(user);
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }

    public void setUrl(String url) {
        ServerProxy.setUrl(url);
    }
}
