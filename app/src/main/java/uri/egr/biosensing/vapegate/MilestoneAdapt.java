package com.example.nickp.cornellhack;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nickp on 3/11/2017.
 */

public class MilestoneAdapt extends ArrayAdapter<MilestoneEntry> {
    private static ArrayList<MilestoneEntry> users;

    private LayoutInflater mInflater;

    public MilestoneAdapt(Context context, int textViewResourceId, ArrayList<MilestoneEntry> users) {
        super(context, textViewResourceId);
        this.users = users;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return users.size();
    }

    public MilestoneEntry getItem(int position) {
        return users.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.mile_list, null);
            holder = new ViewHolder();
            holder.txtDate = (TextView) convertView.findViewById(R.id.milestonetit);
            holder.txtNumSen = (TextView) convertView.findViewById(R.id.milestonedes);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDate.setText(users.get(position).getTitle());
        holder.txtNumSen.setText("" + users.get(position).getDesc());
        holder.txtDate.setGravity(Gravity.CENTER);
        holder.txtNumSen.setGravity(Gravity.CENTER);

        return convertView;
    }

    static class ViewHolder {
        TextView txtDate;
        TextView txtNumSen;
    }
}
