package uri.egr.biosensing.vapegate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by nickp on 3/11/2017.
 */

public class Home extends MainActivity
{
    private LineChart chart;
    private TextView drags, avDrag, avHRInc, avTimeDr;
    private TextView currNic;
    private double duration;
    private LineDataSet chartData;
    private DecimalFormat df = new DecimalFormat("#");
    private Context context = this;
    YAxis y;
    XAxis x;
    String xAxisTitle = "Heart Rate";

    private MonitorGraph monitorGraph = new MonitorGraph();
    private LineChart graphLayout;

    private LineChart dataGraph;
    private LineDataSet dataSet;
    private LineData savedData;

    private ArrayList<Float> dataList;
    private ArrayList<String> yPoints;

    private LineDataSet savedXData;
    private ArrayList<String> savedYData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        mDrawer.addView(contentView, 0);

        View contentView2 = inflater.inflate(R.layout.home, null, false);
        mDrawer.addView(contentView2, 1);

//        chart = (LineChart) findViewById(R.id.dayChart);
        currNic = (TextView) findViewById(R.id.nicConc);
        drags = (TextView) findViewById(R.id.dragsTaken);
        avDrag = (TextView) findViewById(R.id.avgDragTime);
        avHRInc = (TextView) findViewById(R.id.avgHR);
        avTimeDr = (TextView) findViewById(R.id.avgBetweenTime);

        //  Sets up the data chart
        graphLayout = (LineChart) findViewById(R.id.dayChart);
        graphLayout.setVisibleXRange(0f, 10f);
        dataGraph = new LineChart(this);
        dataGraph = monitorGraph.createChart(dataGraph);
        graphLayout.addView(dataGraph);
    }

    private void updateGraph()
    {
        //  Resets the graph
        dataGraph.invalidate();

        //  Sets the x and y data points
//        dataSet = open.getRandomDataSet(dataList, dataGraph);
//        yPoints = open.getyPoints();

        //  Creates the new graph
        LineData test = new LineData(yPoints, dataSet);
        dataGraph.setData(test);
        dataGraph.notifyDataSetChanged();
        dataGraph.setVisibleXRangeMaximum(10f);
        dataGraph.moveViewToX(dataSet.getEntryCount() - 1);
    }
}
