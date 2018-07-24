package ticket.com.tickettoridegames.client.view.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ticket.com.tickettoridegames.client.view.Fragments.AssetsFragment;
import ticket.com.tickettoridegames.client.view.Fragments.StatsFragment;
import ticket.com.tickettoridegames.client.view.Fragments.MapFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private int PageCount;
    private String[] PageTitle;
    private Context context;

    private ArrayList<Fragment> tabFragments;

    public TabAdapter(FragmentManager fm, Context context, String[] PageTitle, int PageCount) {

        super(fm);
        tabFragments = new ArrayList<>();
        tabFragments.add(new MapFragment().newInstance());
        tabFragments.add(new StatsFragment().newInstance());
        tabFragments.add(new AssetsFragment().newInstance());

        this.context = context;
        this.PageTitle = PageTitle;
        this.PageCount = PageCount;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        Fragment fragment;
        fragment= tabFragments.get(position);
        //incase you want to pass some info to fragment
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return PageCount;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return PageTitle[position];
    }
}