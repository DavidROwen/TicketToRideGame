package ticket.com.tickettoridegames.client.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.ILoginPresenter;
import ticket.com.tickettoridegames.client.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private ILoginPresenter presenter;

    // UI references.
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private EditText nameText;
    private EditText nameText2;
    private EditText confirmPasswordText;
    private String loginName;
    private String registerName;
    private String loginPassword;
    private String registerPassword;
    private String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        // Set up the login form.
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    loginPassword = mPasswordView.getText().toString();
                    return true;
                }
                return false;
            }
        });

        mPasswordView2 = (EditText) findViewById(R.id.password2);
        mPasswordView2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    registerPassword = mPasswordView2.getText().toString();
                    return true;
                }
                return false;
            }
        });

        nameText = (EditText) findViewById(R.id.userName);
        nameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    loginName = nameText.getText().toString();
                    return true;
                }
                return false;
            }
        });

        nameText2 = (EditText) findViewById(R.id.userName2);
        nameText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    registerName = nameText2.getText().toString();
                    return true;
                }
                return false;
            }
        });

        confirmPasswordText = (EditText) findViewById(R.id.confirm);
        confirmPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    confirmPassword = confirmPasswordText.getText().toString();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.login(loginName, loginPassword);
            }
        });

        Button mRegisterInButton = (Button) findViewById(R.id.register_button);
        mRegisterInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.register(registerName, registerPassword, confirmPassword);
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
    public String getLoginUserName(){ return loginName;}

    @Override
    public String getRegisterUserName(){ return registerName;}

    @Override
    public String getLoginPassword(){ return loginPassword;}

    @Override
    public String getRegisterPassword(){ return registerPassword;}

    @Override
    public String getRegisterConfirmation(){ return confirmPassword;}

    @Override
    public void displayMessage(String message){
        String string = message;
        Toast toast = Toast.makeText(LoginActivity.this, string, Toast.LENGTH_LONG);
        toast.show();
    }
}
