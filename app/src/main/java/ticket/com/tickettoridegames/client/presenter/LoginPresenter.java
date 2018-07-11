package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.client.service.LoginService;

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
    public String login(String username, String password) {

        if (username == null || password == null) {
            return "Invalid fields, please enter all data.";
        } else if (username.equals("") || password.equals("")) {
            return "One of the fields was empty, please enter all data.";
        } else {
            //loginService.login(new User(username.trim(), password.trim()));
            return "PLACEHOLDER - Login Status";
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
    public String register(String username, String password, String passwordConfirm){

        if (username == null || password == null || passwordConfirm == null){
            return "Invalid fields, please enter all data.";
        }
        else if (username.equals("") || password.equals("") || passwordConfirm.equals("")){
            return "One of the fields was empty, please enter all data.";
        }
        else if (!password.equals(passwordConfirm)){
            return "Passwords do not match, please try again.";
        }
        else{
            // Call service with more details.
            //loginService.register(new User(username.trim(), password.trim()));
            return "PLACEHOLDER - Register Status";
        }
    }
}
