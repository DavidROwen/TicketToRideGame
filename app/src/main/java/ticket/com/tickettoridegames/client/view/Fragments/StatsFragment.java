package ticket.com.tickettoridegames.client.view.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.view.GamePlayActivity;
import ticket.com.tickettoridegames.client.view.IStatsView;
import ticket.com.tickettoridegames.client.view.LoginActivity;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.PlayerAction;
import ticket.com.tickettoridegames.utility.model.PlayerStats;

public class StatsFragment extends BasicFragment implements IStatsView{

    @Override
    public BasicFragment provideYourFragment() {

        return new StatsFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stats_fragment,parent,false);

        //Get your parent layout of fragment
        RelativeLayout layout = (RelativeLayout)view;

        //Now specific components here

        Button b2 = (Button)layout.findViewById(R.id.button2);

        return view;
    }

    @Override
    public void displayChat(Chat message){

    }

    @Override
    public String sendChat(String message){
        return null;
    }

    @Override
    public void setChat(List<Chat> chats){

    }

    @Override
    public void setHistory(List<PlayerAction> gameHistory){

    }

    @Override
    public void getPlayerStats(Set<PlayerStats> playerStats){

    }

    @Override
    public void getLongestTrainAward(String player){

    }

    @Override
    public void displayMessage(String message){

    }
}