package ticket.com.server.server.service;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.web.Result;

public class LoginService {

    public static Result login(String username, String password){
        System.out.println("Login request received: Username:" + username + " Password:" + password);

        Result result = new Result();
        ServerModel sm = ServerModel.getInstance();

        try {
            String userId = sm.loginUser(username, password);
            //todo update db
            if (userId == null) {
                result.setSuccess(false);
                result.setErrorMessage("Password incorrect");
            } else {
                result.setSuccess(true);
                result.setMessage(userId);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("User does not exist");
        }
        return result;
    }
}
