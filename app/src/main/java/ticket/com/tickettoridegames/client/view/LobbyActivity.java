package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.ILobbyPresenter;
import ticket.com.tickettoridegames.client.presenter.ILoginPresenter;
import ticket.com.tickettoridegames.client.presenter.LobbyPresenter;
import ticket.com.tickettoridegames.utility.model.Player;

public class LobbyActivity extends AppCompatActivity implements ILobbyView{

    ILobbyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        presenter = new LobbyPresenter(this);
    }

    public void setPlayers(List<Player> players){}

    public void displayChat(String message){}

    public void sendChat(String message){}

    public void displayMessage(String toast){}
}
