package ticket.com.tickettoridegames.client.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.IJoinPresenter;
import ticket.com.tickettoridegames.client.presenter.JoinPresenter;
import ticket.com.tickettoridegames.utility.model.Game;

public class JoinActivity extends AppCompatActivity implements IJoinView{

    private IJoinPresenter presenter;
    Map<String, Game> games;

    // Widgets
    private Button createGameButton;
    private Button joinGameButton;
    private Switch privateGameButton;
    private Spinner playerNumber;
    private Spinner playerColor;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;

    // EditTexts
    private EditText gameNameText;
    public static TextView GameID;

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

        joinGameButton = findViewById(R.id.join_game_button);
        joinGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String string = GameID.getText().toString();
                presenter.joinGame(string);
            }
        });

        GameID = findViewById(R.id.textField);
        privateGameButton = findViewById(R.id.private_game_switch);
        gameNameText = findViewById(R.id.game_name_input);

        // Setup player number spinner
        playerNumber = findViewById(R.id.game_player_number);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerNumber.setAdapter(adapter);

        // Setup player color spinner
        playerColor = findViewById(R.id.current_player_color);
        ArrayAdapter<CharSequence> color_adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        color_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerColor.setAdapter(color_adapter);



//        //######################################testing purposes##########################################
//        Game one = new Game("one", 5);
//        Player One = new Player("test1", "111");
//        one.addPlayers(One);
//        Game two = new Game("test2", 3);
//        Player Two = new Player("sam", "222");
//        two.addPlayers(Two);
//        Game three = new Game("test3", 2);
//        Player Three = new Player("fred", "333");
//        three.addPlayers(Three);
//        games = new HashMap<String, Game>();
//        games.put(one.getId(),one);
//        games.put(two.getId(),two);
//        games.put(three.getId(),three);
//
//        setGames(games);
        //######################################testing purposes##########################################
    }


    @Override
    public void setGames(Map<String, Game> games){
        this.games = games;
        myRecyclerView = (RecyclerView) findViewById(R.id.myrecyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myAdapter = new adapter(games);
        myRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public String getChosenGame(String gameID){   //not quite sure how to call right now
        return gameID;
    }

    @Override
    public String getNewGameName(){
        return gameNameText.getText().toString();
    }

    @Override
    public Integer getNewPlayerCount(){
        String number = (String) playerNumber.getSelectedItem();
        return Integer.parseInt(number);
    }

    @Override
    public String getNewPlayerColor(){
        return playerColor.getSelectedItem().toString();
    }

    // Join was successful go to the join view.
    @Override
    public void changeView(Boolean isStarted){
        if (isStarted){
            Intent intent = new Intent(JoinActivity.this, GamePlayActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(JoinActivity.this, LobbyActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(JoinActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}

class adapter extends RecyclerView.Adapter<CustomViewHolder> {

    int selected_position = 0; // You have to set this globally in the Adapter class
    Map<String, Game> games;
    String[] keySet; //ArrayList<String> maybe


    public adapter(Map<String, Game> games) {
        this.games = games;
        this.keySet = games.keySet().toArray(new String[0]);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.customlayout, viewGroup, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int i) {
        holder.bindResult(games, keySet[i]);
    }

    @Override
    public int getItemCount() {
        return keySet.length;
    }
}

class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected TextView line1;
    protected TextView line2;
    protected TextView line3;
    protected TextView line4;

    public CustomViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        line1 = v.findViewById(R.id.textView1);
        line2 = v.findViewById(R.id.textView2);
        line3 = v.findViewById(R.id.textView3);
        line4 = v.findViewById(R.id.textView4);
    }

    public void bindResult(Map<String, Game> games, String key){
        Game newGame = games.get(key);

        line4.setText(newGame.getId());
        line1.setText(newGame.getName());
        line2.setText(newGame.getPlayerNamesString());
        line3.setText(newGame.getNumberOfPlayers() + "/" + newGame.getMaxPlayers());
    }

    @Override
    public void onClick(View v) {
        if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
        v.setBackgroundColor(Color.GREEN);

        //makes a hidden text that is read when button is clicked
        line1 = (TextView)  v.findViewById(R.id.textView4);
        JoinActivity.GameID.setText(line1.getText().toString());
    }
}
