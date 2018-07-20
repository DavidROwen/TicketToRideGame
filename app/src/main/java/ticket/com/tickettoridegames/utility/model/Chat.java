package ticket.com.tickettoridegames.utility.model;

public class Chat {
    private String username;
    private String message;

    public Chat(String username, String message){
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setId(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
