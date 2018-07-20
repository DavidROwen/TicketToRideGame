package ticket.com.tickettoridegames.client.view.Fragments;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.tickettoridegames.client.view.JoinActivity;
import ticket.com.tickettoridegames.utility.model.Game;

public class AssetsFragment extends BasicFragment implements IAssetsView{

    //Widgets
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayList<String> listRoutes=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayAdapter<String> adapter;

    @Override
    public BasicFragment provideYourFragment() {

        return new AssetsFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.assets_fragment,parent,false);

        //Get your parent layout of fragment
        RelativeLayout layout = (RelativeLayout)view;

        //Now specific components here
        RecyclerView ownedtrains = (RecyclerView)getView().findViewById(R.id.ownedTrains);
        RecyclerView trainBank = (RecyclerView)getView().findViewById(R.id.trainBank);

        ListView routes = (ListView)getView().findViewById(R.id.routes);
        adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                listRoutes);
        routes.setAdapter(adapter);

        return view;
        //return null;
    }


}

//class adapter extends RecyclerView.Adapter<CustomViewHolder> {
//
//    int selected_position = 0; // You have to set this globally in the Adapter class
//    Map<String, Game> games;
//    String[] keySet; //ArrayList<String> maybe
//
//
//    public adapter(Map<String, Game> games) {
//        this.games = games;
//        this.keySet = games.keySet().toArray(new String[0]);
//    }
//
//    @Override
//    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View itemView = LayoutInflater.
//                from(viewGroup.getContext()).
//                inflate(R.layout.customlayout, viewGroup, false);
//
//        return new ticket.com.tickettoridegames.client.view.CustomViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ticket.com.tickettoridegames.client.view.CustomViewHolder holder, int i) {
//        holder.bindResult(games, keySet[i]);
//    }
//
//    @Override
//    public int getItemCount() {
//        return keySet.length;
//    }
//}
//
//class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//    protected TextView line1;
//
//    public CustomViewHolder(View v) {
//        super(v);
//        v.setOnClickListener(this);
//        line1 = (TextView)  v.findViewById(R.id.textView1);
//    }
//
//    public void bindResult(Map<String, Game> games, String key){
//        Game newGame = games.get(key);
//
//        line1.setText(newGame.getName());
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
//        v.setBackgroundColor(Color.GREEN);
//
//        //makes a hidden text that is read when button is clicked
//        line1 = (TextView)  v.findViewById(R.id.textView4);
//        JoinActivity.GameID.setText(line1.getText().toString());
//    }
//}
