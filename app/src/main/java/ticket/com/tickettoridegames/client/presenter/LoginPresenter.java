package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.view.ILobbyView;
import ticket.com.tickettoridegames.client.view.ILoginView;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

public class LoginPresenter implements ILoginPresenter, Observer{

    private LoginService loginService;
    private ClientModel clientModel;
    private ILoginView loginView;

    public LoginPresenter(ILoginView view){
        loginView = view;
        loginService = new LoginService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);
    }

    /**
     * @description Validates input and then checks with the server if the username password combo is valid.
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public void login(String username, String password) {
        if (username == null || password == null) {
            loginView.displayMessage("Fields was null, please enter all data.");
        } else if (username.isEmpty() || password.isEmpty()) {
            loginView.displayMessage("One of the fields was empty, please enter all data.");
        } else {
            Result result = loginService.login(new User(username.trim(), password.trim()));
            if (!result.isSuccess()){
                loginView.displayMessage(result.getErrorMessage());
            }
        }
    }

    /**
     * @description Validates the views data then sends a message if it was not successful.
     *
     * @param username
     * @param password
     * @param passwordConfirm
     * @return
     */
    @Override
    public void register(String username, String password, String passwordConfirm){

        if (username == null || password == null || passwordConfirm == null){
            loginView.displayMessage("Invalid fields, please enter all data.");
        }
        else if (username.equals("") || password.equals("") || passwordConfirm.equals("")){
            loginView.displayMessage("One of the fields was empty, please enter all data.");
        }
        else if (!password.equals(passwordConfirm)){
            loginView.displayMessage("Passwords do not match, please try again.");
        }
        else{
            Result result = loginService.register(new User(username.trim(), password.trim()));
            if (!result.isSuccess()){
                loginView.displayMessage(result.getErrorMessage());
            }
        }
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        // update view here
        // Should fire on login success then we will notify the view to change to the join view.
//        if (clientModel.getUser() != null) {
//            loginView.changeView();
//        }
    }
}
