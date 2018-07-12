package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.ILoginPresenter;
import ticket.com.tickettoridegames.client.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
    }

    public String getLoginUserName(){ return "";}

    public String getRegisterUserName(){ return "";}

    public String getLoginPassword(){ return "";}

    public String getRegisterPassword(){ return "";}

    public String getRegisterConfirmation(){ return "";}

    public void displayMessage(String message){}

}
