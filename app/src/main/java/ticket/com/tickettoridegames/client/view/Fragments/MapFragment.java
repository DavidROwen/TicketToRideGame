package ticket.com.tickettoridegames.client.view.Fragments;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.tickettoridegames.client.presenter.MapPresenter;
import ticket.com.tickettoridegames.client.view.GamePlayActivity;
import ticket.com.tickettoridegames.client.view.IMapView;
import ticket.com.tickettoridegames.utility.model.City;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;

public class MapFragment extends BasicFragment implements IMapView{

    // Variables
    View view;
    private IMapPresenter presenter;
    private Button turnButton;
    private String userInput;
    private Route chosenRoute;

    @Override
    public BasicFragment provideYourFragment() {

        return new MapFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map_fragment,parent,false);
        presenter = new MapPresenter(this);

        //Init
        initTrianTracks();

        //Now specific components here
        Button drawTrainsButton = (Button)view.findViewById(R.id.button1);
        drawTrainsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.drawTrainCard();
            }
        });

        Button drawRoutesButton = (Button)view.findViewById(R.id.button5);
        drawRoutesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.drawDestinationCards();
            }
        });

        Button passOffButton = (Button)view.findViewById(R.id.pass_off_button);
        passOffButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.passOff();
            }
        });

        turnButton = (Button)view.findViewById(R.id.turn_button);
        turnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.changeTurn();
            }
        });

        Button placeTrainsButton = (Button)view.findViewById(R.id.button2);
        placeTrainsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.claimRoute(chosenRoute);
            }
        });

        return view;
    }

    public void enableTurn(){
        turnButton.setEnabled(true);
    }

    public void disableTurn(){
        turnButton.setEnabled(false);
    }

    public void displayDestinationCards(Set<DestinationCard> destinationCards){
        Object[] myArr = destinationCards.toArray();
        DestinationCard one = (DestinationCard) myArr[0];
        DestinationCard two = (DestinationCard) myArr[0];
        DestinationCard three = (DestinationCard) myArr[0];
        LinkedList<DestinationCard> returnCards = new LinkedList<>();
        LinkedList<DestinationCard> claimedCards = new LinkedList<>();

        final CharSequence[] items = {one.to_String(), two.to_String(), three.to_String()};
        final boolean [] selected = {false, false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Routes")
                .setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialogInterface, int item, boolean b) {
                        Log.d("Myactivity", String.format("%s: %s", items[item], b));
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (selected[0]){
                            returnCards.add(one);
                        }
                        else
                        {
                            claimedCards.add(one);
                        }

                        if (selected[1]){
                            returnCards.add(two);
                        }
                        else
                        {
                            claimedCards.add(two);
                        }

                        if (selected[2]){
                            returnCards.add(three);
                        }
                        else
                        {
                            claimedCards.add(three);
                        }

                        presenter.claimDestinationCard(claimedCards);
                        presenter.returnDestinationCard(returnCards);
                    }
                });

        builder.create().show();
    }

    public void placeTrains(Route route){

    }

    private void initTrianTracks(){
        Map<String, Route> trainTracks = new HashMap<>();

        Route trainTrack = new Route(new City("vancouver"), new City("calgary"), 3, TrainCard.TRAIN_TYPE.WILD);
        trainTracks.put("vancouver_calgary", trainTrack);
    }
}