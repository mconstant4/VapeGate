package uri.egr.biosensing.vapegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

import uri.egr.biosensing.vapegate.receivers.VapeGateUpdateReceiver;
import uri.egr.biosensing.vapegate.services.BandConnectionService;
import uri.egr.biosensing.vapegate.services.VapeGateManagerService;
import uri.egr.biosensing.vapegate.tasks.RequestHeartRateTask;

public class MainActivity extends AppCompatActivity {
    private boolean mConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RequestHeartRateTask().execute(new WeakReference<Activity>(this));

        mConnected = false;
        registerReceiver(mVapeGateUpdateReceiver, VapeGateUpdateReceiver.VAPE_GATE_INTENT_FILTER);

        final Context context = this;
        Button connectButton = (Button) findViewById(R.id.connect_button);
        Button disconnectButton = (Button) findViewById(R.id.disconnect_button);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BandConnectionService.connect(context);
                VapeGateManagerService.connect(context);
            }
        });
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BandConnectionService.disconnect(context);
                VapeGateManagerService.disconnect(context);
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mVapeGateUpdateReceiver);
        super.onDestroy();
    }

    private VapeGateUpdateReceiver mVapeGateUpdateReceiver = new VapeGateUpdateReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(VapeGateUpdateReceiver.EXTRA_CONNECTION_UPDATE)) {
                if (intent.getBooleanExtra(EXTRA_CONNECTION_UPDATE, false)) {
                    mConnected = true;
                    Log.d("MAIN", "Connected to Vape Gate");
                } else {
                    mConnected = false;
                    Log.d("MAIN", "Disconnected from Vape Gate");
                }
            } else if (intent.hasExtra(VapeGateUpdateReceiver.EXTRA_READ_UPDATE)) {
                byte[] data = intent.getByteArrayExtra(VapeGateUpdateReceiver.EXTRA_READ_UPDATE);
                Log.d("MAIN", "Vape Gate: " + data[0] + " " + data[1]);
            }
        }
    };
}
