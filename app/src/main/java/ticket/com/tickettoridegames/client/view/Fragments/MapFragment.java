package ticket.com.tickettoridegames.client.view.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import ticket.com.tickettoridegames.R;

public class MapFragment extends BasicFragment implements IMapFragment {
    @Override
    public BasicFragment provideYourFragment() {

        return new MapFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment,parent,false);

        //Get your parent layout of fragment
        RelativeLayout layout = (RelativeLayout)view;

        //Now specific components here

        Button b1 = (Button)layout.findViewById(R.id.button1);
        Button b2 = (Button)layout.findViewById(R.id.button2);
        Button b3 = (Button)layout.findViewById(R.id.button3);

        return view;
    }


}