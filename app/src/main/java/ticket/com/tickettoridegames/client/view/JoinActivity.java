package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.IJoinPresenter;
import ticket.com.tickettoridegames.client.presenter.JoinPresenter;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public class JoinActivity extends AppCompatActivity implements IJoinView{

    private IJoinPresenter presenter;

    // Widgets
    private Button createGameButton;
    private Switch privateGameButton;
    private Spinner playerNumber;
    private Spinner playerColor;

    // EditTexts
    private EditText gameNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        presenter = new JoinPresenter(this);

        createGameButton = findViewById(R.id.create_game_button);
        createGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.createGame(getNewGameName(), getNewPlayerCount(), getNewPlayerColor());
            }
        });

        privateGameButton = findViewById(R.id.private_game_switch);
        gameNameText = findViewById(R.id.game_name_input);

        playerNumber = findViewById(R.id.game_player_number);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.player_count, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerNumber.setAdapter(adapter);

        playerColor = findViewById(R.id.current_player_color);
        ArrayAdapter<CharSequence> color_adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        color_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerColor.setAdapter(color_adapter);

    }


    @Override
    public Map<String, Game> getGames(){
        return null;
    }

    @Override
    public void setGames(Map<String, Game> games){}

    @Override
    public void addGame(Game newGame){}

    @Override
    public List<Player> getPlayers(String gameID){
        return null;
    }

    @Override
    public void setPlayers(List<Player> players){}

    @Override
    public void setPlayerCount(String gameID){}

    @Override
    public String getNewGameName(){
        return gameNameText.getText().toString();
    }

    @Override
    public Integer getNewPlayerCount(){
        return (Integer) playerNumber.getSelectedItem();
    }

    @Override
    public String getNewPlayerColor(){
        return playerColor.getSelectedItem().toString();
    }
    // Join was successful go to the join view.
    @Override
    public void changeView(){}

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(JoinActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
