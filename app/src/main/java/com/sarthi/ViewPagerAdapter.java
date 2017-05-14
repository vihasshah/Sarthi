package com.sarthi;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;


//import com.hashchains.ui.Fragments.BtcFragment;
//import com.hashchains.ui.Fragments.EthereumFragment;
//import com.hashchains.ui.Fragments.InrFragment;

import java.util.List;

/**
 * Created by Dell on 01-02-2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> listFragments;
    FragmentManager manager;
    public ViewPagerAdapter(FragmentManager manager, List<Fragment> listFragments) {
        super(manager);
        this.listFragments = listFragments;
        this.manager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        // return current fragment
//        tabLayout.setScrollPosition(position,0f,true);
        //get Fragment
        Fragment selectedfragment = listFragments.get(position);
        // add fragments to backstack
//        Fragment fragment = manager.findFragmentByTag(selectedfragment.getClass().getSimpleName());
//        if(fragment == null) {
//            //adding to backstack
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.addToBackStack(selectedfragment.getClass().getSimpleName());
//            transaction.commit();
//        }
        return selectedfragment;
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
}
