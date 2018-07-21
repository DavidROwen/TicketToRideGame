package ticket.com.tickettoridegames.client.view.Fragments;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.view.GamePlayActivity;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.tickettoridegames.client.view.JoinActivity;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.TrainCard;

import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.BLACK;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.BLUE;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.GREEN;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.ORANGE;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.PINK;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.RED;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.WHITE;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.WILD;
import static ticket.com.tickettoridegames.utility.model.TrainCard.TRAIN_TYPE.YELLOW;

public class AssetsFragment extends BasicFragment implements IAssetsView{

    //Variables
    Set<TrainCard> hand;
    Set<TrainCard> trainBank;
    Set<DestinationCard> destinationCards;

    //Widgets
    private RecyclerView myHandRecyclerView;
    private RecyclerView myBankRecyclerView;
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
        LinearLayout layout = (LinearLayout)view;
        
        ListView routes = (ListView)layout.findViewById(R.id.routes);
        adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                listRoutes);
        routes.setAdapter(adapter);

        return view;
        //return null;
    }

    @Override
    public void setHand(Set<TrainCard> hand){
        this.hand = hand;
        myHandRecyclerView = (RecyclerView) getView().findViewById(R.id.ownedTrains);

        myHandRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myAdapter = new ImageAdapter(hand);
        myHandRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void setBank(Set<TrainCard> trainBank){
        this.trainBank = trainBank;

        myBankRecyclerView = (RecyclerView) getView().findViewById(R.id.trainBank);

        myBankRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myAdapter = new ImageAdapter(trainBank);
        myBankRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public TrainCard getBankChoice(TrainCard trainCard){
        return null;
    }

    @Override
    public void setRoutes(Set<DestinationCard> destinationCards){
        this.destinationCards = destinationCards;

        for (DestinationCard destinationCard:destinationCards) {
            String message = destinationCard.getLocation().getName() + "->" +
                    destinationCard.getLocation2().getName() + ": " + destinationCard.getValue().toString();
            adapter.add(message);
        }
    }

    @Override
    public void displayMessage(String message){

    }
}

class ImageAdapter extends RecyclerView.Adapter<ImageCustomViewHolder> {

    int selected_position = 0; // You have to set this globally in the ImageAdapter class
    Set<TrainCard> trainCardsSet;
    TrainCard[] trainCards; //ArrayList<String> maybe


    public ImageAdapter(Set<TrainCard> trainCards) {
        this.trainCardsSet = trainCardsSet;
        this.trainCards = trainCardsSet.toArray(new TrainCard[0]);
    }

    @Override
    public ImageCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.train_pic, viewGroup, false);

        return new ImageCustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageCustomViewHolder holder, int i) {
        holder.bindResult(trainCards[i]);
    }

    @Override
    public int getItemCount() {
        return trainCards.length;
    }
}

class ImageCustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected ImageView train;

    public ImageCustomViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        train = (ImageView)  v.findViewById(R.id.train);
    }

    public void bindResult(TrainCard trainCard){
        if(trainCard.getType() == BLUE) {
            train.setImageResource(R.drawable.blue);
        }else if(trainCard.getType() == BLACK){
            train.setImageResource(R.drawable.black);
        }else if(trainCard.getType() == PINK){
            train.setImageResource(R.drawable.pink);
        }else if(trainCard.getType() == RED){
            train.setImageResource(R.drawable.red);
        }else if(trainCard.getType() == ORANGE){
            train.setImageResource(R.drawable.orange);
        }else if(trainCard.getType() == YELLOW){
            train.setImageResource(R.drawable.yellow);
        }else if(trainCard.getType() == WHITE){
            train.setImageResource(R.drawable.white);
        }else if(trainCard.getType() == GREEN){
            train.setImageResource(R.drawable.green);
        }else if(trainCard.getType() == WILD){
            train.setImageResource(R.drawable.rainbow);
        }
    }

    @Override
    public void onClick(View v) {
        if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
        v.setBackgroundColor(Color.GREEN);

        //makes a hidden text that is read when button is clicked
        train = (ImageView)  v.findViewById(R.id.train);
        //GamePlayActivity.GameID.setText(line1.getText().toString());    //Game ID is textview where the info of touch is stored
    }
}
