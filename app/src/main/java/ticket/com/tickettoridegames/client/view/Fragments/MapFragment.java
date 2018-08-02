package ticket.com.tickettoridegames.client.view.Fragments;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.IMapPresenter;
import ticket.com.tickettoridegames.client.presenter.MapPresenter;
import ticket.com.tickettoridegames.client.view.IMapView;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Pair;
import ticket.com.utility.model.Route;
import ticket.com.utility.model.TrainCard;

public class MapFragment extends BasicFragment implements IMapView{

    // Variables
    View view;

    private IMapPresenter presenter;
    private boolean created;
    private boolean firstCall;

    private Button turnButton;
    private Button drawTrainsButton;
    private Button drawRoutesButton;
    private Button placeTrainsButton;

    private Map<String, String> buttonToRouteConversion = new HashMap<>(); //the key is the button, the value is the route NAME

    public MapFragment(){
        created = false;
        firstCall = true;
    }

    @Override
    public BasicFragment provideYourFragment() {
        return new MapFragment();
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map_fragment,parent,false);
        initButtonToRouteConversion();

        for(String buttonName : buttonToRouteConversion.keySet()) {
            initButton(buttonName);
        }

        //Now specific components here
        drawTrainsButton = view.findViewById(R.id.button1);
        drawTrainsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.drawTrainCard();
            }
        });

        drawRoutesButton = view.findViewById(R.id.pass_off_button);
        drawRoutesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.drawDestinationCards();
            }
        });

//        Button passOffButton = view.findViewById(R.id.pass_off_button);
//        passOffButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                presenter.passOff();
//            }
//        });

