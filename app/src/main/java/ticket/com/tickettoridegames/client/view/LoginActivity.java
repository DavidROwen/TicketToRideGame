package ticket.com.tickettoridegames.client.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.ILoginPresenter;
import ticket.com.tickettoridegames.client.presenter.LoginPresenter;
import ticket.com.tickettoridegames.client.web.Poller;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private ILoginPresenter presenter;

    // UI references.
    private EditText loginPasswordEditText;
    private EditText registerPasswordEditText;
    private EditText usernameLoginEditText;
    private EditText usernameRegEditText;
    private EditText confirmPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
        Poller poller = new Poller();

        // Set up the login form.
        loginPasswordEditText = (EditText) findViewById(R.id.password);
        registerPasswordEditText = (EditText) findViewById(R.id.password2);
        usernameLoginEditText = (EditText) findViewById(R.id.userName);
        usernameRegEditText = (EditText) findViewById(R.id.userName2);
        confirmPasswordText = (EditText) findViewById(R.id.confirm);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    presenter.login(getLoginUserName(), getLoginPassword());
                    if (ClientModel.get_instance().getUser() != null) {
                        changeView();
                    }
                }
                catch (Exception e){
                    displayMessage(e.toString());
                }
            }
        });

        Button mRegisterInButton = (Button) findViewById(R.id.register_button);
        mRegisterInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    presenter.register(getRegisterUserName(), getRegisterPassword(), getRegisterConfirmation());
                    if (ClientModel.get_instance().getUser() != null){
                        changeView();
                    }
                }
                catch (Exception e){
                    displayMessage(e.toString());
                }
            }
        });
    }

    // Login or register was successful go to the join view.
    @Override
    public void changeView(){
        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(intent);
    }

    @Override
    public String getLoginUserName(){ return usernameLoginEditText.getText().toString();}

    @Override
    public String getRegisterUserName(){ return usernameRegEditText.getText().toString();}

    @Override
    public String getLoginPassword(){ return loginPasswordEditText.getText().toString();}

    @Override
    public String getRegisterPassword(){ return registerPasswordEditText.getText().toString();}

    @Override
    public String getRegisterConfirmation(){ return confirmPasswordText.getText().toString();}

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
