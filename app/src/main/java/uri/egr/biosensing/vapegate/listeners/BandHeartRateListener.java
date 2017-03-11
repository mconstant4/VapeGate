package uri.egr.biosensing.vapegate.listeners;

import android.content.Context;
import android.util.Log;

import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;

/**
 * Created by mcons on 3/11/2017.
 */

public class BandHeartRateListener implements BandHeartRateEventListener {
    private Context mContext;

    public BandHeartRateListener(Context context) {
        mContext = context;
    }

    @Override
    public void onBandHeartRateChanged(BandHeartRateEvent bandHeartRateEvent) {
        Log.d("HR Data", "HR Data Received");

        //Send to Watson

    }
}
