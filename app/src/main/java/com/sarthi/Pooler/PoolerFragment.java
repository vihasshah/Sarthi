package com.sarthi.Pooler;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sarthi.Helper.Const;
import com.sarthi.Helper.DataBaseHandler;
import com.sarthi.Helper.GPSTracker;
import com.sarthi.R;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Vihas on 28-03-2017.
 */

public class PoolerFragment extends Fragment {
//    public static int g_id = 1;
//    public static int p_id = 1;
    Button btn_src;
    Button btn_dest;
    TextView name;
    TextView age;
    TextView contact;
    TextView time_txt;
    TextView date_txt;
    Button btn_time;
    Button btn_date;
    Button btn_fully_done;
    EditText src_txt;
    EditText dest_txt;
    EditText priceEt;
    EditText licenceET, carNumberET,carModelET;
    final Calendar c = Calendar.getInstance();
    int hour;
    int min;
    int day;
    int month;
    int year_x;
    RadioGroup rbg_gender;
    RadioGroup rbg_pref;
    int Source_PICKER_REQUEST = 0;
    int Destination_PICKER_REQUEST = 1;
    double curr_long;
    double curr_lat;
    GPSTracker gps;
    LatLngBounds bound;
    String gender_x;
    String pref_comp_x;
    double src_lat_x = -1;
    double src_long_x = -1;
    double dest_lat_x = -1;
    double dest_long_x = -1;
    DataBaseHandler handler;
    String src_name_x;
    String dest_name_x;
    String img_path = "none";
    // latitude and longitude
    double src_lat_str;
    double src_long_str;
    double dest_lat_str;
    double dest_long_str;
    String selectedPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pooler,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new DataBaseHandler(getActivity());
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            curr_lat = gps.getLatitude();
            curr_long = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
        bound = new LatLngBounds(new LatLng(curr_lat - 0.005, curr_long - 0.005), new LatLng(curr_lat + 0.005, curr_long + 0.005));
        btn_src = (Button) getActivity().findViewById(R.id.src_place_btn);
        btn_dest = (Button) getActivity().findViewById(R.id.dest_place_btn);
        src_txt = (EditText) getActivity().findViewById(R.id.src_place_txt);
        dest_txt = (EditText) getActivity().findViewById(R.id.dest_place_txt);
        name = (TextView) getActivity().findViewById(R.id.name_txt11);
        age = (TextView) getActivity().findViewById(R.id.age_txt11);
        contact = (TextView) getActivity().findViewById(R.id.contact_txt11);
        btn_time = (Button) getActivity().findViewById(R.id.pooler_btn_time);
        btn_date = (Button) getActivity().findViewById(R.id.pooler_btn_date);
        year_x = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        priceEt = (EditText) getActivity().findViewById(R.id.pooler_et_price);
        time_txt = (TextView) getActivity().findViewById(R.id.time_txt);
        time_txt.setText(String.valueOf(hour + ":" + min));
        date_txt = (TextView) getActivity().findViewById(R.id.date_txt);
        date_txt.setText(String.valueOf(day + "/" + (month+1) + "/" + year_x));
//        btn_fully_done = (Button) getActivity().findViewById(R.id.btn_save_jrny_full);
        rbg_gender = (RadioGroup) getActivity().findViewById(R.id.pooler_gender_radiogroup);
        rbg_pref = (RadioGroup) getActivity().findViewById(R.id.rbg_pref_x);
        // car number and model init
        licenceET = (EditText) getActivity().findViewById(R.id.pooler_et_licence_number);
        carNumberET = (EditText) getActivity().findViewById(R.id.pooler_et_car_number);
        carModelET = (EditText) getActivity().findViewById(R.id.pooler_et_car_model);

