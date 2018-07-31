package ticket.com.tickettoridegames.utility.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameMap {

    private Map<String, Route> routes = new HashMap<>(); //key is route name

    public GameMap(){}

    public boolean claimRoute(String playerID, Route route){
        return true; //todo init routes here
    }

    public Map<String, Route> getRoutes() {
        return Collections.unmodifiableMap(routes);
    }

    public List<Route> getClaimedRoutes() {
        List<Route> claimed = new ArrayList<>();

        for(String each : routes.keySet()) {
            Route route = routes.get(each);
            if(route.isOwned()) { claimed.add(route); }
        }

        return Collections.unmodifiableList(claimed);
    }
}
