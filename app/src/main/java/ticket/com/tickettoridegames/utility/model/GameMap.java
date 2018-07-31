package ticket.com.tickettoridegames.utility.model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameMap {

//    private Map<String, Route> routes = new HashMap<>(); //key is route name
    private Map<String, Route> trainTracks = new HashMap<>(); //temp for routes //key is route name
    private Route newestClaimedRoute;


    public GameMap(){
        initTrianTracks();
    }

    public boolean claimRoute(String playerID, Route route){
        if(!trainTracks.get(route.getName()).claim(playerID)) { return false; }

        newestClaimedRoute = route;
        return true;
    }

    public Map<String, Route> getRoutes() {
        return Collections.unmodifiableMap(trainTracks);
    }

    public List<Route> getClaimedRoutes() {
        List<Route> claimed = new ArrayList<>();

        for(String each : trainTracks.keySet()) {
            Route route = trainTracks.get(each);
            if(route.isOwned()) { claimed.add(route); }
        }

        return Collections.unmodifiableList(claimed);
    }

    public Map<String, Route> getTrainTracks() {
        return Collections.unmodifiableMap(trainTracks);
    }

    private void initTrianTracks() {
        trainTracks.put("vancouver_calgary", new Route("vancouver_calgary", new City("vancouver"), new City("calgary"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("vancouver_seattle_first", new Route("vancouver_seattle_first", new City("vancouver"), new City("seattle"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("vancouver_seattle_second", new Route("vancouver_seattle_second", new City("vancouver"), new City("seattle"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("seattle_portland_first", new Route("seattle_portland_first", new City("seattle"), new City("portland"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("seattle_portland_second", new Route("seattle_portland_second", new City("seattle"), new City("portland"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("seattle_calgary", new Route("seattle_calgary",new City("seattle"), new City("portland"), 4, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("calgary_helena", new Route("calgary_helena",new City("calgary"), new City("helena"), 4, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("portland_sanFran_first", new Route("portland_sanFran_first",new City("portland"), new City("sanFran"), 5, TrainCard.TRAIN_TYPE.GREEN, 1));
        trainTracks.put("portland_sanFran_second", new Route("portland_sanFran_second",new City("portland"), new City("sanFran"), 5, TrainCard.TRAIN_TYPE.PINK, 2));
        trainTracks.put("seattle_helena", new Route("seattle_helena",new City("seattle"), new City("helena"), 6, TrainCard.TRAIN_TYPE.YELLOW, null));
        trainTracks.put("portland_SLC", new Route("portland_SLC",new City("portland"), new City("SLC"), 6, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("sanFran_SLC_first", new Route("sanFran_SLC_first",new City("sanFran"), new City("SLC"), 5, TrainCard.TRAIN_TYPE.ORANGE, 1));
        trainTracks.put("sanFran_SLC_second", new Route("sanFran_SLC_second",new City("sanFran"), new City("SLC"), 5, TrainCard.TRAIN_TYPE.WHITE, 2));
        trainTracks.put("sanfran_LA_first", new Route("sanfran_LA_first",new City("sanFran"), new City("LA"), 3, TrainCard.TRAIN_TYPE.PINK, 1));
        trainTracks.put("sanfran_LA_second", new Route("sanfran_LA_second",new City("sanFran"), new City("LA"), 3, TrainCard.TRAIN_TYPE.YELLOW, 2));
        trainTracks.put("LA_lasVegas", new Route("LA_lasVegas",new City("LA"), new City("lasVegas"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("lasVegas_SLC", new Route("lasVegas_SLC",new City("lasVegas"), new City("SLC"), 3, TrainCard.TRAIN_TYPE.ORANGE, null));
        trainTracks.put("LA_elPaso", new Route("LA_elPaso",new City("LA"), new City("elPaso"), 6, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("LA_pheonix", new Route("LA_pheonix",new City("LA"), new City("pheonix"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("pheonix_elPaso", new Route("pheonix_elPaso",new City("pheonix"), new City("elPaso"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("pheonix_santaFe", new Route("pheonix_santaFe",new City("pheonix"), new City("santFe"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("elPaso_santaFe", new Route("elPaso_santaFe",new City("elPaso"), new City("santaFe"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("pheonix_denver", new Route("pheonix_denver",new City("pheonix"), new City("denver"), 5, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("santaFe_denver", new Route("santaFe_denver",new City("santFe"), new City("denver"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("SLC_denver_first", new Route("SLC_denver_first",new City("SLC"), new City("denver"), 3, TrainCard.TRAIN_TYPE.YELLOW, 1));
        trainTracks.put("SLC_denver_second", new Route("SLC_denver_second",new City("SLC"), new City("denver"), 3, TrainCard.TRAIN_TYPE.RED, 2));
        trainTracks.put("helena_SLC", new Route("helena_SLC",new City("helena"), new City("SLC"), 3, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("helena_denver", new Route("helena_denver",new City("helena"), new City("denver"), 4, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("calgary_winnipeg", new Route("calgary_winnipeg",new City("calgary"), new City("winnipeg"), 6, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("helena_winnipeg", new Route("helena_winnipeg",new City("helena"), new City("winnipeg"), 4, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("helena_duleth", new Route("helena_winnipeg",new City("helena"), new City("duluth"), 6, TrainCard.TRAIN_TYPE.ORANGE, null));
        trainTracks.put("helena_omaha", new Route("helena_omaha",new City("helena"), new City("omaha"), 5, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("denver_omaha", new Route("denver_omaha",new City("denver"), new City("omaha"), 4, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("denver_KC_first", new Route("denver_KC_first",new City("denver"), new City("KC"), 4, TrainCard.TRAIN_TYPE.BLACK, 1));
        trainTracks.put("denver_KC_second", new Route("denver_KC_second",new City("denver"), new City("KC"), 4, TrainCard.TRAIN_TYPE.ORANGE, 2));
        trainTracks.put("denver_oklahomaCity", new Route("denver_oklahomaCity",new City("denver"), new City("oklahomaCity"), 4, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("santaFe_oklahomaCity", new Route("santaFe_oklahomaCity",new City("santaFe"), new City("oklahomaCity"), 3, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("elPaso_oklahomaCity", new Route("elPaso_oklahomaCity",new City("elPaso"), new City("oklahomaCity"), 5, TrainCard.TRAIN_TYPE.YELLOW, null));
        trainTracks.put("elPaso_dallas", new Route("elPaso_dallas",new City("elPaso"), new City("dallas"), 4, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("elPaso_houston", new Route("elPaso_houston",new City("elPaso"), new City("housten"), 6, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("winnipeg_saultStMarie", new Route("winnipeg_saultStMarie",new City("winnipeg"), new City("saultStMarie"), 6, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("winnipeg_duluth", new Route("winnipeg_duluth", new City("winnipeg"), new City("duluth"), 4, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("duluth_saultStMarie", new Route("duluth_saultStMarie",new City("duluth"), new City("saultStMarie"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("duluth_omaha_first", new Route("duluth_omaha_first",new City("duluth"), new City("omaha"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("duluth_omaha_second", new Route("duluth_omaha_second",new City("duluth"), new City("omaha"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("omaha_KC_first", new Route("omaha_KC_first",new City("omaha"), new City("KC"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("omaha_KC_second", new Route("omaha_KC_second",new City("omaha"), new City("KC"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("KC_oklahomaCity_first", new Route("KC_oklahomaCity_first",new City("KC"), new City("oklahomaCity"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("KC_oklahomaCity_second", new Route("KC_oklahomaCity_second",new City("KC"), new City("oklahomaCity"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("oklahomaCity_dallas_first", new Route("oklahomaCity_dallas_first",new City("oklahomaCity"), new City("dallas"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("oklahomaCity_dallas_second", new Route("oklahomaCity_dallas_second",new City("oklahomaCity"), new City("dallas"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("dallas_houston_first", new Route("dallas_houston_first",new City("dallas"), new City("housten"), 1, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("dallas_houston_second", new Route("dallas_houston_second",new City("dallas"), new City("housten"), 1, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("houston_newOrleans", new Route("houston_newOrleans",new City("housten"), new City("newOrleans"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("dallas_littleRock", new Route("dallas_littleRock",new City("dallas"), new City("littleRock"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("oklahoma_littleRock", new Route("oklahoma_littleRock",new City("oklahomaCity"), new City("littleRock"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("KC_saintLouis_first", new Route("KC_saintLouis_first",new City("KC"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.BLUE, 1));
        trainTracks.put("KC_saintLouis_second", new Route("omaha_chicago",new City("KC"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.PINK, 2));
        trainTracks.put("omaha_chicago", new Route("omaha_chicago",new City("omaha"), new City("chicago"), 4, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("duluth_chicago", new Route("duluth_chicago",new City("duluth"), new City("chicago"), 3, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("newOrleans_littleRock", new Route("newOrleans_littleRock",new City("newOrleans"), new City("littleRock"), 3, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("littleRock_saintLouis", new Route("littleRock_saintLouis",new City("littleRock"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("newOrleans_atlanta_first", new Route("newOrleans_atlanta_first",new City("newOrleans"), new City("atlanta"), 4, TrainCard.TRAIN_TYPE.YELLOW, 1));
        trainTracks.put("newOrleans_atlanta_second", new Route("newOrleans_atlanta_second",new City("newOrleans"), new City("atlanta"), 4, TrainCard.TRAIN_TYPE.ORANGE, 2));
        trainTracks.put("littleRock_Nashville", new Route("littleRock_Nashville",new City("littleRock"), new City("nashville"), 3, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("saintLouis_nashville", new Route("saintLouis_nashville",new City("saintLouis"), new City("nashville"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("nashville_atlanta", new Route("nashville_atlanta",new City("nashville"), new City("atlanta"), 1, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("saintLouis_chicago_first", new Route("saintLouis_chicago_first",new City("saintLouis"), new City("chicago"), 2, TrainCard.TRAIN_TYPE.GREEN, 1));
        trainTracks.put("saintLouis_chicago_second", new Route("saintLouis_chicago_second",new City("saintLouis"), new City("chicago"), 2, TrainCard.TRAIN_TYPE.WHITE, 2));
        trainTracks.put("duluth_toronto", new Route("duluth_toronto",new City("duluth"), new City("toronto"), 6, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("saultStMarie_montreal", new Route("saultStMarie_montreal",new City("saultStMarie"), new City("montreal"), 5, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("saultStMarie_toronto", new Route("saultStMarie_toronto",new City("saultStMarie"), new City("toronto"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("toronto_montreal", new Route("toronto_montreal",new City( "toronto"), new City("montreal"), 3, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("chicago_toronto", new Route("chicago_toronto",new City( "chicago"), new City("toronto"), 4, TrainCard.TRAIN_TYPE.WHITE, null));
        trainTracks.put("montreal_boston_first", new Route("montreal_boston_first", new City("montreal"), new City("boston"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("montreal_boston_second", new Route("montreal_boston_second", new City("montreal"), new City("boston"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("chicago_pittsburg_first", new Route("chicago_pittsburg_first", new City("chicago"), new City("pittsburg"), 3, TrainCard.TRAIN_TYPE.ORANGE, 1));
        trainTracks.put("chicago_pittsburg_second", new Route("chicago_pittsburg_second", new City("chocago"), new City("pittsburg"), 3, TrainCard.TRAIN_TYPE.BLACK, 2));
        trainTracks.put("saintLouis_pittsburg", new Route("saintLouis_pittsburg", new City("saintLouis"), new City("pittsburg"), 5, TrainCard.TRAIN_TYPE.GREEN, null));
        trainTracks.put("nashville_pittsburg", new Route("nashville_pittsburg", new City("nashville"), new City("pittsburg"), 4, TrainCard.TRAIN_TYPE.YELLOW, null));
        trainTracks.put("nashville_raleigh", new Route("nashville_raleigh", new City("nashville"), new City("raleigh"), 3, TrainCard.TRAIN_TYPE.BLACK, null));
        trainTracks.put("newOrleans_miami", new Route("newOrleans_miami", new City("newOrleans"), new City("miami"), 6, TrainCard.TRAIN_TYPE.RED, null));
        trainTracks.put("atlanta_miami", new Route("atlanta_miami", new City("atlanta"), new City("miami"), 5, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("atlanta_charleston", new Route("atlanta_charleston", new City("atlanta"), new City("charleston"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("atlanta_raleigh_first", new Route("atlanta_raleigh_first", new City("atlanta"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("atlanta_raleigh_second", new Route("atlanta_raleigh_second", new City("atlanta"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("miami_charleston", new Route("miami_charleston", new City("miami"), new City("charleston"), 4, TrainCard.TRAIN_TYPE.PINK, null));
        trainTracks.put("charleston_raleigh", new Route("charleston_raleigh", new City("charleson"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("raleigh_pittsburg", new Route("raleigh_pittsburg", new City("raleigh"), new City("pittsburg"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("toronto_pittsburg", new Route("toronto_pittsburg", new City( "toronto"), new City("pittsburg"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("raleigh_washington_first", new Route("raleigh_washington_first", new City("raleigh"), new City("washington"), 3, TrainCard.TRAIN_TYPE.WILD, 1));
        trainTracks.put("raleigh_washington_second", new Route("raleigh_washington_second", new City("raleigh"), new City("washington"), 2, TrainCard.TRAIN_TYPE.WILD, 2));
        trainTracks.put("pittsburg_washington", new Route("pittsburg_washington", new City("pittsburg"), new City("washington"), 2, TrainCard.TRAIN_TYPE.WILD, null));
        trainTracks.put("washington_newYork_first", new Route("washington_newYork_first", new City("washington"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.ORANGE, 1));
        trainTracks.put("washington_newYork_second", new Route("washington_newYork_second", new City("washington"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.BLACK, 2));
        trainTracks.put("pittsburg_newYork_first", new Route("pittsburg_newYork_first", new City("pittsburg"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.GREEN, 1));
        trainTracks.put("pittsburg_newYork_second", new Route("pittsburg_newYork_second", new City("pittsburg"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.WHITE, 2));
        trainTracks.put("montreal_newYork", new Route("montreal_newYork", new City("montreal"), new City("newYork"), 3, TrainCard.TRAIN_TYPE.BLUE, null));
        trainTracks.put("newYork_boston_first", new Route("newYork_boston_first", new City("newYork"), new City("boston"), 2, TrainCard.TRAIN_TYPE.YELLOW, 1));
        trainTracks.put("newYork_boston_second", new Route("newYork_boston_second", new City("newYork"), new City("boston"), 2, TrainCard.TRAIN_TYPE.RED, 2));
    }

    public Route getRoute(String routeName) {
        return trainTracks.get(routeName);
    }

    public Boolean isClaimed(Route route) {
        return trainTracks.get(route.getName()).isOwned();
    }

    public Route getNewestClaimedRoute() {
        return newestClaimedRoute;
    }
}
