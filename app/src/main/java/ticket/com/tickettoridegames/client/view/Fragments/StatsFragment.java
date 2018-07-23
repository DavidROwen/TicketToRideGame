package ticket.com.tickettoridegames.client.view.Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.IStatsPresenter;
import ticket.com.tickettoridegames.client.presenter.StatsPresenter;
import ticket.com.tickettoridegames.client.view.GamePlayActivity;
import ticket.com.tickettoridegames.client.view.IStatsView;
import ticket.com.tickettoridegames.client.view.LoginActivity;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.PlayerAction;
import ticket.com.tickettoridegames.utility.model.PlayerStats;

public class StatsFragment extends BasicFragment implements IStatsView{

    //Variables
    private ArrayList<String> historyItems=new ArrayList<>();     //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayAdapter<String> historyAdapter;     //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayList<String> listChats=new ArrayList<>();     //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayAdapter<String> chatAdapter;     //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    List<PlayerStats> playerStats;
    List<PlayerAction> gameHistory;
    List<Chat> chats;
    View view;

    // Widgets
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private EditText chat_input;
    private EditText longest_train_player_input;

    private IStatsPresenter presenter;

    @Override
    public BasicFragment provideYourFragment() {
        return new StatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StatsPresenter(this);
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.stats_fragment,parent,false);

        //Now specific components here
        ListView historyList = view.findViewById(R.id.history);
        historyAdapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                historyItems);
        historyList.setAdapter(historyAdapter);

        ListView chatList = view.findViewById(R.id.listViewChat);
        chatAdapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                listChats);
        chatList.setAdapter(chatAdapter);

        chat_input = view.findViewById(R.id.chat_input_msg);

        Button chatButton = (Button)view.findViewById(R.id.chat_send_button);
        chatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.sendMessage(chat_input.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void displayChat(Chat chat){
        String message = chat.getUsername() + ": " + chat.getMessage();
        chatAdapter.add(message);
    }

    @Override
    public void setChat(List<Chat> chats){
        for (Chat chat:chats) {
            String message = chat.getUsername() + ": " + chat.getMessage();
            chatAdapter.add(message);
        }
    }

    @Override
    public void setHistory(List<PlayerAction> gameHistory){
        this.gameHistory = gameHistory;

        for (PlayerAction playerAction : gameHistory) {
            String message = playerAction.toString();
            historyAdapter.add(message);
        }
    }

    @Override
    public void displayHistory(PlayerAction pa){
        String history = pa.getUserName() + ": " + pa.getAction();
        historyAdapter.add(history);
    }

    @Override
    public void setPlayerStats(List<PlayerStats> playerStats){
        this.playerStats = playerStats;
        myRecyclerView = (RecyclerView) view.findViewById(R.id.ownedTrains);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myAdapter = new StatsAdapter(playerStats);
        myRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void setLongestTrainAward(String player){
        longest_train_player_input = (EditText) view.findViewById(R.id.playerNameText);
        longest_train_player_input.setText(player);
    }

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}

class StatsAdapter extends RecyclerView.Adapter<StatsCustomViewHolder> {

    int selected_position = 0; // You have to set this globally in the StatsAdapter class
    List<PlayerStats> playerStats;
    PlayerStats[] playerArray;


    public StatsAdapter(List<PlayerStats> playerStats) {
        this.playerStats = playerStats;
        this.playerArray = playerStats.toArray(new PlayerStats[0]);
    }

    @Override
    public StatsCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.custom_stats_layout, viewGroup, false);

        return new StatsCustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StatsCustomViewHolder holder, int i) {
        holder.bindResult(playerArray[i]);
    }

    @Override
    public int getItemCount() {
        return playerArray.length;
    }
}

class StatsCustomViewHolder extends RecyclerView.ViewHolder{
    protected TextView line1;
    protected TextView line2;
    protected TextView line3;
    protected TextView line4;
    protected TextView line5;

    public StatsCustomViewHolder(View v) {
        super(v);
        line1 = (TextView)  v.findViewById(R.id.textView1);
        line2 = (TextView)  v.findViewById(R.id.textView2);
        line3 = (TextView)  v.findViewById(R.id.textView3);
        line4 = (TextView)  v.findViewById(R.id.textView4);
        line5 = (TextView)  v.findViewById(R.id.textView5);
    }

    public void bindResult(PlayerStats playerStat){
        line1.setText(playerStat.getName());
        line2.setText(playerStat.getPoints());
        line3.setText(playerStat.getNumberOfPieces());
        line4.setText(playerStat.getNumberOfCards());
        line4.setText(playerStat.getNumberOfRoutes());
    }
}
