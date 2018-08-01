package ticket.com.tickettoridegames.client.view;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.client.view.Adapter.TabAdapter;

public class GamePlayActivity extends AppCompatActivity implements IGamePlayActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        ViewPager pager = findViewById(R.id.mediaViewPager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(),getApplicationContext(), new String[]{"Map","Stats","Assets"},3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.swipeTabs);
        tabLayout.setupWithViewPager(pager);
    }


    @Override
    public void changeView(){
        Intent intent = new Intent(GamePlayActivity.this, EndGameActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayMessage(String message) {}
}