        onTimePickListener();
        onDatePickListener();
//        OnClickListener2();
//        dest_placeListener();
//        src_placeListener();
        onSourcePickListener();
        onDestinationListner();
    }

    private void onDestinationListner() {
        dest_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LocationPickerActivity.class);
                startActivityForResult(i, 112);
            }
        });
    }

    // get source code
    private void onSourcePickListener() {
        src_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LocationPickerActivity.class);
                startActivityForResult(i, 111);
            }
        });

    }

    public void onTimePickListener() {
        btn_time.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        TimePickerDialog dialog = (TimePickerDialog) showDialog(0);
//                        dialog.show();
                        showDialog(0);
                    }
                }
        );
    }

    public void onDatePickListener() {
        btn_date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        DatePickerDialog dialog = (DatePickerDialog) showDialog(1);
//                        dialog.show();
                        showDialog(1);
                    }
                }
        );
    }


    protected TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (year_x == c.get(Calendar.YEAR) && month == (c.get(Calendar.MONTH) + 1) && day == c.get(Calendar.DAY_OF_MONTH)) {
                if (hourOfDay > c.get(Calendar.HOUR_OF_DAY)) {
                    hour = hourOfDay;
                    min = minute;
                } else if (hourOfDay == c.get(Calendar.HOUR_OF_DAY) && minute >= c.get(Calendar.MINUTE)) {
                    hour = hourOfDay;
                    min = minute;
                } else {
                    Toast.makeText(getActivity(), "Please choose a Valid Time !", Toast.LENGTH_SHORT).show();
                    showDialog(0);
                }
            } else {
                hour = hourOfDay;
                min = minute;
            }
            time_txt.setText(String.valueOf(hour + ":" + min));
        }
    };
    protected DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (year > c.get(Calendar.YEAR)) {
                year_x = year;
                month = monthOfYear+1;
                day = dayOfMonth;
                date_txt.setText(String.valueOf(day + "/" + (month) + "/" + year_x));
            } else if (year == c.get(Calendar.YEAR) && monthOfYear > c.get(Calendar.MONTH)) {
                year_x = year;
                month = monthOfYear+1;
                day = dayOfMonth;
                date_txt.setText(String.valueOf(day + "/" + (month) + "/" + year_x));
            } else if (year == c.get(Calendar.YEAR) && monthOfYear == c.get(Calendar.MONTH) && dayOfMonth > c.get(Calendar.DAY_OF_MONTH)) {
                year_x = year;
                month = monthOfYear+1;
                day = dayOfMonth;
                date_txt.setText(String.valueOf(day + "/" + (month) + "/" + year_x));
            } else if (year == c.get(Calendar.YEAR) && monthOfYear == c.get(Calendar.MONTH) && dayOfMonth == c.get(Calendar.DAY_OF_MONTH)) {
                if (hour > c.get(Calendar.HOUR_OF_DAY)) {
                    year_x = year;
                    month = monthOfYear+1;
                    day = dayOfMonth;
                    date_txt.setText(String.valueOf(day + "/" + (month) + "/" + year_x));
                } else if (hour == c.get(Calendar.HOUR_OF_DAY) && min > c.get(Calendar.MINUTE)) {
                    year_x = year;
                    month = monthOfYear+1;
                    day = dayOfMonth;
                    date_txt.setText(String.valueOf(day + "/" + (month) + "/" + year_x));
                } else {
                    Toast.makeText(getActivity(), "Please choose a Valid Time", Toast.LENGTH_SHORT).show();
                    date_txt.setText(String.valueOf(day + "/" + (month) + "/" + year_x));
                    showDialog(0);
                }
            } else {
                Toast.makeText(getActivity(), "Please choose a Valid Date", Toast.LENGTH_SHORT).show();
                date_txt.setText(String.valueOf(day + "/" + (month) + "/" + year_x));
                showDialog(1);
            }
        }
    };


    public void showDialog(int id) {
        if (id == 0)
             new TimePickerDialog(getActivity(), time_listener, hour, min, false).show();
        if (id == 1)
            new DatePickerDialog(getActivity(), date_listener, year_x, month, day).show();
//        return null;
    }
    public boolean validate() {
        boolean flag = true;
        if (name.getText().toString().isEmpty()) {
            flag = false;
            name.setError("Enter Name");
        }
        if (age.getText().toString().isEmpty()) {
            flag = false;
            age.setError("Enter Age");
        }
        if (contact.getText().toString().isEmpty()) {
            flag = false;
            contact.setError("Enter Contact No.");
        }
        if (src_txt.getText().toString().isEmpty()) {
            flag = false;
            contact.setError("Enter Source Address");
        }
        if (dest_txt.getText().toString().isEmpty()) {
            flag = false;
            contact.setError("Enter Destination Address");
        }
        if (priceEt.getText().toString().isEmpty()) {
            flag = false;
            contact.setError("Enter Price");
        }
        if (licenceET.getText().toString().isEmpty()) {
            flag = false;
            contact.setError("Enter Licence Number");
        }
        if (carNumberET.getText().toString().isEmpty()) {
            flag = false;
            contact.setError("Enter Car number");
        }
        if (carModelET.getText().toString().isEmpty()) {
            flag = false;
            contact.setError("Enter car model");
        }
        return flag;
    }

