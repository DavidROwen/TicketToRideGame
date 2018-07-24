package ticket.com.tickettoridegames.client.view;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager pager = (ViewPager)findViewById(R.id.mediaViewPager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(),getApplicationContext(), new String[]{"Map","Stats","Assets"},3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.swipeTabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayMessage(String message) {}
}