package ticket.com.tickettoridegames.client.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.ILobbyPresenter;
import ticket.com.tickettoridegames.client.presenter.LobbyPresenter;
import ticket.com.tickettoridegames.utility.model.Chat;

public class LobbyActivity extends ListActivity implements ILobbyView{

    private ILobbyPresenter presenter;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listChats=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        presenter = new LobbyPresenter(this);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        adapter2=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listChats);
        setListAdapter(adapter2);

        Button startButton = (Button) findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do stuff
            }
        });

        Button sendButton = (Button) findViewById(R.id.chat_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do stuff
            }
        });
    }

    @Override
    public void setPlayers(Set<String> players){
        for (String player:players) {
            adapter.add(player);
        }
    }

    @Override
    public void displayChat(String message){}

    @Override
    public void sendChat(String message){}

    @Override
    public void setChat(List<Chat> chats){

    }

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(LobbyActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
