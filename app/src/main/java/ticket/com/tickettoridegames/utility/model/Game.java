package ticket.com.tickettoridegames.utility.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Game {

    private Map<String, Player> players;
    private List<Chat> chatList;
    private String id;
    private String name;

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    private int  maxPlayers;
    private int numberOfPlayers;

    public Game(String name, int numberOfPlayers){
        //collection subject to change
        this.players = new HashMap<>();
        this.chatList = new ArrayList<>();
        this.name = name;
        this.maxPlayers = numberOfPlayers;
        this.numberOfPlayers = 0;
        this.id = UUID.randomUUID().toString();
    }
    public Game(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getPlayers(){
        return players.keySet();
    }

    public boolean addPlayers(Player p){
        if(numberOfPlayers != maxPlayers) {
            players.put(p.getId(), p);
            numberOfPlayers++;
            return true;
        }
        else{
            return false;
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
}
