package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

public class LoginPresenter implements ILoginPresenter{

    private LoginService loginService;

    public LoginPresenter(){
        loginService = new LoginService();
    }

    /**
     * @description Validates input and then checks with the server if the username password combo it valid.
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Result login(String username, String password) {
        if (username == null || password == null) {
            return new Result(false,"","Invalid fields, please enter all data.");
        } else if (username.equals("") || password.equals("")) {
            return new Result(false,"","One of the fields was empty, please enter all data.");
        } else {
            return loginService.login(new User(username.trim(), password.trim()));
        }
    }

    /**
     * @description Validates the views data then prepares a response if it was not successful.
     *
     * @param username
     * @param password
     * @param passwordConfirm
     * @return
     */
    @Override
    public Result register(String username, String password, String passwordConfirm){

        if (username == null || password == null || passwordConfirm == null){
            return new Result(false, "","Invalid fields, please enter all data.");
        }
        else if (username.equals("") || password.equals("") || passwordConfirm.equals("")){
            return new Result(false,"","One of the fields was empty, please enter all data.");
        }
        else if (!password.equals(passwordConfirm)){
            return new Result(false,"","Passwords do not match, please try again.");
        }
        else{
            return loginService.register(new User(username.trim(), password.trim()));
        }
    }
}
