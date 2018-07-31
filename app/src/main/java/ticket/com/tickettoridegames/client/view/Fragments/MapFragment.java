package ticket.com.tickettoridegames.client.view.Fragments;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Integer red =  -65536;
    private Integer blue = -16776961;
    private Integer yellow = -256;
    private Integer green = -16711936;
    private Integer black =  -16777216;
    private IMapPresenter presenter;
    private Button turnButton;
    private Button drawTrainsButton;
    private Button drawRoutesButton;
    private Button placeTrainsButton;
    private Route chosenRoute;
    private String userInput;
    private Map<String, String> routeButtons = new HashMap<>(); //the key is the button, the value is the route name

    @Override
    public BasicFragment provideYourFragment() {
        return new MapFragment();
    }

    private void initButton(String buttonName) {
        FloatingActionButton button = view.findViewById(getResources().getIdentifier(buttonName, "id", getActivity().getPackageName()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(routeButtons.get(buttonName));
            }
        });
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map_fragment,parent,false);
        //presenter = new MapPresenter(this);

        //Init
        initTrianTracks();

        for(String buttonName : routeButtons.keySet()) {
            initButton(buttonName);
        }

        //Now specific components here
        drawTrainsButton = (Button)view.findViewById(R.id.button1);
        drawTrainsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.drawTrainCard();
            }
        });

        drawRoutesButton = (Button)view.findViewById(R.id.button5);
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

        placeTrainsButton = (Button)view.findViewById(R.id.button2);
        placeTrainsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //presenter.claimRoute(chosenRoute);
            }
        });
        presenter = new MapPresenter(this);
        return view;
    }

    public void enableTurn(){
        turnButton.setEnabled(true);
        placeTrainsButton.setEnabled(true);
        drawTrainsButton.setEnabled(true);
    }

    public void disableTurn(){
        turnButton.setEnabled(false);
        placeTrainsButton.setEnabled(false);
        drawTrainsButton.setEnabled(false);
    }

    public void displayDestinationCards(Set<DestinationCard> destinationCards){
        Object[] myArr = destinationCards.toArray();
        DestinationCard one = (DestinationCard) myArr[0];
        DestinationCard two = (DestinationCard) myArr[1];
        DestinationCard three = (DestinationCard) myArr[2];
        LinkedList<DestinationCard> claimedCards = new LinkedList<>();
        LinkedList<DestinationCard> discardedCards = new LinkedList<>();

        final CharSequence[] items = {one.to_String(), two.to_String(), three.to_String()};
        final boolean [] selected = {false, false, false};
//
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
                            claimedCards.add(one);
                        }
                        else
                        {
                            discardedCards.add(one);
                        }

                        if (selected[1]){
                            claimedCards.add(two);
                        }
                        else
                        {
                            discardedCards.add(two);
                        }

                        if (selected[2]){
                            claimedCards.add(three);
                        }
                        else
                        {
                            discardedCards.add(three);
                        }

                        presenter.setDestinationCards(claimedCards, discardedCards);
                    }
                });

        builder.create().show();
    }

    public void displayColorOptions(){

        final CharSequence[] items = {"Black", "Blue", "Green", "Orange", "Pink", "Red", "White", "Yellow", "Wild"};
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Colors");
        dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = items[item].toString();  //Selected item in listview

                if (selectedText == "Black"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.BLACK);
                }else if(selectedText == "Blue"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.BLUE);
                }else if(selectedText == "Green"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.GREEN);
                }else if(selectedText == "Orange"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.ORANGE);
                }else if(selectedText == "Pink"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.PINK);
                }else if(selectedText == "Red"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.RED);
                }else if(selectedText == "White"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.WHITE);
                }else if(selectedText == "Yellow"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.YELLOW);
                }else if(selectedText == "Wild"){
                    presenter.setColorChoice(TrainCard.TRAIN_TYPE.WILD);
                }
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public void placeTrains(Route route, TrainCard.TRAIN_TYPE color){
//        String routeName = (String) getKeyFromValue(trainTracks,route);
//        String buttonName = (String) getKeyFromValue(routeButtons,routeName);
//
//        if (color == TrainCard.TRAIN_TYPE.BLACK){
//            //buttonName.setBackgroundTintList(ColorStateList.valueOf(black));
//        }else if(color == TrainCard.TRAIN_TYPE.BLUE){
//            //buttonName.setBackgroundTintList(ColorStateList.valueOf(blue));
//        }else if(color == TrainCard.TRAIN_TYPE.YELLOW){
//            //buttonName.setBackgroundTintList(ColorStateList.valueOf(yellow));
//        }else if(color == TrainCard.TRAIN_TYPE.GREEN){
//            //buttonName.setBackgroundTintList(ColorStateList.valueOf(green));
//        }else if(color == TrainCard.TRAIN_TYPE.RED){
//            //buttonName.setBackgroundTintList(ColorStateList.valueOf(red));
//        }
    }

    public void placeTrains(Route route, Integer color){
        String buttonName = (String) getKeyFromValue(routeButtons,route.getName());

        FloatingActionButton button = view.findViewById(getResources().getIdentifier(buttonName, "id", getActivity().getPackageName()));
        button.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @Override
    public void setClaimedRoutes(List<Pair<Route, Integer>> routes) {
        for (Pair<Route, Integer> each : routes){
            placeTrains(each.first, each.second);
        }
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void disablePickRoutes(){
        drawRoutesButton.setEnabled(false);
    }

    //todo init names with gameMap so they always work
    private void initTrianTracks(){
        routeButtons.put("floatingActionButton13", "vancouver_calgary");
        routeButtons.put("floatingActionButton11", "vancouver_seattle_first");
        routeButtons.put("floatingActionButton12", "vancouver_seattle_second");
        routeButtons.put("floatingActionButton7", "seattle_portland_first");
        routeButtons.put("floatingActionButton8", "seattle_portland_second");
        routeButtons.put("floatingActionButton14", "seattle_calgary");
        routeButtons.put("floatingActionButton36", "calgary_helena");
        routeButtons.put("floatingActionButton10", "portland_sanFran_first");
        routeButtons.put("floatingActionButton9", "portland_sanFran_second");
        routeButtons.put("floatingActionButton34", "seattle_helena");
        routeButtons.put("floatingActionButton31", "portland_SLC");
        routeButtons.put("floatingActionButton18", "sanFran_SLC_first");
        routeButtons.put("floatingActionButton17", "sanFran_SLC_second");
        routeButtons.put("floatingActionButton15", "sanfran_LA_first");
        routeButtons.put("floatingActionButton16", "sanfran_LA_second");
        routeButtons.put("floatingActionButton22", "LA_lasVegas");
        routeButtons.put("floatingActionButton21", "lasVegas_SLC");
        routeButtons.put("floatingActionButton20", "LA_elPaso");
        routeButtons.put("floatingActionButton19", "LA_pheonix");
        routeButtons.put("floatingActionButton24", "pheonix_elPaso");
        routeButtons.put("floatingActionButton25", "pheonix_santaFe");
        routeButtons.put("floatingActionButton28", "elPaso_santaFe");
        routeButtons.put("floatingActionButton23", "pheonix_denver");
        routeButtons.put("floatingActionButton29", "santaFe_denver");
        routeButtons.put("floatingActionButton33", "SLC_denver_first");
        routeButtons.put("floatingActionButton32", "SLC_denver_second");
        routeButtons.put("floatingActionButton30", "helena_SLC");
        routeButtons.put("floatingActionButton39", "helena_denver");
        routeButtons.put("floatingActionButton35", "calgary_winnipeg");
        routeButtons.put("floatingActionButton42", "helena_winnipeg");
        routeButtons.put("floatingActionButton41", "helena_duleth");
        routeButtons.put("floatingActionButton40", "helena_omaha");
        routeButtons.put("floatingActionButton44", "denver_omaha");
        routeButtons.put("floatingActionButton73", "denver_KC_first");
        routeButtons.put("floatingActionButton70", "denver_KC_second");
        routeButtons.put("floatingActionButton38", "denver_oklahomaCity");
        routeButtons.put("floatingActionButton69", "santaFe_oklahomaCity");
        routeButtons.put("floatingActionButton27", "elPaso_oklahomaCity");
        routeButtons.put("floatingActionButton37", "elPaso_dallas");
        routeButtons.put("floatingActionButton26", "elPaso_houston");
        routeButtons.put("floatingActionButton45", "winnipeg_saultStMarie");
        routeButtons.put("floatingActionButton46", "winnipeg_duluth");
        routeButtons.put("floatingActionButton86", "duluth_saultStMarie");
        routeButtons.put("floatingActionButton85", "duluth_omaha_first");
        routeButtons.put("floatingActionButton84", "duluth_omaha_second");
        routeButtons.put("floatingActionButton75", "omaha_KC_first");
        routeButtons.put("floatingActionButton74", "omaha_KC_second");
        routeButtons.put("floatingActionButton51", "KC_oklahomaCity_first");
        routeButtons.put("floatingActionButton52", "KC_oklahomaCity_second");
        routeButtons.put("floatingActionButton48", "oklahomaCity_dallas_first");
        routeButtons.put("floatingActionButton47", "oklahomaCity_dallas_second");
        routeButtons.put("floatingActionButton49", "dallas_houston_first");
        routeButtons.put("floatingActionButton50", "dallas_houston_second");
        routeButtons.put("floatingActionButton43", "houston_newOrleans");
        routeButtons.put("floatingActionButton54", "dallas_littleRock");
        routeButtons.put("floatingActionButton53", "oklahoma_littleRock");
        routeButtons.put("floatingActionButton78", "KC_saintLouis_first");
        routeButtons.put("floatingActionButton76", "KC_saintLouis_second");
        routeButtons.put("floatingActionButton88", "omaha_chicago");
        routeButtons.put("floatingActionButton87", "duluth_chicago");
        routeButtons.put("floatingActionButton55", "newOrleans_littleRock");
        routeButtons.put("floatingActionButton68", "littleRock_saintLouis");
        routeButtons.put("floatingActionButton60", "newOrleans_atlanta_first");
        routeButtons.put("floatingActionButton59", "newOrleans_atlanta_second");
        routeButtons.put("floatingActionButton67", "littleRock_Nashville");
        routeButtons.put("floatingActionButton108", "saintLouis_nashville");
        routeButtons.put("floatingActionButton110", "nashville_atlanta");
        routeButtons.put("floatingActionButton79", "saintLouis_chicago_first");
        routeButtons.put("floatingActionButton80", "saintLouis_chicago_second");
        routeButtons.put("floatingActionButton91", "duluth_toronto");
        routeButtons.put("floatingActionButton95", "saultStMarie_montreal");
        routeButtons.put("floatingActionButton94", "saultStMarie_toronto");
        routeButtons.put("floatingActionButton93", "toronto_montreal");
        routeButtons.put("floatingActionButton92", "chicago_toronto");
        routeButtons.put("floatingActionButton109", "montreal_boston_first");
        routeButtons.put("floatingActionButton107", "montreal_boston_second");
        routeButtons.put("floatingActionButton83", "chicago_pittsburg_first");
        routeButtons.put("floatingActionButton82", "chicago_pittsburg_second");
        routeButtons.put("floatingActionButton81", "saintLouis_pittsburg");
        routeButtons.put("floatingActionButton96", "nashville_pittsburg");
        routeButtons.put("floatingActionButton66", "nashville_raleigh");
        routeButtons.put("floatingActionButton56", "newOrleans_miami");
        routeButtons.put("floatingActionButton57", "atlanta_miami");
        routeButtons.put("floatingActionButton61", "atlanta_charleston");
        routeButtons.put("floatingActionButton63", "atlanta_raleigh_first");
        routeButtons.put("floatingActionButton65", "atlanta_raleigh_second");
        routeButtons.put("floatingActionButton58", "miami_charleston");
        routeButtons.put("floatingActionButton62", "charleston_raleigh");
        routeButtons.put("floatingActionButton97", "raleigh_pittsburg");
        routeButtons.put("floatingActionButton99", "toronto_pittsburg");
        routeButtons.put("floatingActionButton89", "raleigh_washington_first");
        routeButtons.put("floatingActionButton90", "raleigh_washington_second");
        routeButtons.put("floatingActionButton98", "pittsburg_washington");
        routeButtons.put("floatingActionButton104", "washington_newYork_first");
        routeButtons.put("floatingActionButton103", "washington_newYork_second");
        routeButtons.put("floatingActionButton101", "pittsburg_newYork_first");
        routeButtons.put("floatingActionButton102", "pittsburg_newYork_second");
        routeButtons.put("floatingActionButton100", "montreal_newYork");
        routeButtons.put("floatingActionButton105", "newYork_boston_first");
        routeButtons.put("floatingActionButton106", "newYork_boston_second");
    }
}