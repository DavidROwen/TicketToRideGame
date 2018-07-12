package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

public class LoginService {

    public Result login(String username, String password){

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        try {
            String userId = sm.loginUser(username, password);
            if(userId == null){
                result.setSuccess(false);
                result.setErrorMessage("Password incorrect");
            }
            else{
                result.setSuccess(true);
                result.setMessage(userId);
            }
        }
        catch (Exception e){
            result.setSuccess(false);
            result.setErrorMessage("User does not exist");
        }
        return result;
    }
}
