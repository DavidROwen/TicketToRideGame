package ticket.com.tickettoridegames.utility.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Game {

    private Collection<Player> players;
    private String id;
    private String name;
    private int numberOfPlayers;

    public Game(String name, int numberOfPlayers){
        //collection subject to change
        this.players = new HashSet<>();
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<Player> getPlayers(){
        return players;
    }

    public void addPlayers(Player p){
        players.add(p);
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
}