//    public void src_placeListener() {
//        btn_src.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            PlacePicker.IntentBuilder intentBuilder =
//                                    new PlacePicker.IntentBuilder();
//                            intentBuilder.setLatLngBounds(bound);
//                            Intent intent = intentBuilder.build(getActivity());
//                            startActivityForResult(intent, Source_PICKER_REQUEST);
//
//                        } catch (GooglePlayServicesRepairableException e) {
//                            e.printStackTrace();
//                        } catch (GooglePlayServicesNotAvailableException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        );
//    }
//
//    public void dest_placeListener() {
//        btn_dest.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            PlacePicker.IntentBuilder intentBuilder =
//                                    new PlacePicker.IntentBuilder();
//                            intentBuilder.setLatLngBounds(bound);
//                            Intent intent = intentBuilder.build(getActivity());
//                            startActivityForResult(intent, Destination_PICKER_REQUEST);
//
//                        } catch (GooglePlayServicesRepairableException e) {
//                            e.printStackTrace();
//                        } catch (GooglePlayServicesNotAvailableException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        );
//    }

//    public void OnClickListener2() {
//        btn_fully_done.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (validate()) {
//                            g_id = rbg_gender.getCheckedRadioButtonId();
//                            View radioButton = rbg_gender.findViewById(g_id);
//                            int gender_id = rbg_gender.indexOfChild(radioButton);
//                            if (gender_id == 0) {
//                                gender_x = "Male";
//                            } else {
//                                gender_x = "Female";
//                            }
//                            p_id = rbg_pref.getCheckedRadioButtonId();
//                            View radioButton2 = rbg_pref.findViewById(p_id);
//                            int gender_pref = rbg_pref.indexOfChild(radioButton2);
//                            if (gender_pref == 0) {
//                                pref_comp_x = "Male Youth";
//                            } else if (gender_pref == 1) {
//                                pref_comp_x = "Female Youth";
//                            } else {
//                                pref_comp_x = "Senior Citizen";
//                            }
////                            RideDetails rd = new RideDetails(MainActivity.num, name.getText().toString(), gender_x, pref_comp_x, Integer.parseInt(age.getText().toString()), Long.parseLong(contact.getText().toString()), hour, min, year_x, month, day, src_lat_x, src_long_x, dest_lat_x, dest_long_x, src_name_x, dest_name_x, img_path);
////                            try {
////                                data_base.addRide(rd);
////                            } catch (Exception e) {
////                                Toast.makeText(newRide.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
////                            }
////                            Toast.makeText(newRide.this, "Details Saved", Toast.LENGTH_SHORT).show();
////                            Intent intent = new Intent();
////                            setResult(Activity.RESULT_OK, intent);
////                            finish();
//                        } else {
//                            Toast.makeText(getActivity(), "Fill details Properly", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_save_text){
            if (validate()) {
                String licenceNumStr = licenceET.getText().toString();
                String carNumStr = carNumberET.getText().toString();
                String carModelStr = carModelET.getText().toString();
                selectedPrice = priceEt.getText().toString();
                int gender_id = rbg_gender.getCheckedRadioButtonId();
                if (gender_id == 0) {
                    gender_x = "Male";
                } else {
                    gender_x = "Female";
                }
//                MainActivity.p_id = rbg_pref.getCheckedRadioButtonId();
//                View radioButton2 = rbg_pref.findViewById(MainActivity.p_id);
                int gender_pref = rbg_pref.getCheckedRadioButtonId();
                if (gender_pref == 0) {
                    pref_comp_x = "Male Youth";
                } else if (gender_pref == 1) {
                    pref_comp_x = "Female Youth";
                } else {
                    pref_comp_x = "Senior Citizen";
                }
                // values adding to database
                RideDetails rd = new RideDetails((handler.getRowCount()+1),
                        name.getText().toString(),
                        gender_x,
                        pref_comp_x,
                        Integer.parseInt(age.getText().toString()),
                        Long.parseLong(contact.getText().toString()), hour, min, year_x,
                        month,
                        day,
                        src_lat_str,
                        src_long_str,
                        dest_lat_str,
                        dest_long_str,
                        src_name_x,
                        dest_name_x,
                        selectedPrice,
                        licenceNumStr,
                        carNumStr,
                        carModelStr);
                try {
                    handler.addRide(rd);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }




                Toast.makeText(getActivity(), "Details Saved", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Fill details Properly", Toast.LENGTH_SHORT).show();
            }
        }
