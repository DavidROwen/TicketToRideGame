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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
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
    private Integer red =  -65536;
    private Integer blue = -16776961;
    private Integer yellow = -256;
    private Integer green = -16711936;
    private Integer black =  -16777216;
    private IMapPresenter presenter;
    private Button turnButton;
    private String userInput;
    private Route chosenRoute;
    private Map<String, String> routeButtons = new HashMap<>(); //the key is the button, the value is the route name

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

        //RouteButtons
        FloatingActionButton floatingActionButton7 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton7);
        floatingActionButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fab7.setBackgroundTintList(ColorStateList.valueOf(black));
            }
        });

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

        trainTracks.put("vancouver_calgary", new Route(new City("vancouver"), new City("calgary"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("vancouver_seattle_first", new Route(new City("vancouver"), new City("seattle"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("vancouver_seattle_second", new Route(new City("vancouver"), new City("seattle"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("seattle_portland_first", new Route(new City("seattle"), new City("portland"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("seattle_portland_second", new Route(new City("seattle"), new City("portland"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("seattle_calgary", new Route(new City("seattle"), new City("portland"), 4, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("calgary_helena", new Route(new City("calgary"), new City("helena"), 4, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("portland_sanFran_first", new Route(new City("portland"), new City("sanFran"), 5, TrainCard.TRAIN_TYPE.GREEN, 1));
        trainTracks.put("portland_sanFran_second", new Route(new City("portland"), new City("sanFran"), 5, TrainCard.TRAIN_TYPE.PINK, 2));
        trainTracks.put("seattle_helena", new Route(new City("seattle"), new City("helena"), 6, TrainCard.TRAIN_TYPE.YELLOW, null));
        trainTracks.put("portland_SLC", new Route(new City("portland"), new City("SLC"), 6, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("sanFran_SLC_first", new Route(new City("sanFran"), new City("SLC"), 5, TrainCard.TRAIN_TYPE.ORANGE, 1));
        trainTracks.put("sanFran_SLC_second", new Route(new City("sanFran"), new City("SLC"), 5, TrainCard.TRAIN_TYPE.WHITE, 2));
        trainTracks.put("sanfran_LA_first", new Route(new City("sanFran"), new City("LA"), 3, TrainCard.TRAIN_TYPE.PINK, 1));
        trainTracks.put("sanfran_LA_second", new Route(new City("sanFran"), new City("LA"), 3, TrainCard.TRAIN_TYPE.YELLOW, 2));
        trainTracks.put("LA_lasVegas", new Route(new City("LA"), new City("lasVegas"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("lasVegas_SLC", new Route(new City("lasVegas"), new City("SLC"), 3, TrainCard.TRAIN_TYPE.ORANGE, null));
        trainTracks.put("LA_elPaso", new Route(new City("LA"), new City("elPaso"), 6, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("LA_pheonix", new Route(new City("LA"), new City("pheonix"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("pheonix_elPaso", new Route(new City("pheonix"), new City("elPaso"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("pheonix_santaFe", new Route(new City("pheonix"), new City("santFe"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("elPaso_santaFe", new Route(new City("elPaso"), new City("santaFe"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("pheonix_denver", new Route(new City("pheonix"), new City("denver"), 5, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("santaFe_denver", new Route(new City("santFe"), new City("denver"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("SLC_denver_first", new Route(new City("SLC"), new City("denver"), 3, TrainCard.TRAIN_TYPE.YELLOW, 1));
        trainTracks.put("SLC_denver_second", new Route(new City("SLC"), new City("denver"), 3, TrainCard.TRAIN_TYPE.RED, 2));
        trainTracks.put("helena_SLC", new Route(new City("helena"), new City("SLC"), 3, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("helena_denver", new Route(new City("helena"), new City("denver"), 4, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("calgary_winnipeg", new Route(new City("calgary"), new City("winnipeg"), 6, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("helena_winnipeg", new Route(new City("helena"), new City("winnipeg"), 4, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("helena_duleth", new Route(new City("helena"), new City("duluth"), 6, TrainCard.TRAIN_TYPE.ORANGE, null));
        trainTracks.put("helena_omaha", new Route(new City("helena"), new City("omaha"), 5, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("denver_omaha", new Route(new City("denver"), new City("omaha"), 4, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("denver_KC_first", new Route(new City("denver"), new City("KC"), 4, TrainCard.TRAIN_TYPE.BLACK, 1));
        trainTracks.put("denver_KC_second", new Route(new City("denver"), new City("KC"), 4, TrainCard.TRAIN_TYPE.ORANGE, 2));
        trainTracks.put("denver_oklahomaCity", new Route(new City("denver"), new City("oklahomaCity"), 4, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("santaFe_oklahomaCity", new Route(new City("santaFe"), new City("oklahomaCity"), 3, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("elPaso_oklahomaCity", new Route(new City("elPaso"), new City("oklahomaCity"), 5, TrainCard.TRAIN_TYPE.YELLOW, null));
        trainTracks.put("elPaso_dallas", new Route(new City("elPaso"), new City("dallas"), 4, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("elPaso_houston", new Route(new City("elPaso"), new City("housten"), 6, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("winnipeg_saultStMarie", new Route(new City("winnipeg"), new City("saultStMarie"), 6, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("winnipeg_duluth", new Route(new City("winnipeg"), new City("duluth"), 4, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("duluth_saultStMarie", new Route(new City("duluth"), new City("saultStMarie"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("duluth_omaha_first", new Route(new City("duluth"), new City("omaha"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("duluth_omaha_second", new Route(new City("duluth"), new City("omaha"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("omaha_KC_first", new Route(new City("omaha"), new City("KC"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("omaha_KC_second", new Route(new City("omaha"), new City("KC"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("KC_oklahomaCity_first", new Route(new City("KC"), new City("oklahomaCity"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("KC_oklahomaCity_second", new Route(new City("KC"), new City("oklahomaCity"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("oklahomaCity_dallas_first", new Route(new City("oklahomaCity"), new City("dallas"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("oklahomaCity_dallas_second", new Route(new City("oklahomaCity"), new City("dallas"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("dallas_houston_first", new Route(new City("dallas"), new City("housten"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("dallas_houston_second", new Route(new City("dallas"), new City("housten"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("houston_newOrleans", new Route(new City("housten"), new City("newOrleans"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("dallas_littleRock", new Route(new City("dallas"), new City("littleRock"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("oklahoma_littleRock", new Route(new City("oklahomaCity"), new City("littleRock"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("KC_saintLouis_first", new Route(new City("KC"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.BLUE, 1));
        trainTracks.put("KC_saintLouis_second", new Route(new City("KC"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.PINK, 2));
        trainTracks.put("omaha_chicago", new Route(new City("omaha"), new City("chicago"), 4, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("duluth_chicago", new Route(new City("duluth"), new City("chicago"), 3, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("newOrleans_littleRock", new Route(new City("newOrleans"), new City("littleRock"), 3, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("littleRock_saintLouis", new Route(new City("littleRock"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("newOrleans_atlanta_first", new Route(new City("newOrleans"), new City("atlanta"), 4, TrainCard.TRAIN_TYPE.YELLOW, 1));
        trainTracks.put("newOrleans_atlanta_second", new Route(new City("newOrleans"), new City("atlanta"), 4, TrainCard.TRAIN_TYPE.ORANGE, 2));
        trainTracks.put("littleRock_Nashville", new Route(new City("littleRock"), new City("nashville"), 3, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("saintLouis_nashville", new Route(new City("saintLouis"), new City("nashville"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("nashville_atlanta", new Route(new City("nashville"), new City("atlanta"), 1, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("saintLouis_chicago_first", new Route(new City("saintLouis"), new City("chicago"), 2, TrainCard.TRAIN_TYPE.GREEN, 1));
        trainTracks.put("saintLouis_chicago_second", new Route(new City("saintLouis"), new City("chicago"), 2, TrainCard.TRAIN_TYPE.WHITE, 2));
        trainTracks.put("duluth_toronto", new Route(new City("duluth"), new City("toronto"), 6, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("saultStMarie_montreal", new Route(new City("saultStMarie"), new City("montreal"), 5, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("saultStMarie_toronto", new Route(new City("saultStMarie"), new City("toronto"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("toronto_montreal", new Route(new City("toronto"), new City("montreal"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("chicago_toronto", new Route(new City("chicago"), new City("toronto"), 4, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("montreal_boston_first", new Route(new City("montreal"), new City("boston"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("montreal_boston_second", new Route(new City("montreal"), new City("boston"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("chicago_pittsburg_first", new Route(new City("chicago"), new City("pittsburg"), 3, TrainCard.TRAIN_TYPE.ORANGE, 1));
        trainTracks.put("chicago_pittsburg_second", new Route(new City("chocago"), new City("pittsburg"), 3, TrainCard.TRAIN_TYPE.BLACK, 2));
        trainTracks.put("saintLouis_pittsburg", new Route(new City("saintLouis"), new City("pittsburg"), 5, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("nashville_pittsburg", new Route(new City("nashville"), new City("pittsburg"), 4, TrainCard.TRAIN_TYPE.YELLOW, null));
        trainTracks.put("nashville_raleigh", new Route(new City("nashville"), new City("raleigh"), 3, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("newOrleans_miami", new Route(new City("newOrleans"), new City("miami"), 6, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("atlanta_miami", new Route(new City("atlanta"), new City("miami"), 5, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("atlanta_charleston", new Route(new City("atlanta"), new City("charleston"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("atlanta_raleigh_first", new Route(new City("atlanta"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("atlanta_raleigh_second", new Route(new City("atlanta"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("miami_charleston", new Route(new City("miami"), new City("charleston"), 4, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("charleston_raleigh", new Route(new City("charleson"), new City("raleigh"),  2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("raleigh_pittsburg", new Route(new City("raleigh"), new City("pittsburg"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("toronto_pittsburg", new Route(new City("toronto"), new City("pittsburg"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("raleigh_washington_first", new Route(new City("raleigh"), new City("washington"), 3, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("raleigh_washington_second", new Route(new City("raleigh"), new City("washington"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("pittsburg_washington", new Route(new City("pittsburg"), new City("washington"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("washington_newYork_first", new Route(new City("washington"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.ORANGE, 1));
        trainTracks.put("washington_newYork_second", new Route(new City("washington"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.BLACK, 2));
        trainTracks.put("pittsburg_newYork_first", new Route(new City("pittsburg"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.GREEN, 1));
        trainTracks.put("pittsburg_newYork_second", new Route(new City("pittsburg"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.WHITE, 2));
        trainTracks.put("montreal_newYork", new Route(new City("montreal"), new City("newYork"), 3, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("newYork_boston_first", new Route(new City("newYork"), new City("boston"), 2, TrainCard.TRAIN_TYPE.YELLOW, 1));
        trainTracks.put("newYork_boston_second", new Route(new City("newYork"), new City("boston"), 2, TrainCard.TRAIN_TYPE.RED, 2));

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