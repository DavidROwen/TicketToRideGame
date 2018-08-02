package ticket.com.tickettoridegames.client.view.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.AssetsPresenter;
import ticket.com.tickettoridegames.client.presenter.IAssetsPresenter;
import ticket.com.tickettoridegames.client.view.IAssetsView;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.TrainCard;

import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.BLACK;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.BLUE;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.GREEN;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.ORANGE;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.PINK;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.RED;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.WHITE;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.WILD;
import static ticket.com.utility.model.TrainCard.TRAIN_TYPE.YELLOW;

public class AssetsFragment extends BasicFragment implements IAssetsView{

    //Linked Presenter
    IAssetsPresenter presenter;

    //Variables
    private List<TrainCard> hand;
    private List<TrainCard> trainBank;
    private Set<DestinationCard> destinationCards;
    private ArrayList<String> listRoutes=new ArrayList<>(); //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayAdapter<String> adapter;                   //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private boolean created;

    //Widgets
    private RecyclerView myHandRecyclerView;
    private RecyclerView myBankRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private View view;
    private TextView trainDeckCount;
    private TextView routesDeckCount;

    public AssetsFragment(){
        created = false;
    }

    @Override
    public BasicFragment provideYourFragment() {
        return new AssetsFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.assets_fragment,parent,false);

        ListView routes = view.findViewById(R.id.routes);
        adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                listRoutes);
        routes.setAdapter(adapter);

        trainDeckCount = view.findViewById(R.id.trainBankCount);
        routesDeckCount = view.findViewById(R.id.routesDeckCount);

        if(!created) {
            presenter = new AssetsPresenter(this);
            created = true;
        }
        else{
            presenter.updateView();
        }
        return view;
        //return null;
    }

    @Override
    public void setHand(List<TrainCard> hand){
        hand.sort(TrainCard::compareTo);
        this.hand = hand;

        myHandRecyclerView = view.findViewById(R.id.ownedTrains);
        myHandRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myAdapter = new ImageAdapter(hand);
        myHandRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void setBank(List<TrainCard> trainBank){
        this.trainBank = trainBank;
        myBankRecyclerView = view.findViewById(R.id.trainBank);
        myBankRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myAdapter = new ImageAdapter(trainBank);
        myBankRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void setTrainDeckCount(Integer size){
        String text = "Cards in deck " + size.toString();
        trainDeckCount.setText(text);
    }

    @Override
    public void setRouteDeckCount(Integer size){
        String text = "Cards in deck: " + size.toString();
        routesDeckCount.setText(text);
    }

    @Override
    public TrainCard getBankChoice(TrainCard trainCard){
        return trainCard;
    }

    @Override
    public void pickupCard(Integer index) {
        presenter.pickupCard(index);
    }

    @Override
    public void setRoutes(Set<DestinationCard> destinationCards){
        adapter.clear();
        addRoute(destinationCards);
    }

    @Override
    public void addRoute(Set<DestinationCard> destinationCards) {
        for (DestinationCard destinationCard : destinationCards) {
            adapter.add(destinationCard.to_String());
        }
    }

    @Override
    public void displayMessage(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }


    class ImageAdapter extends RecyclerView.Adapter<ImageCustomViewHolder> {

        private int selected_position = 0; // You have to set this globally in the ImageAdapter class
        private List<TrainCard> trainCards;

        public ImageAdapter(List<TrainCard> trainCards) {
            this.trainCards = trainCards;
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
            holder.bindResult(trainCards.get(i));
        }

        @Override
        public int getItemCount() {
            return trainCards.size();
        }
    }

    class ImageCustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected ImageView train;

        public ImageCustomViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            train = v.findViewById(R.id.train);
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

            // if the player can draw then return a toast and say that you drew.
            v.setBackgroundColor(Color.GREEN);

            presenter.drawFromBank(getAdapterPosition());
        }
    }
}

