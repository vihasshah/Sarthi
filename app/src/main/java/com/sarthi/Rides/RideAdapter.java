package com.sarthi.Rides;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sarthi.Helper.Const;
import com.sarthi.Pooler.RideDetails;
import com.sarthi.R;

import java.util.List;

/**
 * Created by Vihas on 28-03-2017.
 */

public class RideAdapter extends BaseAdapter {
    private Context context;
    private List<RideDetails> list;
    boolean started = false;
    int position;
    public RideAdapter(Context context, List<RideDetails> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView toTV, fromTV, personNameTV, timeTV, moneyTV;
        Button joinBtn, startBtn, stopBtn;
        LinearLayout startStopLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position = position;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.single_row_ride, parent, false);
            holder.toTV = (TextView) convertView.findViewById(R.id.row_ride_tv_to);
            holder.fromTV = (TextView) convertView.findViewById(R.id.row_ride_tv_from);
            holder.personNameTV = (TextView) convertView.findViewById(R.id.row_ride_tv_name);
            holder.timeTV = (TextView) convertView.findViewById(R.id.row_ride_tv_time);
            holder.moneyTV = (TextView) convertView.findViewById(R.id.row_ride_tv_money);
            holder.joinBtn = (Button) convertView.findViewById(R.id.row_ride_btn_join);
            holder.startStopLayout = (LinearLayout) convertView.findViewById(R.id.row_ride_start_stop_layout);
            holder.startBtn = (Button) convertView.findViewById(R.id.row_ride_btn_start);
            holder.stopBtn = (Button) convertView.findViewById(R.id.row_ride_btn_stop);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.toTV.setText(list.get(position).getDest_name_x());
        holder.fromTV.setText(list.get(position).getSrc_name_x());
        holder.personNameTV.setText(list.get(position).getName());
        String timeStr = list.get(position).getHour()+":"+list.get(position).getMin();
        holder.timeTV.setText(timeStr);
        holder.moneyTV.setText("Rs. " + list.get(position).getPrice());
        if (list.get(position).isJoin()) {
            holder.joinBtn.setText("Joined");
            list.get(position).setJoin(true);
            holder.startStopLayout.setVisibility(View.VISIBLE);
        } else {
            holder.joinBtn.setText("Join");
            list.get(position).setJoin(false);
            holder.startStopLayout.setVisibility(View.GONE);
        }
        holder.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isJoin()) {
                    holder.joinBtn.setText("Join");
                    list.get(position).setJoin(false);
                    holder.startStopLayout.setVisibility(View.GONE);
                } else {
                    holder.joinBtn.setText("Joined");
                    list.get(position).setJoin(true);
                    holder.startStopLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        if (!list.get(position).isRideStarted()) {
            // handling stop button
            holder.stopBtn.setEnabled(false);
            holder.stopBtn.setTextColor(Color.GRAY);
        } else {
            holder.stopBtn.setEnabled(true);

        }
        // handling start button
        holder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ride started", Toast.LENGTH_SHORT).show();
                list.get(position).setRideStarted(true);
                holder.startBtn.setEnabled(false);
                holder.startBtn.setTextColor(Color.GRAY);
                holder.stopBtn.setEnabled(true);
                holder.stopBtn.setTextColor(context.getResources().getColor(R.color.red));

            }
        });
        holder.stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ride stopped", Toast.LENGTH_SHORT).show();
                list.get(position).setRideStarted(false);
                holder.startBtn.setEnabled(true);
                holder.startBtn.setTextColor(context.getResources().getColor(R.color.green));
                holder.stopBtn.setEnabled(false);
                holder.stopBtn.setTextColor(Color.GRAY);
//                    AlertDialog dialog = showRatingDialog(context);
//                    dialog.show();
                showFairDialog(context);
            }
        });

        //view on click listner event handle
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RideDetailsActivity.class);
                intent.putExtra(Const.INTENT_CLASS_PASS,list.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void showFairDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thank you");
        builder.setMessage("Your Fair: "+list.get(position).getPrice());
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showRatingDialog(context);
                dialog.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Rating dialog
     *
     * @param context
     * @return dialog
     */
    public  void showRatingDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rating_layout, null);
        builder.setView(view);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(context, "Thank you for your feedback", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // dialog layout initialization
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.dialog_ratings_rating_bar);
//        ImageView cancelDialog = (ImageView) view.findViewById(R.id.dialog_ratings_cancel);
        final EditText ratingFeedbackET = (EditText) view.findViewById(R.id.dialog_ratings_feedback_edittext);
//        Button sendButton = (Button) view.findViewById(R.id.dialog_ratings_send_button);

        // alert dialog create
        final AlertDialog dialog = builder.create();


        // handle send button
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
                // check if internet is available or not
//                if(NetworkUtils.isConnectedToInternet(context)){
                // if internet is available the call webservice
                // create json request body for webservice call
                // handle rating bar
//                String rating = String.valueOf(ratingBar.getRating());
//                Log.d(Const.TAG, "ratings given:" + rating);
//                // rating feedback
//                String ratingFeedbackStr = ratingFeedbackET.getText().toString();
//                Log.d(Const.TAG, "Ratin feedback given:" + ratingFeedbackStr);
//                String rootKey = "rating";
//                String dialogMessage = context.getString(R.string.rating_sending);
//                String childkeys[] = new String[]{"vendorID", "customerID", "rating", "experience"};
//                String childValues[] = new String[]{"10010", "5", rating, ratingFeedbackStr};
//                    // creating model
//                    JSONModel model = new JSONModel();
//                    model.setRootKey(rootKey);
//                    model.setChildKey(childkeys);
//                    model.setChildValue(childValues);
//                    ArrayList<JSONModel> jsonModelArrayList = new ArrayList<JSONModel>();
//                    jsonModelArrayList.add(model);
//                    // creating json request string from model
//                    String jsonRequestBody = NetworkUtils.createSimpleJson(jsonModelArrayList);
//                    new WebserviceCall(context, Const.RATING_URL, jsonRequestBody, dialogMessage, true, new AsyncResponse() {
//
//                        @Override
//                        public void onSuccess(String message, JSONArray jsonData) {
//                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(String message) {
//                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
//                        }
//
//                    }).execute();
//                }else{
//                    // if internet is not available
//                    DisplayUtils.makeSnakeBar(context,venderDetailsCoLayout,context.getString(R.string.not_connected_internet));
//                }
//            }
//        });
        dialog.show();
//        return dialog;
    }
}