//        turnButton = view.findViewById(R.id.turn_button);
//        turnButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                presenter.changeTurn();
//            }
//        });

        if(!created){
            created = true;
            presenter = new MapPresenter(this);
        }
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

    public void setFirstCall(boolean val){
        firstCall = val;
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
        builder.setCancelable(false);
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

                        presenter.setDestinationCards(claimedCards, discardedCards, firstCall);
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
        String buttonName = (String) getKeyFromValue(buttonToRouteConversion,route.NAME);

        FloatingActionButton button = view.findViewById(getResources().getIdentifier(buttonName, "id", getActivity().getPackageName()));
        button.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @Override
    public void setClaimedRoutes(List<Pair<Route, Integer>> routes) {
        for (Pair<Route, Integer> each : routes){
            claimRoute(each.first, each.second);
        }
    }

    @Override
    public void claimRoute(Route route, Integer color) {
        placeTrains(route, color);
    } //todo rename placetrains

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

    private void initButton(String buttonName) {
        FloatingActionButton button = view.findViewById(getResources().getIdentifier(buttonName, "id", getActivity().getPackageName()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(buttonToRouteConversion.get(buttonName));
            }
        });
    }

    //todo init names with gameMap so they always work
    private void initButtonToRouteConversion(){
        buttonToRouteConversion.put("floatingActionButton13", "vancouver_calgary");
        buttonToRouteConversion.put("floatingActionButton11", "vancouver_seattle_first");
        buttonToRouteConversion.put("floatingActionButton12", "vancouver_seattle_second");
        buttonToRouteConversion.put("floatingActionButton7", "seattle_portland_first");
        buttonToRouteConversion.put("floatingActionButton8", "seattle_portland_second");
        buttonToRouteConversion.put("floatingActionButton14", "seattle_calgary");
        buttonToRouteConversion.put("floatingActionButton36", "calgary_helena");
        buttonToRouteConversion.put("floatingActionButton10", "portland_sanFran_first");
        buttonToRouteConversion.put("floatingActionButton9", "portland_sanFran_second");
        buttonToRouteConversion.put("floatingActionButton34", "seattle_helena");
        buttonToRouteConversion.put("floatingActionButton31", "portland_SLC");
        buttonToRouteConversion.put("floatingActionButton18", "sanFran_SLC_first");
        buttonToRouteConversion.put("floatingActionButton17", "sanFran_SLC_second");
        buttonToRouteConversion.put("floatingActionButton15", "sanFran_LA_first");
        buttonToRouteConversion.put("floatingActionButton16", "sanFran_LA_second");
        buttonToRouteConversion.put("floatingActionButton22", "LA_lasVegas");
        buttonToRouteConversion.put("floatingActionButton21", "lasVegas_SLC");
        buttonToRouteConversion.put("floatingActionButton20", "LA_elPaso");
        buttonToRouteConversion.put("floatingActionButton19", "LA_pheonix");
        buttonToRouteConversion.put("floatingActionButton24", "pheonix_elPaso");
        buttonToRouteConversion.put("floatingActionButton25", "pheonix_santaFe");
        buttonToRouteConversion.put("floatingActionButton28", "elPaso_santaFe");
        buttonToRouteConversion.put("floatingActionButton23", "pheonix_denver");
        buttonToRouteConversion.put("floatingActionButton29", "santaFe_denver");
        buttonToRouteConversion.put("floatingActionButton33", "SLC_denver_first");
        buttonToRouteConversion.put("floatingActionButton32", "SLC_denver_second");
        buttonToRouteConversion.put("floatingActionButton30", "helena_SLC");
        buttonToRouteConversion.put("floatingActionButton39", "helena_denver");
        buttonToRouteConversion.put("floatingActionButton35", "calgary_winnipeg");
        buttonToRouteConversion.put("floatingActionButton42", "helena_winnipeg");
        buttonToRouteConversion.put("floatingActionButton41", "helena_duluth");
        buttonToRouteConversion.put("floatingActionButton40", "helena_omaha");
        buttonToRouteConversion.put("floatingActionButton44", "denver_omaha");
        buttonToRouteConversion.put("floatingActionButton73", "denver_KC_first");
        buttonToRouteConversion.put("floatingActionButton70", "denver_KC_second");
        buttonToRouteConversion.put("floatingActionButton38", "denver_oklahomaCity");
        buttonToRouteConversion.put("floatingActionButton69", "santaFe_oklahomaCity");
        buttonToRouteConversion.put("floatingActionButton27", "elPaso_oklahomaCity");
        buttonToRouteConversion.put("floatingActionButton37", "elPaso_dallas");
        buttonToRouteConversion.put("floatingActionButton26", "elPaso_houston");
        buttonToRouteConversion.put("floatingActionButton45", "winnipeg_saultStMarie");
        buttonToRouteConversion.put("floatingActionButton46", "winnipeg_duluth");
        buttonToRouteConversion.put("floatingActionButton86", "duluth_saultStMarie");
        buttonToRouteConversion.put("floatingActionButton85", "duluth_omaha_first");
        buttonToRouteConversion.put("floatingActionButton84", "duluth_omaha_second");
        buttonToRouteConversion.put("floatingActionButton75", "omaha_KC_first");
        buttonToRouteConversion.put("floatingActionButton74", "omaha_KC_second");
        buttonToRouteConversion.put("floatingActionButton51", "KC_oklahomaCity_first");
        buttonToRouteConversion.put("floatingActionButton52", "KC_oklahomaCity_second");
        buttonToRouteConversion.put("floatingActionButton48", "oklahomaCity_dallas_first");
        buttonToRouteConversion.put("floatingActionButton47", "oklahomaCity_dallas_second");
        buttonToRouteConversion.put("floatingActionButton49", "dallas_houston_first");
        buttonToRouteConversion.put("floatingActionButton50", "dallas_houston_second");
        buttonToRouteConversion.put("floatingActionButton43", "houston_newOrleans");
        buttonToRouteConversion.put("floatingActionButton54", "dallas_littleRock");
        buttonToRouteConversion.put("floatingActionButton53", "oklahomaCity_littleRock");
        buttonToRouteConversion.put("floatingActionButton78", "KC_saintLouis_first");
        buttonToRouteConversion.put("floatingActionButton76", "KC_saintLouis_second");
        buttonToRouteConversion.put("floatingActionButton88", "omaha_chicago");
        buttonToRouteConversion.put("floatingActionButton87", "duluth_chicago");
        buttonToRouteConversion.put("floatingActionButton55", "newOrleans_littleRock");
        buttonToRouteConversion.put("floatingActionButton68", "littleRock_saintLouis");
        buttonToRouteConversion.put("floatingActionButton60", "newOrleans_atlanta_first");
        buttonToRouteConversion.put("floatingActionButton59", "newOrleans_atlanta_second");
        buttonToRouteConversion.put("floatingActionButton67", "littleRock_nashville");
        buttonToRouteConversion.put("floatingActionButton108", "saintLouis_nashville");
        buttonToRouteConversion.put("floatingActionButton110", "nashville_atlanta");
        buttonToRouteConversion.put("floatingActionButton79", "saintLouis_chicago_first");
        buttonToRouteConversion.put("floatingActionButton80", "saintLouis_chicago_second");
        buttonToRouteConversion.put("floatingActionButton91", "duluth_toronto");
        buttonToRouteConversion.put("floatingActionButton95", "saultStMarie_montreal");
        buttonToRouteConversion.put("floatingActionButton94", "saultStMarie_toronto");
        buttonToRouteConversion.put("floatingActionButton93", "toronto_montreal");
        buttonToRouteConversion.put("floatingActionButton92", "chicago_toronto");
        buttonToRouteConversion.put("floatingActionButton109", "montreal_boston_first");
        buttonToRouteConversion.put("floatingActionButton107", "montreal_boston_second");
        buttonToRouteConversion.put("floatingActionButton83", "chicago_pittsburg_first");
        buttonToRouteConversion.put("floatingActionButton82", "chicago_pittsburg_second");
        buttonToRouteConversion.put("floatingActionButton81", "saintLouis_pittsburg");
        buttonToRouteConversion.put("floatingActionButton96", "nashville_pittsburg");
        buttonToRouteConversion.put("floatingActionButton66", "nashville_raleigh");
        buttonToRouteConversion.put("floatingActionButton56", "newOrleans_miami");
        buttonToRouteConversion.put("floatingActionButton57", "atlanta_miami");
        buttonToRouteConversion.put("floatingActionButton61", "atlanta_charleston");
        buttonToRouteConversion.put("floatingActionButton63", "atlanta_raleigh_first");
        buttonToRouteConversion.put("floatingActionButton65", "atlanta_raleigh_second");
        buttonToRouteConversion.put("floatingActionButton58", "miami_charleston");
        buttonToRouteConversion.put("floatingActionButton62", "charleston_raleigh");
        buttonToRouteConversion.put("floatingActionButton97", "raleigh_pittsburg");
        buttonToRouteConversion.put("floatingActionButton99", "toronto_pittsburg");
        buttonToRouteConversion.put("floatingActionButton89", "raleigh_washington_first");
        buttonToRouteConversion.put("floatingActionButton90", "raleigh_washington_second");
        buttonToRouteConversion.put("floatingActionButton98", "pittsburg_washington");
        buttonToRouteConversion.put("floatingActionButton104", "washington_newYork_first");
        buttonToRouteConversion.put("floatingActionButton103", "washington_newYork_second");
        buttonToRouteConversion.put("floatingActionButton101", "pittsburg_newYork_first");
        buttonToRouteConversion.put("floatingActionButton102", "pittsburg_newYork_second");
        buttonToRouteConversion.put("floatingActionButton100", "montreal_newYork");
        buttonToRouteConversion.put("floatingActionButton105", "newYork_boston_first");
        buttonToRouteConversion.put("floatingActionButton106", "newYork_boston_second");
    }
}