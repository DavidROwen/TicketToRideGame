package ticket.com.utility.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ticket.com.utility.web.Result;


public class GameMap {
    private Map<String, Route> routes = new HashMap<>(); //key is route NAME
    private Map<String, Pair<String,String>> doubleRoutesIndex = new HashMap<>(); //key is route name
    private List<Pair<String, String>> claimedRoutes = new LinkedList<>(); //routeName, ownerId
    private Pair<String,String> newestClaimedRoute; //routeName, ownerId

    public GameMap(){
        initRoutes();
        initDoubleRoutesIndex();
    }

    public Result canClaim(String routeName, String playerId, Integer numPlayers) {
        //check with routs
        if(routes.get(routeName) == null) { return new Result(false, null, "Route doesn't exist"); }
        Result result = routes.get(routeName).canClaim();
        if(!result.isSuccess()) { return result; }

        //check by self
        if(isDouble(routeName) && getDouble(routeName).isOwned() && numPlayers < 4) {
            return new Result(false, null, "Game needs at least 4 players to claim the second route of a double route");
        }
        else if(isDouble(routeName) && getDouble(routeName).isOwned() && getDouble(routeName).getOwnerId().equals(playerId)) {
            return new Result(false, null, "Players can't claim both routes of a double route");
        }
        else { return new Result(true, null, null); }
    }

    //assumes that it canClaim
    public Boolean claimRoute(String playerID, String routeName){
        newestClaimedRoute = new Pair<String,String>(routeName, playerID);
        claimedRoutes.add(new Pair<String,String>(routeName, playerID));

        return routes.get(routeName).claim(playerID); //also updates doubleRoutesIndex
    }

    public Map<String, Route> getRoutes() {
        return Collections.unmodifiableMap(routes);
    }

    public List<Pair<String, String>> getClaimedRoutes() {
        return Collections.unmodifiableList(claimedRoutes);
    }

    public List<Route> getPlayersRoutes(String playerID) {
        List<Route> playersRoutes = new ArrayList<>();

        for(String each : routes.keySet()) {
            Route route = routes.get(each);
            if(route.getOwnerId().equals(playerID)) { playersRoutes.add(route); }
        }

        return Collections.unmodifiableList(playersRoutes);
    }

    //returns pair of routeId and ownerId
    public Pair<String, String> getNewestClaimedRoute() {
        return newestClaimedRoute;
    }

