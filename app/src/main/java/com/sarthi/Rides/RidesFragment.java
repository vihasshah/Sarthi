package com.sarthi.Rides;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sarthi.Helper.Const;
import com.sarthi.Helper.DataBaseHandler;
import com.sarthi.Login.LoginActivity;
import com.sarthi.Pooler.RideDetails;
import com.sarthi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vihas on 28-03-2017.
 */

public class RidesFragment extends Fragment {
    ListView listView;
    DataBaseHandler handler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ride,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new DataBaseHandler(getActivity());
        listView = (ListView) getActivity().findViewById(R.id.ride_list_view);
//        RideModel model = new RideModel();
//        model.setFromAddress("Ghandhinagar");
//        model.setToAddress("Ahmedabad");
//        model.setPersonName("Kunal Jhaveri");
//        model.setTime("12:00");
//        model.setFair("120");
//        List<RideModel> modelList = new ArrayList<>();
//        modelList.add(model);
        List<RideDetails> list = handler.getAllRides();
        if(list!=null) {
            Log.d(Const.TAG, "size:" + list.size());
            RideAdapter adapter = new RideAdapter(getActivity(), handler.getAllRides());
            listView.setAdapter(adapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logout,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_logout_text){
            getActivity().getSharedPreferences(Const.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            // clear backstack and create new task
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
