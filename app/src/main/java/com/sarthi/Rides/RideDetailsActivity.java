package com.sarthi.Rides;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sarthi.Helper.Const;
import com.sarthi.Pooler.RideDetails;
import com.sarthi.R;

public class RideDetailsActivity extends AppCompatActivity {
    TextView fromTV,toTV,timeTV,fairTV,nameTV,contactTV,licenceTV,carNumTV,carModelTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // home button display in action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // init

        fromTV = (TextView) findViewById(R.id.ride_details_from_address);
        toTV = (TextView) findViewById(R.id.ride_details_to_address);
        timeTV = (TextView) findViewById(R.id.ride_details_time);
        fairTV = (TextView) findViewById(R.id.ride_details_fair);
        nameTV = (TextView) findViewById(R.id.ride_details_rider_name);
        contactTV = (TextView) findViewById(R.id.ride_details_contact);
        licenceTV = (TextView) findViewById(R.id.ride_detials_licence_number);
        carNumTV = (TextView) findViewById(R.id.ride_details_cer_number);
        carModelTV = (TextView) findViewById(R.id.ride_details_car_model);

        //get class from intent

        RideDetails model = (RideDetails) getIntent().getSerializableExtra(Const.INTENT_CLASS_PASS);

        // setting title for action bar
        getSupportActionBar().setTitle(model.getSrc_name_x()+"-"+model.getDest_name_x());


        // setting values of text fields
        fromTV.setText(model.getSrc_name_x());
        toTV.setText(model.getDest_name_x());
        timeTV.setText(model.getHour()+":"+model.getMin());
        fairTV.setText(model.getPrice());
        nameTV.setText(model.getName());
        contactTV.setText(String.valueOf(model.getContact()));
        licenceTV.setText(model.getLicenceNumber());
        carNumTV.setText(model.getCarNumber());
        carModelTV.setText(model.getCarModel());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
