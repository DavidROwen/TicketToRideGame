package ticket.com.tickettoridegames.client.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.ILoginPresenter;
import ticket.com.tickettoridegames.client.presenter.LoginPresenter;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.tickettoridegames.utility.model.City;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Route;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private ILoginPresenter presenter;

    // UI references.
    private EditText loginPasswordEditText;
    private EditText registerPasswordEditText;
    private EditText usernameLoginEditText;
    private EditText usernameRegEditText;
    private EditText confirmPasswordText;
    private EditText urlTextInput;

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
        urlTextInput = findViewById(R.id.UrlTextInput);
        urlTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.setUrl(editable.toString());
            }
        });

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


        ////////////////////////////Testing////////////////////////////
        Game game = new Game();
        List<Route> playersRoutes = new ArrayList<>();
        playersRoutes.add(new Route("1", new City("SLC"), new City("Denver"),2,null));
        playersRoutes.add(new Route("2", new City("Omaha"), new City("KC"),2,null));
        playersRoutes.add(new Route("3", new City("Omaha"), new City("Chicago"),2,null));
        playersRoutes.add(new Route("4", new City("Miami"), new City("Charleston"),2,null));
        playersRoutes.add(new Route("5", new City("Toronto"), new City("Chicago"),2,null));
        playersRoutes.add(new Route("h6i", new City("Dallas"), new City("Houston"),2,null));
        playersRoutes.add(new Route("h7i", new City("Charleston"), new City("Atlanta"),2,null));
        playersRoutes.add(new Route("h8i", new City("Denver"), new City("SantaFe"),2,null));
        playersRoutes.add(new Route("h9i", new City("SantaFe"), new City("OklahomaCity"),2,null));
        playersRoutes.add(new Route("h0i", new City("SLC"), new City("LV"),2,null));
        playersRoutes.add(new Route("h99i", new City("KC"), new City("OklahomaCity"),2,null));

        ArrayList<ArrayList<String>> groupsResult = game.TestCompletedDestinationPoints(playersRoutes);
        String fred = "fred";
        //////////////////////////Testing/////////////////////////////
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
