package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.IJoinPresenter;
import ticket.com.tickettoridegames.client.presenter.JoinPresenter;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public class JoinActivity extends AppCompatActivity implements IJoinView{

    IJoinPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        presenter = new JoinPresenter(this);
    }

    public List<Game> getGames(){
        return null;
    }

    public void addGame(Game newGame){}

    public List<Player> getPlayers(String gameID){
        return null;
    }

    public void setPlayers(List<Player> players){}

    public void setPlayerCount(String gameID){}

    public Integer getPlayerCount(String gameID){
        return 2;
    }

    public void displayMessage(String message){}
}
