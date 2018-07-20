package ticket.com.tickettoridegames.utility.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameMap {

    private Set<Route> routes;
    private Map<String, Route> claimedRoutes;

    public GameMap(){
        routes = new HashSet<>();
        claimedRoutes = new HashMap<>();
    }

    public boolean claimRoute(String playerID, Route route){
        if (claimedRoutes.containsValue(route)){
            return false;
        }
        else {
            claimedRoutes.put(playerID, route);
            return true;
        }
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Map<String, Route> getClaimedRoutes() {

        return claimedRoutes;
    }

    public void setClaimedRoutes(Map<String, Route> claimedRoutes) {
        this.claimedRoutes = claimedRoutes;
    }
}
