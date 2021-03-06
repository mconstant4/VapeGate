package uri.egr.biosensing.vapegate.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandInfo;
import com.microsoft.band.sensors.HeartRateConsentListener;

import java.lang.ref.WeakReference;

/**
 * Created by mcons on 3/11/2017.
 */

public class RequestHeartRateTask extends AsyncTask<WeakReference<Activity>, Void, Void> {
    private WeakReference<Context> mContext;

    private HeartRateConsentListener mHeartRateConsentListener = new HeartRateConsentListener() {
        @Override
        public void userAccepted(boolean b) {

        }
    };

    @Override
    protected Void doInBackground(WeakReference...weakReferences) {
        WeakReference<Activity> activityWeakReference = weakReferences[0];
        if (activityWeakReference == null || activityWeakReference.get() == null) {
            return null;
        }

        mContext = new WeakReference<Context>(activityWeakReference.get());

        BandClientManager bandClientManager = BandClientManager.getInstance();
        BandInfo[] pairedBands = bandClientManager.getPairedBands();
        if (pairedBands.length == 0) {
            return null;
        } else {
            try {
                if (activityWeakReference.get() != null) {
                    BandClient bandClient = bandClientManager.create(activityWeakReference.get(), pairedBands[0]);
                    bandClient.connect().await();
                    bandClient.getSensorManager().requestHeartRateConsent(activityWeakReference.get(), mHeartRateConsentListener);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}