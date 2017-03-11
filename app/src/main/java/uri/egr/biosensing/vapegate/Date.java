package com.example.nickp.cornellhack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nickp on 3/11/2017.
 */

public class Date extends MainActivity
{
    ListView dates;
    String date[] = {"03/10/2017", "03/11/2017", "03/12/2017"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recov);

        dates = (ListView)findViewById(R.id.dateList);

        final ArrayList<DateListEntry> date = new ArrayList<DateListEntry>();

        for(int count=0; count < date.size(); count++)
        {

        }

        dates.setAdapter(new DateListAdapt(getApplicationContext(), R.layout.date_list, date));

        dates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                DateListEntry test = date.get(i);

                String date = test.getDate();

                ArrayList<Integer> hr = new ArrayList<Integer>();
                ArrayList<String> time = new ArrayList<String>();

                Intent intent = new Intent(Date.this, Graph.class);
                intent.putExtra("HR", hr);
                intent.putExtra("TIME", time);
                startActivity(intent);
            }
        });
    }
}
