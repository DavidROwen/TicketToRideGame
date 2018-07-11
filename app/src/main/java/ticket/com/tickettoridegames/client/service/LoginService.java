package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Result;

public class LoginService {

    public Result login(User user){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(ticket.com.tickettoridegames.server.service.LoginService.class.getName(),
                            ticket.com.tickettoridegames.server.service.LoginService.class,
                            ticket.com.tickettoridegames.server.service.LoginService.class.newInstance(),
                            "login",
                            new Class<?>[]{User.class},
                            new Object[]{user})
            );
            if (result.isSuccess()){
                ClientModel clientModel = ClientModel.get_instance();
                // Parse/get the user from the response here.
                clientModel.loginUser(new User("",""));
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

    public Result register(User user){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(ticket.com.tickettoridegames.server.service.RegisterService.class.getName(),
                            ticket.com.tickettoridegames.server.service.RegisterService.class,
                            ticket.com.tickettoridegames.server.service.RegisterService.class.newInstance(),
                            "register",
                            new Class<?>[]{User.class}, new Object[]{user})
            );
            if (result.isSuccess()){
                ClientModel clientModel = ClientModel.get_instance();
                // Parse/get the user from the response here.
                clientModel.loginUser(new User("",""));
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

}
