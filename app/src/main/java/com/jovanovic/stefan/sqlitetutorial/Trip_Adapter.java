package com.jovanovic.stefan.sqlitetutorial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Trip_Adapter extends RecyclerView.Adapter<Trip_Adapter.MyViewHolder> implements Filterable {

    private final Context context;
    private final Activity activity;
    private List<Trip> trips;
    private final List<Trip> tripsNew;

    Trip_Adapter(Activity activity, Context context, List<Trip> trips) {
        this.activity = activity;
        this.context = context;
        this.trips = trips;
        this.tripsNew = trips;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Trip trip = trips.get(position);
        int id = trip.getId();
        String trip_name = trip.getTrip_name();
        String place_to = trip.getPlace_to();
        String date_start = trip.getDate_start();
        String risk = trip.getRisk();

        // set value to form
        holder.trip_id_txt.setText(String.valueOf(id));
        holder.trip_name_txt.setText(trip_name);
        holder.place_to_txt.setText(place_to);
        holder.date_start_txt.setText(date_start);
        holder.risk.setText(risk);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passing parameter values
                Intent intent = new Intent(context, Detail_Trip_Activity.class);
                intent.putExtra("trip", trip);
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, Update_Trip_Activity.class);
                intent.putExtra("selectedTrip", trip);
                activity.startActivityForResult(intent, 1);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    trips = tripsNew;
                } else {
                    List<Trip>list = new ArrayList<>();
                    for (Trip trip : trips) {
                        if (trip.getTrip_name().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(trip);
                        } else if (trip.getPlace_to().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(trip);
                        } else if (trip.getDate_start().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(trip);
                        }
                    }

                    trips = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = trips;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                trips = (List<Trip>) results.values;
                notifyDataSetChanged();
            }
        };

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trip_id_txt, trip_name_txt, place_to_txt, date_start_txt, risk;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_name_txt = itemView.findViewById(R.id.trip_name_txt);
            place_to_txt = itemView.findViewById(R.id.place_to_txt);
            date_start_txt = itemView.findViewById(R.id.date_start_txt);
            risk = itemView.findViewById(R.id.risk);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
