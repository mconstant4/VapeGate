package uri.egr.biosensing.vapegate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nickp on 3/10/2017.
 */

public class Start extends First
{
    private Button next;
    private TextView name, age, weight, daily, hasQuit, lastCig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        next = (Button)findViewById(R.id.finsh);
        name = (TextView)findViewById(R.id.name);
        age = (TextView)findViewById(R.id.age);
        weight = (TextView)findViewById(R.id.weight);
        daily = (TextView)findViewById(R.id.daily);
        hasQuit = (TextView)findViewById(R.id.hasQuit);
        lastCig = (TextView)findViewById(R.id.lastCig);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.length() > 2 && age.length() > 1 && weight.length() > 4
                        && daily.length() > 0 && hasQuit.length() > 1 && lastCig.length() > 1)
                {
                    //Confirm PW strength
                    //Send this data to DB
                    Intent intent = new Intent(Start.this, Home.class);
                    //intent.putExtra("DOC_FIRST_NAME", first.getText().toString());
                    //intent.putExtra("DOC_LAST_NAME", last.getText().toString());
                    startActivity(intent);
                }

                else
                {
                    if(name.length() < 2)
                    {
                        name.setTextColor(Color.RED);
                    }
                    else
                    {
                        name.setTextColor(Color.BLACK);
                    }
                    if(age.length() < 1)
                    {
                        age.setTextColor(Color.RED);
                    }
                    else
                    {
                        age.setTextColor(Color.BLACK);
                    }
                    if(weight.length() < 4)
                    {
                        weight.setTextColor(Color.RED);
                    }
                    else
                    {
                        weight.setTextColor(Color.BLACK);
                    }
                    if(daily.length() == 0)
                    {
                        daily.setTextColor(Color.RED);
                    }
                    else
                    {
                        daily.setTextColor(Color.BLACK);
                    }
                    if(hasQuit.length() < 1)
                    {
                        hasQuit.setTextColor(Color.RED);
                    }
                    else
                    {
                        hasQuit.setTextColor(Color.BLACK);
                    }
                    if(lastCig.length() < 1)
                    {
                        lastCig.setTextColor(Color.RED);
                    }
                    else
                    {
                        lastCig.setTextColor(Color.BLACK);
                    }

                    Context context = getApplicationContext();
                    CharSequence text = "Please fill out all out all fields";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }
}