//    }
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {
            if(resultCode == RESULT_OK){
                double latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
                Log.d(Const.TAG,"LATITUDE****"+String.valueOf(latitude));
                src_lat_str = latitude;
                double longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
                Log.d(Const.TAG,"LONGITUDE****"+String.valueOf(longitude));
                src_long_str = longitude;
                String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
                Log.d(Const.TAG,"ADDRESS****"+String.valueOf(address));
                String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);
                Log.d(Const.TAG,"POSTALCODE****"+String.valueOf(postalcode));
//                Bundle bundle = data.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
//                Log.d(Const.TAG,"BUNDLE TEXT****"+bundle.getString("test"));
                Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
                if(fullAddress != null) {
                    Log.d(Const.TAG, "FULL ADDRESS****" + fullAddress.toString());
                    String addressTxt = fullAddress.getAddressLine(1) + fullAddress.getAddressLine(2);
                    src_name_x = addressTxt;
                    src_txt.setText(addressTxt);
                } else{
                    Log.d(Const.TAG, "FULL ADDRESS**** null");
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 112) {
            if(resultCode == RESULT_OK){
                double latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
                Log.d(Const.TAG,"LATITUDE****"+String.valueOf(latitude));
                dest_lat_str = latitude;
                double longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
                Log.d(Const.TAG,"LONGITUDE****"+String.valueOf(longitude));
                dest_long_str = longitude;
                String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
                Log.d(Const.TAG,"ADDRESS****"+String.valueOf(address));
                String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);
                Log.d(Const.TAG,"POSTALCODE****"+String.valueOf(postalcode));
//                Bundle bundle = data.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
//                Log.d(Const.TAG,"BUNDLE TEXT****"+bundle.getString("test"));
                Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
                if(fullAddress != null) {
                    Log.d(Const.TAG, "FULL ADDRESS****" + fullAddress.toString());
                    String addressTxt = fullAddress.getAddressLine(1) + fullAddress.getAddressLine(2);
                    dest_name_x = addressTxt;
                    dest_txt.setText(addressTxt);
                } else{
                    Log.d(Const.TAG, "FULL ADDRESS**** null");
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
