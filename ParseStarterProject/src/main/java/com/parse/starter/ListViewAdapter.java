package com.parse.starter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.starter.Notification;
import com.parse.starter.R;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Notification> Notificationlist = null;
    private ArrayList<Notification> arraylist;

    public ListViewAdapter(Context context,
                           List<Notification> Notificationlist) {
        mContext = context;
        this.Notificationlist = Notificationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Notification>();
        this.arraylist.addAll(Notificationlist);
    }

    public class ViewHolder {
        TextView message;
        }

    @Override
    public int getCount() {
        return Notificationlist.size();
    }

    @Override
    public Notification getItem(int position) {
        return Notificationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.message = (TextView) view.findViewById(R.id.txtMessage);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.message.setText(Notificationlist.get(position).getMessage());
//        holder.country.setText(Notificationlist.get(position).getCountry());
//        holder.population.setText(Notificationlist.get(position)
//                .getPopulation());

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
//                Intent intent = new Intent(mContext, SingleItemView.class);
//                // Pass all data rank
//                intent.putExtra("rank",
//                        (Notificationlist.get(position).getRank()));
//                // Pass all data country
//                intent.putExtra("country",
//                        (Notificationlist.get(position).getCountry()));
//                // Pass all data population
//                intent.putExtra("population",
//                        (Notificationlist.get(position).getPopulation()));
                // Start SingleItemView Class
//                mContext.startActivity(intent);
            }
        });

        return view;
    }
}