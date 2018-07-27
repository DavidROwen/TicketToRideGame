package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;

public class GameOverState extends PlayerState {

    private static GameOverState instance = new GameOverState();
    public static GameOverState getInstance(){
        return instance;
    }
    private GameOverState(){}

    public void drawTrainCard(ClientModel cm){
        //can't the game is over
    }

    public void drawDestinationCard(ClientModel cm){
        //gameover. can't draw
    }

    public void changeTurn(ClientModel cm) {
        //no turn to change to.
    }

    public void claimRoute(ClientModel cm) {
        //can't do that
    }

    public void drawFromBank(ClientModel cm) {
        //nope
    }
}
