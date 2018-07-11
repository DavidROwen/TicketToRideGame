package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

public class LoginService {

    public Result login(String username, String password){

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        User user = sm.getUserByName(username);
        if(user == null){
            result.setSuccess(false);
            result.setErrorMessage("Username not found");
        }
        else{
            if(user.getPassword().equals(password)){
                result.setSuccess(true);
                result.setMessage(user.getId());
            }
            else{
                result.setSuccess(false);
                result.setErrorMessage("Incorrect information");
            }
        }

        return result;
    }
}
