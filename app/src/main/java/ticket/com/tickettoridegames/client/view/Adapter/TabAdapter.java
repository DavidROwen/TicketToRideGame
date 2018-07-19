package ticket.com.tickettoridegames.client.view.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ticket.com.tickettoridegames.client.view.Fragments.ButtonFragment;
import ticket.com.tickettoridegames.client.view.Fragments.ImageFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private int PageCount;
    private String[] PageTitle;
    private Context context;

    private ArrayList<Fragment> tabFragments;

    public TabAdapter(FragmentManager fm, Context context, String[] PageTitle, int PageCount) {

        super(fm);
        tabFragments = new ArrayList<Fragment>();
        tabFragments.add(new ImageFragment().newInstance());
        tabFragments.add(new ButtonFragment().newInstance());

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