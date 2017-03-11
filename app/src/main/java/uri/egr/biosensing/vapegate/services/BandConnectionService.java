package uri.egr.biosensing.vapegate.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandResultCallback;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.UserConsent;

import java.util.concurrent.TimeUnit;

import uri.egr.biosensing.vapegate.listeners.BandHeartRateListener;

/**
 * Created by mcons on 3/11/2017.
 */

public class BandConnectionService extends Service {
    //Actions provided by this Service
    public static final String ACTION_CONNECT = "uri.egr.vapegate.band_connect";
    public static final String ACTION_DISCONNECT = "uri.egr.vapegate.band_disconnect";

    public static void connect(Context context) {
        Intent intent = new Intent(context, BandConnectionService.class);
        intent.setAction(ACTION_CONNECT);
        context.startService(intent);
    }

    public static void disconnect(Context context) {
        Intent intent = new Intent(context, BandConnectionService.class);
        intent.setAction(ACTION_DISCONNECT);
        context.startService(intent);
    }

    private Context mContext;
    private BandClientManager mBandClientManager;
    private BandClient mBandClient;

    private BandHeartRateListener mBandHeartRateListener;

    private BandResultCallback<ConnectionState> mBandConnectResultCallback = new BandResultCallback<ConnectionState>() {
        @Override
        public void onResult(ConnectionState connectionState, Throwable throwable) {
            switch (connectionState) {
                case CONNECTED:
                    log("Band State: CONNECTED");
                    if (mBandClient.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED) {
                        log("Please give Heart Rate consent for this Service to work");
                        disconnect();
                    }
                    try {
                        mBandClient.getSensorManager().registerHeartRateEventListener(mBandHeartRateListener);
                    } catch (BandException e) {
                        e.printStackTrace();
                    }
                    break;
                case BOUND:
                    log("Band State: BOUND");
                    log("Stopping Service");
                    stopSelf();
                    break;
                case BINDING:
                    log("Band State: BINDING");
                    break;
                case UNBOUND:
                    log("Band State: UNBOUND");
                    break;
                case UNBINDING:
                    log("Band State: UNBINDING");
                    break;
                default:
                    log("Band State: UNKNOWN");
                    break;
            }
        }
    };

    private BandResultCallback<Void> mBandDisconnectResultCallback = new BandResultCallback<Void>() {
        @Override
        public void onResult(Void aVoid, Throwable throwable) {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        log("Service Started");
        mContext = this;

        mBandClientManager = BandClientManager.getInstance();
        mBandHeartRateListener = new BandHeartRateListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        if (intent == null || intent.getAction() == null) {
            return START_NOT_STICKY;
        }

        switch (intent.getAction()) {
            case ACTION_CONNECT:
                connect();
                break;
            case ACTION_DISCONNECT:
                disconnect();
                break;
            default:
                break;
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        log("Service Destroyed");
        super.onDestroy();
    }

    private void connect() {
        if (mBandClientManager == null) {
            log("Connect Failed (Band Client Manager not Initialized)");
            return;
        }

        BandInfo[] pairedBands = mBandClientManager.getPairedBands();
        connect(pairedBands[0]);
    }

    private void connect(BandInfo bandInfo) {
        mBandClient = mBandClientManager.create(this, bandInfo);
        mBandClient.connect().registerResultCallback(mBandConnectResultCallback);
    }

    private void disconnect() {
        if (mBandClientManager == null) {
            log("Disconnect Failed (Band Client Manager not Initialized)");
            return;
        }

        if (mBandClient == null || !mBandClient.isConnected()) {
            return;
        }

        try {
            mBandClient.getSensorManager().unregisterHeartRateEventListener(mBandHeartRateListener);
        } catch (BandException e) {
            e.printStackTrace();
        }

        mBandClient.disconnect().registerResultCallback(mBandDisconnectResultCallback, 10, TimeUnit.SECONDS);
    }

    private void log(String message) {
        Log.d("BandConnectionService", message);
    }

}
