package ticket.com.tickettoridegames.client.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ticket.com.tickettoridegames.R;
import ticket.com.tickettoridegames.utility.model.PlayerStats;

import static android.graphics.Color.GRAY;

public class EndGameActivity extends AppCompatActivity implements IEndGameView{

    //Variables
    List<PlayerStats> playerStats;

    //Widgets
    TextView winnerNameText;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
    }

    @Override
    public void setPlayerStats(List<PlayerStats> playerStats){
        this.playerStats = playerStats;
        myRecyclerView = (RecyclerView) findViewById(R.id.statsrecyclerview);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new StatsAdapter(playerStats);
        myRecyclerView.setAdapter(myAdapter);
    }

//    @Override
//    public void setLongestTrainAward(String player){
//
//    }

    @Override
    public void setWinner(String player){
        winnerNameText = (TextView) findViewById(R.id.PlayerName);
        winnerNameText.setText(player);
    }

    class StatsAdapter extends RecyclerView.Adapter<StatsCustomViewHolder> {

        int selected_position = 0; // You have to set this globally in the StatsAdapter class
        List<PlayerStats> playerStats;
        PlayerStats[] playerArray;


        public StatsAdapter(List<PlayerStats> playerStats) {
            this.playerStats = playerStats;
            this.playerArray = playerStats.toArray(new PlayerStats[0]);
        }

        @Override
        public StatsCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.custom_stats_layout, viewGroup, false);

            return new StatsCustomViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StatsCustomViewHolder holder, int i) {
            holder.bindResult(playerArray[i]);
        }

        @Override
        public int getItemCount() {
            return playerArray.length;
        }
    }

    class StatsCustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView line1;
        protected TextView line2;
        protected TextView line3;
        protected TextView line4;
        protected TextView line5;

        public StatsCustomViewHolder(View v) {
            super(v);
            line1 = (TextView)  v.findViewById(R.id.textView1);
            line2 = (TextView)  v.findViewById(R.id.textView2);
            line3 = (TextView)  v.findViewById(R.id.textView3);
            line4 = (TextView)  v.findViewById(R.id.textView4);
            line5 = (TextView)  v.findViewById(R.id.textView5);
        }

        public void bindResult(PlayerStats playerStat){
            line1.setText(String.valueOf(playerStat.getName()));
            line1.setTextColor(GRAY);
            line1.setBackgroundColor(playerStat.getColor());
            line2.setText(String.valueOf(playerStat.getPoints()));
            line3.setText(String.valueOf(playerStat.getNumberOfPieces()));
            line4.setText(String.valueOf(playerStat.getNumberOfCards()));
            line5.setText(String.valueOf(playerStat.getNumberOfRoutes()));
        }
    }

}
