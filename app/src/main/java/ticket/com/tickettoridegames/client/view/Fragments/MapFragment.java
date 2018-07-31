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
    private Map<String, Route> trainTracks;
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

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map_fragment,parent,false);
        //presenter = new MapPresenter(this);

        //Init
        initTrianTracks();

        //RouteButtons
        FloatingActionButton floatingActionButton7 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton7);
        floatingActionButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton7")));
            }
        });
        FloatingActionButton floatingActionButton8 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton8);
        floatingActionButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton8")));
            }
        });
        FloatingActionButton floatingActionButton9 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton9);
        floatingActionButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton9")));
            }
        });
        FloatingActionButton floatingActionButton10 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton10);
        floatingActionButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton10")));
            }
        });
        FloatingActionButton floatingActionButton11 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton11);
        floatingActionButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton11")));
            }
        });
        FloatingActionButton floatingActionButton12 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton12);
        floatingActionButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton12")));
            }
        });
        FloatingActionButton floatingActionButton13 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton13);
        floatingActionButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton13")));
            }
        });
        FloatingActionButton floatingActionButton14 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton14);
        floatingActionButton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton14")));
            }
        });
        FloatingActionButton floatingActionButton15 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton15);
        floatingActionButton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton15")));
            }
        });
        FloatingActionButton floatingActionButton16 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton16);
        floatingActionButton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton16")));
            }
        });
        FloatingActionButton floatingActionButton17 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton17);
        floatingActionButton17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton17")));
            }
        });
        FloatingActionButton floatingActionButton18 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton18);
        floatingActionButton18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton18")));
            }
        });
        FloatingActionButton floatingActionButton19 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton19);
        floatingActionButton19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton19")));
            }
        });
        FloatingActionButton floatingActionButton20 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton20);
        floatingActionButton20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton20")));
            }
        });
        FloatingActionButton floatingActionButton21 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton21);
        floatingActionButton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton21")));
            }
        });
        FloatingActionButton floatingActionButton22 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton22);
        floatingActionButton22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton22")));
            }
        });
        FloatingActionButton floatingActionButton23 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton23);
        floatingActionButton23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton23")));
            }
        });
        FloatingActionButton floatingActionButton24 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton24);
        floatingActionButton24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton24")));
            }
        });
        FloatingActionButton floatingActionButton25 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton25);
        floatingActionButton25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton25")));
            }
        });
        FloatingActionButton floatingActionButton26 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton26);
        floatingActionButton26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton26")));
            }
        });
        FloatingActionButton floatingActionButton27 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton27);
        floatingActionButton27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton27")));
            }
        });
        FloatingActionButton floatingActionButton28 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton28);
        floatingActionButton28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton28")));
            }
        });
        FloatingActionButton floatingActionButton29 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton29);
        floatingActionButton29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton29")));
            }
        });
        FloatingActionButton floatingActionButton30 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton30);
        floatingActionButton30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton30")));
            }
        });
        FloatingActionButton floatingActionButton31 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton31);
        floatingActionButton31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton31")));
            }
        });
        FloatingActionButton floatingActionButton33 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton33);
        floatingActionButton33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton33")));
            }
        });
        FloatingActionButton floatingActionButton34 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton34);
        floatingActionButton34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton34")));
            }
        });
        FloatingActionButton floatingActionButton35 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton35);
        floatingActionButton35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton35")));
            }
        });
        FloatingActionButton floatingActionButton36 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton36);
        floatingActionButton36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton36")));
            }
        });
        FloatingActionButton floatingActionButton37 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton37);
        floatingActionButton37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton37")));
            }
        });
        FloatingActionButton floatingActionButton38 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton38);
        floatingActionButton38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton38")));
            }
        });
        FloatingActionButton floatingActionButton39 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton39);
        floatingActionButton39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton39")));
            }
        });
        FloatingActionButton floatingActionButton40 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton40);
        floatingActionButton40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton40")));
            }
        });
        FloatingActionButton floatingActionButton41 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton41);
        floatingActionButton41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton41")));
            }
        });
        FloatingActionButton floatingActionButton42 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton42);
        floatingActionButton42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton42")));
            }
        });
        FloatingActionButton floatingActionButton43 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton43);
        floatingActionButton43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton43")));
            }
        });
        FloatingActionButton floatingActionButton44 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton44);
        floatingActionButton44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton44")));
            }
        });
        FloatingActionButton floatingActionButton45 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton45);
        floatingActionButton45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton45")));
            }
        });
        FloatingActionButton floatingActionButton46 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton46);
        floatingActionButton46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton46")));
            }
        });
        FloatingActionButton floatingActionButton47 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton47);
        floatingActionButton47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton47")));
            }
        });
        FloatingActionButton floatingActionButton48 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton48);
        floatingActionButton48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton48")));
            }
        });
        FloatingActionButton floatingActionButton49 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton49);
        floatingActionButton49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton49")));
            }
        });
        FloatingActionButton floatingActionButton50 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton50);
        floatingActionButton50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton50")));
            }
        });
        FloatingActionButton floatingActionButton51 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton51);
        floatingActionButton51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton51")));
            }
        });
        FloatingActionButton floatingActionButton52 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton52);
        floatingActionButton52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton52")));
            }
        });
        FloatingActionButton floatingActionButton53 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton53);
        floatingActionButton53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton53")));
            }
        });
        FloatingActionButton floatingActionButton54 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton54);
        floatingActionButton54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton54")));
            }
        });
        FloatingActionButton floatingActionButton55 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton55);
        floatingActionButton55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton55")));
            }
        });
        FloatingActionButton floatingActionButton56 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton56);
        floatingActionButton56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton56")));
            }
        });
        FloatingActionButton floatingActionButton57 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton57);
        floatingActionButton57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton57")));
            }
        });
        FloatingActionButton floatingActionButton58 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton58);
        floatingActionButton58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton58")));
            }
        });
        FloatingActionButton floatingActionButton59 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton59);
        floatingActionButton59.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton59")));
            }
        });
        FloatingActionButton floatingActionButton60 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton60);
        floatingActionButton60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton60")));
            }
        });
        FloatingActionButton floatingActionButton61 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton61);
        floatingActionButton61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton61")));
            }
        });
        FloatingActionButton floatingActionButton62 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton62);
        floatingActionButton62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton62")));
            }
        });
        FloatingActionButton floatingActionButton63 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton63);
        floatingActionButton63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton63")));
            }
        });
        FloatingActionButton floatingActionButton65 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton65);
        floatingActionButton65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton65")));
            }
        });
        FloatingActionButton floatingActionButton66 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton66);
        floatingActionButton66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton66")));
            }
        });
        FloatingActionButton floatingActionButton67 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton67);
        floatingActionButton67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton67")));
            }
        });
        FloatingActionButton floatingActionButton68 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton68);
        floatingActionButton68.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton68")));
            }
        });
        FloatingActionButton floatingActionButton69 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton69);
        floatingActionButton69.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton69")));
            }
        });
        FloatingActionButton floatingActionButton70 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton70);
        floatingActionButton70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton70")));
            }
        });
        FloatingActionButton floatingActionButton73 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton73);
        floatingActionButton73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton73")));
            }
        });
        FloatingActionButton floatingActionButton74 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton74);
        floatingActionButton74.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton74")));
            }
        });
        FloatingActionButton floatingActionButton75 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton75);
        floatingActionButton75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton75")));
            }
        });
        FloatingActionButton floatingActionButton76 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton76);
        floatingActionButton76.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton76")));
            }
        });
        FloatingActionButton floatingActionButton78 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton78);
        floatingActionButton78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton78")));
            }
        });
        FloatingActionButton floatingActionButton79 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton79);
        floatingActionButton79.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton79")));
            }
        });
        FloatingActionButton floatingActionButton80 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton80);
        floatingActionButton80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton80")));
            }
        });
        FloatingActionButton floatingActionButton81 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton81);
        floatingActionButton81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton81")));
            }
        });
        FloatingActionButton floatingActionButton82 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton82);
        floatingActionButton82.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton82")));
            }
        });
        FloatingActionButton floatingActionButton83 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton83);
        floatingActionButton83.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton83")));
            }
        });
        FloatingActionButton floatingActionButton84 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton84);
        floatingActionButton84.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton84")));
            }
        });
        FloatingActionButton floatingActionButton85 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton85);
        floatingActionButton85.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton85")));
            }
        });
        FloatingActionButton floatingActionButton86 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton86);
        floatingActionButton86.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton86")));
            }
        });
        FloatingActionButton floatingActionButton87 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton87);
        floatingActionButton87.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton87")));
            }
        });
        FloatingActionButton floatingActionButton88 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton88);
        floatingActionButton88.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton88")));
            }
        });
        FloatingActionButton floatingActionButton89 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton89);
        floatingActionButton89.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton89")));
            }
        });
        FloatingActionButton floatingActionButton90 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton90);
        floatingActionButton90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton90")));
            }
        });
        FloatingActionButton floatingActionButton91 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton91);
        floatingActionButton91.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton91")));
            }
        });
        FloatingActionButton floatingActionButton92 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton92);
        floatingActionButton92.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton92")));
            }
        });
        FloatingActionButton floatingActionButton93 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton93);
        floatingActionButton93.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton93")));
            }
        });
        FloatingActionButton floatingActionButton94 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton94);
        floatingActionButton94.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton94")));
            }
        });
        FloatingActionButton floatingActionButton95 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton95);
        floatingActionButton95.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton95")));
            }
        });
        FloatingActionButton floatingActionButton96 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton96);
        floatingActionButton96.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton96")));
            }
        });
        FloatingActionButton floatingActionButton97 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton97);
        floatingActionButton97.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton97")));
            }
        });
        FloatingActionButton floatingActionButton98 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton98);
        floatingActionButton98.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton98")));
            }
        });
        FloatingActionButton floatingActionButton100 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton100);
        floatingActionButton100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton100")));
            }
        });
        FloatingActionButton floatingActionButton101 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton101);
        floatingActionButton101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton101")));
            }
        });
        FloatingActionButton floatingActionButton102 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton102);
        floatingActionButton102.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton102")));
            }
        });
        FloatingActionButton floatingActionButton103 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton103);
        floatingActionButton103.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton103")));
            }
        });
        FloatingActionButton floatingActionButton104 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton104);
        floatingActionButton104.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton104")));
            }
        });
        FloatingActionButton floatingActionButton105 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton105);
        floatingActionButton105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton105")));
            }
        });
        FloatingActionButton floatingActionButton106 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton106);
        floatingActionButton106.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton106")));
            }
        });
        FloatingActionButton floatingActionButton107 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton107);
        floatingActionButton107.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton107")));
            }
        });
        FloatingActionButton floatingActionButton108 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton108);
        floatingActionButton108.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton108")));
            }
        });
        FloatingActionButton floatingActionButton109 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton109);
        floatingActionButton109.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton109")));
            }
        });
        FloatingActionButton floatingActionButton110 = (FloatingActionButton) view.findViewById(R.id.floatingActionButton110);
        floatingActionButton110.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.claimRoute(trainTracks.get(routeButtons.get("floatingActionButton110")));
            }
        });


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
        String routeName = (String) getKeyFromValue(trainTracks,route);
        String buttonName = (String) getKeyFromValue(routeButtons,routeName);

        if (color == TrainCard.TRAIN_TYPE.BLACK){
            //buttonName.setBackgroundTintList(ColorStateList.valueOf(black));
        }else if(color == TrainCard.TRAIN_TYPE.BLUE){
            //buttonName.setBackgroundTintList(ColorStateList.valueOf(blue));
        }else if(color == TrainCard.TRAIN_TYPE.YELLOW){
            //buttonName.setBackgroundTintList(ColorStateList.valueOf(yellow));
        }else if(color == TrainCard.TRAIN_TYPE.GREEN){
            //buttonName.setBackgroundTintList(ColorStateList.valueOf(green));
        }else if(color == TrainCard.TRAIN_TYPE.RED){
            //buttonName.setBackgroundTintList(ColorStateList.valueOf(red));
        }
    }

    public void placeTrains(Route route, Integer color){
        String routeName = (String) getKeyFromValue(trainTracks,route);
        String buttonName = (String) getKeyFromValue(routeButtons,routeName);

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