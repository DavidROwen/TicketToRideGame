package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

public class RegisterService {

    public Result register(String username, String password){

        System.out.println("Register request received: Username:"+username+" Password:"+password);

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        User newUser = new User(username, password);
        try{
            sm.addNewUser(newUser);
            result.setSuccess(true);
            result.setMessage(newUser.getId());
        }
        catch(Exception e){
            result.setSuccess(false);
            result.setErrorMessage("Username already in use");
        }

        return result;
    }
}
