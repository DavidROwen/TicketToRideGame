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
import ticket.com.utility.model.Chat;

/**
 * LobbyActivity is the view that shows the players who have so far signed up and implements the live chat
 *
 * @invariant Server is running
 */
public class LobbyActivity extends Activity implements ILobbyView{


    /**
     * creates relevant lists used for chat and player list as well as corresponding adapters
     * attains corresponding presenter as well
     */
    // Variables
    private ILobbyPresenter presenter;
    private ArrayList<String> listItems=new ArrayList<>();     //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayAdapter<String> adapter;     //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayList<String> listChats=new ArrayList<>();     //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayAdapter<String> adapter2;     //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW

    /**
     * creates view objects that are used
     */
    // Widgets
    private EditText chat_input;

    /**
     * creates the lobby view using the lobby xml. inits adapters and buttons
     *
     * @pre isNull(savedInstanceState) == false
     * @post adapter != null
     * @post adapter2 != null
     * @param savedInstanceState a valid instance of the state
     */
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

        Button startButton = findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startGame();
            }
        });

        Button sendButton = findViewById(R.id.chat_send_button);
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

    /**
     * changes the view form lobby to game board
     *
     * @pre this.activity is LobbyActivity
     * @post this.activity = GamePlayActivity
     */
    @Override
    public void changeView(){
        Intent intent = new Intent(LobbyActivity.this, GamePlayActivity.class);
        startActivity(intent);
    }

    /**
     * Updates the list of joined players
     *
     * @pre players is a Set of String objects
     * @pre Strings represent valid players that correspond to a user
     * @post adapter.has() >= 1
     * @param players list of players who joined view specific game
     */
    @Override
    public void resetPlayers(Set<String> players){
        adapter.clear();
        for (String player:players) {
            adapter.add(player);
        }
    }

    /**
     * adds most recent player to game list
     *
     * @pre player is a string
     * @pre player represents a valid user
     * @post adapter >= 1
     * @param player a string representing a player
     */
    @Override
    public void addPlayerName(String player){
            adapter.add(player);
    }

    /**
     * adds the most recent chat to the chat list
     *
     * @pre chat is a Chat object
     * @pre Strings represent valid players that correspond to a user
     * @post adapter2.has() >= 1
     * @param chat most recent addition to the chatList
     */
    @Override
    public void displayChat(Chat chat){
        String message = chat.getUsername() + ": " + chat.getMessage();
        adapter2.add(message);
    }

    /**
     * Updates the list of sent chats
     *
     * @pre chat is a List of Chat objects
     * @post adapter2.has() >= 0
     * @param chats list of chats from all players
     */
    @Override
    public void setChat(List<Chat> chats){
        for (Chat chat:chats) {
            String message = chat.getUsername() + ": " + chat.getMessage();
            adapter2.add(message);
        }
    }

    /**
     * creates a toast for the user from the presenter
     *
     * @pre message is a String
     * @post toast appears
     * @param message a string the user is informed by
     */
    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(LobbyActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
