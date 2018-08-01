package ticket.com.server.server.service;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

public class RegisterService {

    public static Result register(String username, String password){

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
