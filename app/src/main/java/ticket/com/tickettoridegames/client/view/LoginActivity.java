package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.ILoginPresenter;
import ticket.com.tickettoridegames.client.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
    }

    // Login or register was successful go to the join view.
    @Override
    public void changeView(){}

    @Override
    public String getLoginUserName(){ return "";}

    @Override
    public String getRegisterUserName(){ return "";}

    @Override
    public String getLoginPassword(){ return "";}

    @Override
    public String getRegisterPassword(){ return "";}

    @Override
    public String getRegisterConfirmation(){ return "";}

    @Override
    public void displayMessage(String message){}

}
