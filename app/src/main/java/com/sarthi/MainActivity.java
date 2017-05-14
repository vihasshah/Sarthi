package com.sarthi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sarthi.Helper.Const;
import com.sarthi.Helper.DataBaseHandler;
import com.sarthi.Pooler.PoolerFragment;
import com.sarthi.Rides.RidesFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int g_id = 1;
    public static int p_id = 1;
    public static int num = 1;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    DataBaseHandler handler = new DataBaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle tab layout
        tabLayout = (TabLayout) findViewById(R.id.home_main_tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

        int[] tabIcons = new int[]{R.drawable.ic_ride,R.drawable.ic_person};
        // create list of Fragments
        List<Fragment> listFragments = new ArrayList<>();
        listFragments.add(new RidesFragment());
        listFragments.add(new PoolerFragment());

        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        pagerAdapter = new ViewPagerAdapter(getFragmentManager(),listFragments);
        viewPager.setAdapter(pagerAdapter);
        // setupwithviewageer method will create auto 3 tab
        tabLayout.setupWithViewPager(viewPager);
        // get tabs and set icons
        handleTabLayout(tabLayout,tabIcons,new String[]{"Rider","Pooler"});
        //add view pager change listner
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // load fragment on base on position
                viewPager.setCurrentItem(position);
                tabLayout.getTabAt(position).select();
                if(position == 0){
                    Log.d(Const.TAG,"updating fragment....");
                    Fragment fragment = pagerAdapter.getItem(position);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.detach(fragment).attach(fragment).commit();
                    Log.d(Const.TAG,"fragment updated....");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /***
     * handling all tab layouts
     * @param tabLayout
     * @param tabIcons array
     */
    private void handleTabLayout(TabLayout tabLayout, int[] tabIcons,String[] titles) {
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE,Color.WHITE);
        int tabCount = tabIcons.length;
        for(int i = 0 ; i < tabCount ; i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setIcon(tabIcons[i]);
            tab.setText(titles[i]);
        }
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
//            Place place = PlacePicker.getPlace(data, this);
//            src_txt.setTextColor(Color.BLACK);
//            src_txt.setText(place.getName().toString());
//            LatLng ll1 = place.getLatLng();
//            src_lat_x = ll1.latitude;
//            src_long_x = ll1.longitude;
//            src_name_x = place.getName().toString();
//        }
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            Place place = PlacePicker.getPlace(data, this);
//            dest_txt.setTextColor(Color.BLACK);
//            dest_txt.setText(place.getName().toString());
//            LatLng ll1 = place.getLatLng();
//            dest_lat_x = ll1.latitude;
//            dest_long_x = ll1.longitude;
//            dest_name_x = place.getName().toString();
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getFragmentManager().findFragmentByTag(PoolerFragment.class.getSimpleName());
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
