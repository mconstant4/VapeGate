package uri.egr.biosensing.vapegate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by nickp on 3/11/2017.
 */

public class Home extends MainActivity
{
    private LineChart chart;
    private TextView drags, avDrag, avHRInc, avTimeDr;
    private TextView currNic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        mDrawer.addView(contentView, 0);

        View contentView2 = inflater.inflate(R.layout.home, null, false);
        mDrawer.addView(contentView2, 1);

        chart = (LineChart) findViewById(R.id.dayChart);
        currNic = (TextView)findViewById(R.id.nicConc);
        drags = (TextView)findViewById(R.id.dragsTaken);
        avDrag = (TextView)findViewById(R.id.avgDragTime);
        avHRInc = (TextView)findViewById(R.id.avgHR);
        avTimeDr = (TextView)findViewById(R.id.avgBetweenTime);
    }
}
