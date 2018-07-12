package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.Set;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.presenter.ILobbyPresenter;
import ticket.com.tickettoridegames.client.presenter.LobbyPresenter;
import ticket.com.tickettoridegames.utility.model.Chat;

public class LobbyActivity extends AppCompatActivity implements ILobbyView{

    private ILobbyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        presenter = new LobbyPresenter(this);
    }

    @Override
    public void setPlayers(Set<String> players){}

    @Override
    public void displayChat(String message){}

    @Override
    public void sendChat(String message){}

    @Override
    public void setChat(List<Chat> chats){}

    @Override
    public void displayMessage(String toast){}

    @Override
    public String getGameID(){return "";}
}
