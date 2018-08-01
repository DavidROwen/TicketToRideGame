package ticket.com.utility.model;

import java.util.UUID;

public class User {
    private String username;
    private String password;
    private String id;
    private String gameId;
    private String color;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID().toString();
    }
    public User(){}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
