package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.IJoinPresenter;
import ticket.com.tickettoridegames.client.presenter.JoinPresenter;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;

public class JoinActivity extends AppCompatActivity implements IJoinView{

    private IJoinPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        presenter = new JoinPresenter(this);
    }

    @Override
    public Map<String, Game> getGames(){
        return null;
    }

    @Override
    public void setGames(Map<String, Game> games){}

    @Override
    public void addGame(Game newGame){}

    @Override
    public List<Player> getPlayers(String gameID){
        return null;
    }

    @Override
    public void setPlayers(List<Player> players){}

    @Override
    public void setPlayerCount(String gameID){}

    @Override
    public Integer getPlayerCount(String gameID){
        return 2;
    }

    // Join was successful go to the join view.
    @Override
    public void changeView(){}

    @Override
    public void displayMessage(String message){}
}
