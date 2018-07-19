package ticket.com.tickettoridegames.utility.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import sun.security.krb5.internal.crypto.Des;

public class Game {

    //General game data
    private Map<String, Player> players;
    private List<Chat> chatList;
    private String id;
    private String name;
    private int  maxPlayers;
    private int numberOfPlayers;
    private boolean isStarted;

    // Stores the playerIDs in turn order
     private List<String> turnOrder;

     // Map data
    private GameMap map;
    private Set<DestinationCard> destinationCards;
    private Map<String, DestinationCard> claimedDestinationCards;

    // Stores player actions viewed in the stats history tab
    private List<PlayerAction> gameHistory;

    public Game(){}

    public Game(String name, int numberOfPlayers){
        //collection subject to change
        this.players = new HashMap<>();
        this.chatList = new ArrayList<>();
        this.name = name;
        this.maxPlayers = numberOfPlayers;
        this.numberOfPlayers = 0;
        this.id = UUID.randomUUID().toString();
        this.isStarted = false;
        setupRoutes();
    }

    public void setupRoutes(){
        // this function can set the default route data
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getPlayersId(){
        return players.keySet();
    }

    public String getPlayerNamesString() {
        String string = "";
        for (String key:players.keySet()) {
            string += players.get(key).getUsername() + " ";
        }
        return string;
    }

    public Set<String> getPlayerNames() {
        Set<String> names = new HashSet<>();
        for (String key:players.keySet()) {
            names.add(players.get(key).getUsername());
        }
        return names;
    }

    public boolean addPlayers(Player p){
        if(numberOfPlayers != maxPlayers) {
            //check if already in game.
            if(players.containsKey(p.getId())){
                //call the join game function
                return true;
            }
            else{
                players.put(p.getId(), p);
                numberOfPlayers++;
                return true;
            }
        }
        else{
            if(players.containsKey(p.getId())){
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean removePlayer(Player p){
        if (players.containsValue(p)){
            players.remove(p);
            return true;
        }
        else {
            return false;
        }
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public void addToChat(Chat c){
        chatList.add(c);
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