    private void initRoutes() {
        routes.put("vancouver_calgary", new Route("vancouver_calgary", new City("vancouver"), new City("calgary"), 3, TrainCard.TRAIN_TYPE.WILD));
        routes.put("vancouver_seattle_first", new Route("vancouver_seattle_first", new City("vancouver"), new City("seattle"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("vancouver_seattle_second", new Route("vancouver_seattle_second", new City("vancouver"), new City("seattle"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("seattle_portland_first", new Route("seattle_portland_first", new City("seattle"), new City("portland"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("seattle_portland_second", new Route("seattle_portland_second", new City("seattle"), new City("portland"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("seattle_calgary", new Route("seattle_calgary",new City("seattle"), new City("calgary"), 4, TrainCard.TRAIN_TYPE.WILD));
        routes.put("calgary_helena", new Route("calgary_helena",new City("calgary"), new City("helena"), 4, TrainCard.TRAIN_TYPE.WILD));
        routes.put("portland_sanFran_first", new Route("portland_sanFran_first",new City("portland"), new City("sanFran"), 5, TrainCard.TRAIN_TYPE.GREEN));
        routes.put("portland_sanFran_second", new Route("portland_sanFran_second",new City("portland"), new City("sanFran"), 5, TrainCard.TRAIN_TYPE.PINK));
        routes.put("seattle_helena", new Route("seattle_helena",new City("seattle"), new City("helena"), 6, TrainCard.TRAIN_TYPE.YELLOW));
        routes.put("portland_SLC", new Route("portland_SLC",new City("portland"), new City("SLC"), 6, TrainCard.TRAIN_TYPE.BLUE));
        routes.put("sanFran_SLC_first", new Route("sanFran_SLC_first",new City("sanFran"), new City("SLC"), 5, TrainCard.TRAIN_TYPE.ORANGE));
        routes.put("sanFran_SLC_second", new Route("sanFran_SLC_second",new City("sanFran"), new City("SLC"), 5, TrainCard.TRAIN_TYPE.WHITE));
        routes.put("sanFran_LA_first", new Route("sanFran_LA_first",new City("sanFran"), new City("LA"), 3, TrainCard.TRAIN_TYPE.PINK));
        routes.put("sanFran_LA_second", new Route("sanFran_LA_second",new City("sanFran"), new City("LA"), 3, TrainCard.TRAIN_TYPE.YELLOW));
        routes.put("LA_lasVegas", new Route("LA_lasVegas",new City("LA"), new City("lasVegas"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("lasVegas_SLC", new Route("lasVegas_SLC",new City("lasVegas"), new City("SLC"), 3, TrainCard.TRAIN_TYPE.ORANGE));
        routes.put("LA_elPaso", new Route("LA_elPaso",new City("LA"), new City("elPaso"), 6, TrainCard.TRAIN_TYPE.BLACK));
        routes.put("LA_pheonix", new Route("LA_pheonix",new City("LA"), new City("pheonix"), 3, TrainCard.TRAIN_TYPE.WILD));
        routes.put("pheonix_elPaso", new Route("pheonix_elPaso",new City("pheonix"), new City("elPaso"), 3, TrainCard.TRAIN_TYPE.WILD));
        routes.put("pheonix_santaFe", new Route("pheonix_santaFe",new City("pheonix"), new City("santaFe"), 3, TrainCard.TRAIN_TYPE.WILD));
        routes.put("elPaso_santaFe", new Route("elPaso_santaFe",new City("elPaso"), new City("santaFe"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("pheonix_denver", new Route("pheonix_denver",new City("pheonix"), new City("denver"), 5, TrainCard.TRAIN_TYPE.WHITE));
        routes.put("santaFe_denver", new Route("santaFe_denver",new City("santaFe"), new City("denver"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("SLC_denver_first", new Route("SLC_denver_first",new City("SLC"), new City("denver"), 3, TrainCard.TRAIN_TYPE.YELLOW));
        routes.put("SLC_denver_second", new Route("SLC_denver_second",new City("SLC"), new City("denver"), 3, TrainCard.TRAIN_TYPE.RED));
        routes.put("helena_SLC", new Route("helena_SLC",new City("helena"), new City("SLC"), 3, TrainCard.TRAIN_TYPE.PINK));
        routes.put("helena_denver", new Route("helena_denver",new City("helena"), new City("denver"), 4, TrainCard.TRAIN_TYPE.GREEN));
        routes.put("calgary_winnipeg", new Route("calgary_winnipeg",new City("calgary"), new City("winnipeg"), 6, TrainCard.TRAIN_TYPE.WHITE));
        routes.put("helena_winnipeg", new Route("helena_winnipeg",new City("helena"), new City("winnipeg"), 4, TrainCard.TRAIN_TYPE.BLUE));
        routes.put("helena_duluth", new Route("helena_duluth",new City("helena"), new City("duluth"), 6, TrainCard.TRAIN_TYPE.ORANGE));
        routes.put("helena_omaha", new Route("helena_omaha",new City("helena"), new City("omaha"), 5, TrainCard.TRAIN_TYPE.RED));
        routes.put("denver_omaha", new Route("denver_omaha",new City("denver"), new City("omaha"), 4, TrainCard.TRAIN_TYPE.PINK));
        routes.put("denver_KC_first", new Route("denver_KC_first",new City("denver"), new City("KC"), 4, TrainCard.TRAIN_TYPE.BLACK));
        routes.put("denver_KC_second", new Route("denver_KC_second",new City("denver"), new City("KC"), 4, TrainCard.TRAIN_TYPE.ORANGE));
        routes.put("denver_oklahomaCity", new Route("denver_oklahomaCity",new City("denver"), new City("oklahomaCity"), 4, TrainCard.TRAIN_TYPE.RED));
        routes.put("santaFe_oklahomaCity", new Route("santaFe_oklahomaCity",new City("santaFe"), new City("oklahomaCity"), 3, TrainCard.TRAIN_TYPE.BLUE));
        routes.put("elPaso_oklahomaCity", new Route("elPaso_oklahomaCity",new City("elPaso"), new City("oklahomaCity"), 5, TrainCard.TRAIN_TYPE.YELLOW));
        routes.put("elPaso_dallas", new Route("elPaso_dallas",new City("elPaso"), new City("dallas"), 4, TrainCard.TRAIN_TYPE.RED));
        routes.put("elPaso_houston", new Route("elPaso_houston",new City("elPaso"), new City("houston"), 6, TrainCard.TRAIN_TYPE.GREEN));
        routes.put("winnipeg_saultStMarie", new Route("winnipeg_saultStMarie",new City("winnipeg"), new City("saultStMarie"), 6, TrainCard.TRAIN_TYPE.WILD));
        routes.put("winnipeg_duluth", new Route("winnipeg_duluth", new City("winnipeg"), new City("duluth"), 4, TrainCard.TRAIN_TYPE.BLACK));
        routes.put("duluth_saultStMarie", new Route("duluth_saultStMarie",new City("duluth"), new City("saultStMarie"), 3, TrainCard.TRAIN_TYPE.WILD));
        routes.put("duluth_omaha_first", new Route("duluth_omaha_first",new City("duluth"), new City("omaha"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("duluth_omaha_second", new Route("duluth_omaha_second",new City("duluth"), new City("omaha"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("omaha_KC_first", new Route("omaha_KC_first",new City("omaha"), new City("KC"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("omaha_KC_second", new Route("omaha_KC_second",new City("omaha"), new City("KC"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("KC_oklahomaCity_first", new Route("KC_oklahomaCity_first",new City("KC"), new City("oklahomaCity"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("KC_oklahomaCity_second", new Route("KC_oklahomaCity_second",new City("KC"), new City("oklahomaCity"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("oklahomaCity_dallas_first", new Route("oklahomaCity_dallas_first",new City("oklahomaCity"), new City("dallas"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("oklahomaCity_dallas_second", new Route("oklahomaCity_dallas_second",new City("oklahomaCity"), new City("dallas"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("dallas_houston_first", new Route("dallas_houston_first",new City("dallas"), new City("houston"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("dallas_houston_second", new Route("dallas_houston_second",new City("dallas"), new City("houston"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("houston_newOrleans", new Route("houston_newOrleans",new City("houston"), new City("newOrleans"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("dallas_littleRock", new Route("dallas_littleRock",new City("dallas"), new City("littleRock"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("oklahomaCity_littleRock", new Route("oklahomaCity_littleRock",new City("oklahomaCity"), new City("littleRock"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("KC_saintLouis_first", new Route("KC_saintLouis_first",new City("KC"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.BLUE));
        routes.put("KC_saintLouis_second", new Route("KC_saintLouis_second",new City("KC"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.PINK));
        routes.put("omaha_chicago", new Route("omaha_chicago",new City("omaha"), new City("chicago"), 4, TrainCard.TRAIN_TYPE.BLUE));
        routes.put("duluth_chicago", new Route("duluth_chicago",new City("duluth"), new City("chicago"), 3, TrainCard.TRAIN_TYPE.RED));
        routes.put("newOrleans_littleRock", new Route("newOrleans_littleRock",new City("newOrleans"), new City("littleRock"), 3, TrainCard.TRAIN_TYPE.GREEN));
        routes.put("littleRock_saintLouis", new Route("littleRock_saintLouis",new City("littleRock"), new City("saintLouis"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("newOrleans_atlanta_first", new Route("newOrleans_atlanta_first",new City("newOrleans"), new City("atlanta"), 4, TrainCard.TRAIN_TYPE.YELLOW));
        routes.put("newOrleans_atlanta_second", new Route("newOrleans_atlanta_second",new City("newOrleans"), new City("atlanta"), 4, TrainCard.TRAIN_TYPE.ORANGE));
        routes.put("littleRock_nashville", new Route("littleRock_nashville",new City("littleRock"), new City("nashville"), 3, TrainCard.TRAIN_TYPE.WHITE));
        routes.put("saintLouis_nashville", new Route("saintLouis_nashville",new City("saintLouis"), new City("nashville"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("nashville_atlanta", new Route("nashville_atlanta",new City("nashville"), new City("atlanta"), 1, TrainCard.TRAIN_TYPE.WILD));
        routes.put("saintLouis_chicago_first", new Route("saintLouis_chicago_first",new City("saintLouis"), new City("chicago"), 2, TrainCard.TRAIN_TYPE.GREEN));
        routes.put("saintLouis_chicago_second", new Route("saintLouis_chicago_second",new City("saintLouis"), new City("chicago"), 2, TrainCard.TRAIN_TYPE.WHITE));
        routes.put("duluth_toronto", new Route("duluth_toronto",new City("duluth"), new City("toronto"), 6, TrainCard.TRAIN_TYPE.PINK));
        routes.put("saultStMarie_montreal", new Route("saultStMarie_montreal",new City("saultStMarie"), new City("montreal"), 5, TrainCard.TRAIN_TYPE.BLACK));
        routes.put("saultStMarie_toronto", new Route("saultStMarie_toronto",new City("saultStMarie"), new City("toronto"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("toronto_montreal", new Route("toronto_montreal",new City( "toronto"), new City("montreal"), 3, TrainCard.TRAIN_TYPE.WILD));
        routes.put("chicago_toronto", new Route("chicago_toronto",new City( "chicago"), new City("toronto"), 4, TrainCard.TRAIN_TYPE.WHITE));
        routes.put("montreal_boston_first", new Route("montreal_boston_first", new City("montreal"), new City("boston"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("montreal_boston_second", new Route("montreal_boston_second", new City("montreal"), new City("boston"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("chicago_pittsburg_first", new Route("chicago_pittsburg_first", new City("chicago"), new City("pittsburg"), 3, TrainCard.TRAIN_TYPE.ORANGE));
        routes.put("chicago_pittsburg_second", new Route("chicago_pittsburg_second", new City("chicago"), new City("pittsburg"), 3, TrainCard.TRAIN_TYPE.BLACK));
        routes.put("saintLouis_pittsburg", new Route("saintLouis_pittsburg", new City("saintLouis"), new City("pittsburg"), 5, TrainCard.TRAIN_TYPE.GREEN));
        routes.put("nashville_pittsburg", new Route("nashville_pittsburg", new City("nashville"), new City("pittsburg"), 4, TrainCard.TRAIN_TYPE.YELLOW));
        routes.put("nashville_raleigh", new Route("nashville_raleigh", new City("nashville"), new City("raleigh"), 3, TrainCard.TRAIN_TYPE.BLACK));
        routes.put("newOrleans_miami", new Route("newOrleans_miami", new City("newOrleans"), new City("miami"), 6, TrainCard.TRAIN_TYPE.RED));
        routes.put("atlanta_miami", new Route("atlanta_miami", new City("atlanta"), new City("miami"), 5, TrainCard.TRAIN_TYPE.BLUE));
        routes.put("atlanta_charleston", new Route("atlanta_charleston", new City("atlanta"), new City("charleston"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("atlanta_raleigh_first", new Route("atlanta_raleigh_first", new City("atlanta"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("atlanta_raleigh_second", new Route("atlanta_raleigh_second", new City("atlanta"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("miami_charleston", new Route("miami_charleston", new City("miami"), new City("charleston"), 4, TrainCard.TRAIN_TYPE.PINK));
        routes.put("charleston_raleigh", new Route("charleston_raleigh", new City("charleston"), new City("raleigh"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("raleigh_pittsburg", new Route("raleigh_pittsburg", new City("raleigh"), new City("pittsburg"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("toronto_pittsburg", new Route("toronto_pittsburg", new City( "toronto"), new City("pittsburg"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("raleigh_washington_first", new Route("raleigh_washington_first", new City("raleigh"), new City("washington"), 3, TrainCard.TRAIN_TYPE.WILD));
        routes.put("raleigh_washington_second", new Route("raleigh_washington_second", new City("raleigh"), new City("washington"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("pittsburg_washington", new Route("pittsburg_washington", new City("pittsburg"), new City("washington"), 2, TrainCard.TRAIN_TYPE.WILD));
        routes.put("washington_newYork_first", new Route("washington_newYork_first", new City("washington"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.ORANGE));
        routes.put("washington_newYork_second", new Route("washington_newYork_second", new City("washington"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.BLACK));
        routes.put("pittsburg_newYork_first", new Route("pittsburg_newYork_first", new City("pittsburg"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.GREEN));
        routes.put("pittsburg_newYork_second", new Route("pittsburg_newYork_second", new City("pittsburg"), new City("newYork"), 2, TrainCard.TRAIN_TYPE.WHITE));
        routes.put("montreal_newYork", new Route("montreal_newYork", new City("montreal"), new City("newYork"), 3, TrainCard.TRAIN_TYPE.BLUE));
        routes.put("newYork_boston_first", new Route("newYork_boston_first", new City("newYork"), new City("boston"), 2, TrainCard.TRAIN_TYPE.YELLOW));
        routes.put("newYork_boston_second", new Route("newYork_boston_second", new City("newYork"), new City("boston"), 2, TrainCard.TRAIN_TYPE.RED));
    }

    private void initDoubleRoutesIndex() {
        for(String routeName: routes.keySet()) {
            if(routeName.contains("_first")) {
                doubleRoutesIndex.put(getBaseName(routeName), new Pair<String, String>(routes.get(getBaseName(routeName) + "_first").NAME, routes.get(getBaseName(routeName) + "_second").NAME));
            }
        }
    }

    public boolean isDouble(String routeName) {
        return doubleRoutesIndex.get(getBaseName(routeName)) != null;
    }

    private Route getDouble(String routeName) {
        if(!isDouble(routeName)) { return null; }
        Pair<String, String> routePair = doubleRoutesIndex.get(getBaseName(routeName));
        return routePair.first.equals(routeName) ? routes.get(routePair.second) : routes.get(routePair.first);
    }

    private String getBaseName(String routeName) {
        return routeName.replace("_first", "").replace("_second", ""); //remove tag
    }

    public String getOwner(String routeId) {
        return routes.get(routeId).getOwnerId();
    }

    public Integer getLength(String routeName) {
        return routes.get(routeName).LENGTH;
    }

    public String toString(String routeName) {
        return routes.get(routeName).toString();
    }

    public Integer getPoints(String routeName) {
        return routes.get(routeName).getPoints();
    }

    public Map<String, Pair<String, String>> getDoubleRoutesIndex() {
        return Collections.unmodifiableMap(doubleRoutesIndex);
    }

    public TrainCard.TRAIN_TYPE getType(String routeName) {
        return routes.get(routeName).TYPE;
    }
}
