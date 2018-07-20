package ticket.com.tickettoridegames.client.view.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.view.IStatsView;

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


}