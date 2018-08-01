package ticket.com.utility.model;

public class PlayerAction {

    private String userName;
    private String action;

    public PlayerAction(String userName, String action) {
        this.userName = userName;
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return userName + ": " + action;
    }
}
