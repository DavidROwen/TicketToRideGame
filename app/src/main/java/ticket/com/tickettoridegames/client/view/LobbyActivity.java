package ticket.com.tickettoridegames.client.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.ILobbyPresenter;
import ticket.com.tickettoridegames.client.presenter.LobbyPresenter;
import ticket.com.tickettoridegames.utility.model.Chat;

public class LobbyActivity extends Activity implements ILobbyView{

    // Variables
    private ILobbyPresenter presenter;
    private ArrayList<String> listItems=new ArrayList<>();     //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayAdapter<String> adapter;     //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayList<String> listChats=new ArrayList<>();     //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayAdapter<String> adapter2;     //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW

    // Widgets
    private EditText chat_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        presenter = new LobbyPresenter(this);

        ListView playerList = findViewById(R.id.listViewPlayers);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        playerList.setAdapter(adapter);

        ListView chatList = findViewById(R.id.listViewChat);
        adapter2=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listChats);
        chatList.setAdapter(adapter2);

        chat_input = findViewById(R.id.chat_input_msg);

        Button startButton = (Button) findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startGame();
            }
        });

        Button sendButton = (Button) findViewById(R.id.chat_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendMessage(chat_input.getText().toString());
            }
        });

        //################################testing purposes############################################
//        Set<String> players = new HashSet<>();
//        players.add("fred");
//
//        resetPlayers(players);
        //################################testing purposes############################################
    }

    @Override
    public void changeView(){
        Intent intent = new Intent(LobbyActivity.this, GamePlayActivity.class);
        startActivity(intent);
    }

    @Override
    public void resetPlayers(Set<String> players){
        adapter.clear();
        for (String player:players) {
            adapter.add(player);
        }
    }

    @Override
    public void addPlayerName(String player){
            adapter.add(player);
    }

    @Override
    public void displayChat(Chat chat){
        String message = chat.getUsername() + ": " + chat.getMessage();
        adapter2.add(message);
    }

    @Override
    public void setChat(List<Chat> chats){
        for (Chat chat:chats) {
            String message = chat.getUsername() + ": " + chat.getMessage();
            adapter2.add(message);
        }
    }

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(LobbyActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
