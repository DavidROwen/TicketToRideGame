package ticket.com.tickettoridegames.client.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.presenter.ILobbyPresenter;
import ticket.com.tickettoridegames.client.presenter.LobbyPresenter;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.Player;

public class LobbyActivity extends Activity implements ILobbyView{

    private ILobbyPresenter presenter;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayList<String> listItems=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayAdapter<String> adapter;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayList<String> listChats=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayAdapter<String> adapter2;

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
//        setPlayers(players);
        //################################testing purposes############################################
        addPlayerName(ClientModel.get_instance().getUser().getUsername());
    }

    // not necessary for phase 1
    //@Override
    //public void changeView(){
    //    Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
    //    startActivity(intent);
    //}

    @Override
    public void setPlayers(Set<String> players){
        for (String player:players) {
            adapter.add(player);
        }
    }

    @Override
    public void addPlayerName(String player){
            adapter.add(player);
    }

    @Override
    public void displayChat(String message){
        adapter2.add(message);
    }

    @Override
    public String sendChat(String message){
        return message;
    }

    @Override
    public void setChat(List<Chat> chats){
        for (Chat chat:chats) {
            adapter2.add(chat.getMessage());
        }
    }

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(LobbyActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
