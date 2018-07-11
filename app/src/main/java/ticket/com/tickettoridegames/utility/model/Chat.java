package ticket.com.tickettoridegames.utility.model;

public class Chat {
    private String id;
    private String message;

    public Chat(String playerId, String message){
        this.id = playerId;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
