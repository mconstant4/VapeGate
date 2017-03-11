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

public class DateListAdapt extends ArrayAdapter<DateListEntry> {
    private static ArrayList<DateListEntry> users;

    private LayoutInflater mInflater;

    public DateListAdapt(Context context, int textViewResourceId, ArrayList<DateListEntry> users) {
        super(context, textViewResourceId);
        this.users = users;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return users.size();
    }

    public DateListEntry getItem(int position) {
        return users.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.date_list, null);
            holder = new ViewHolder();
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDate.setText(users.get(position).getDate());
        holder.txtDate.setGravity(Gravity.CENTER);

        return convertView;
    }

    static class ViewHolder {
        TextView txtDate;
    }
}
