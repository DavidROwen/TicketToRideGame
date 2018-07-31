package ticket.com.tickettoridegames.client.State;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.utility.web.Result;

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

    public Result claimRoute(ClientModel cm) {
        return new Result(false, null, "Game has already ended");
    }

    public void drawFromBank(ClientModel cm) {
        //nope
    }
}
