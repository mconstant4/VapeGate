package uri.egr.biosensing.vapegate.services;

import android.app.IntentService;
import android.app.Service;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import uri.egr.biosensing.vapegate.gatt_attributes.GattCharacteristics;
import uri.egr.biosensing.vapegate.gatt_attributes.GattServices;
import uri.egr.biosensing.vapegate.receivers.VapeGateUpdateReceiver;

/**
 * Created by mcons on 3/11/2017.
 */

public class VapeGateManagerService extends Service {
    public static final String ACTION_CONNECT = "uri.egr.vapegate.connect";
    public static final String ACTION_DISCONNECT = "uri.egr.vapegate.disconnect";
    public static final String ACTION_REQUEST_READ = "uri.egr.vapegate.read";

    private boolean mServiceBound;
    private BleConnectionService mService;
    private ServiceConnection mServiceConnection;

    private BroadcastReceiver mBLEUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BLE", "Received Update");
            String action = intent.getStringExtra(BleConnectionService.INTENT_EXTRA);
            switch (action) {
                case BleConnectionService.GATT_STATE_CONNECTED:
                    mService.discoverServices();
                    break;
                case BleConnectionService.GATT_STATE_DISCONNECTED:
                    Intent updateIntent2 = new Intent(VapeGateUpdateReceiver.VAPE_GATE_INTENT_FILTER.getAction(0));
                    updateIntent2.putExtra(VapeGateUpdateReceiver.EXTRA_CONNECTION_UPDATE, false);
                    sendBroadcast(updateIntent2);
                    break;
                case BleConnectionService.GATT_DISCOVERED_SERVICES:
                    BluetoothGattCharacteristic characteristic = mService.getCharacteristic(GattServices.VAPE_GATE_SERVICE, GattCharacteristics.VAPE_GATE_CHARACTERISTIC);
                    if (characteristic != null) {
                        mService.enableNotifications(characteristic);
                    }
                    Intent updateIntent1 = new Intent(VapeGateUpdateReceiver.VAPE_GATE_INTENT_FILTER.getAction(0));
                    updateIntent1.putExtra(VapeGateUpdateReceiver.EXTRA_CONNECTION_UPDATE, true);
                    sendBroadcast(updateIntent1);
                    break;
                case BleConnectionService.GATT_CHARACTERISTIC_READ:
                    byte[] data = intent.getByteArrayExtra(BleConnectionService.INTENT_DATA);
                    Intent updateIntent = new Intent(VapeGateUpdateReceiver.VAPE_GATE_INTENT_FILTER.getAction(0));
                    updateIntent.putExtra(VapeGateUpdateReceiver.EXTRA_READ_UPDATE, data);
                    sendBroadcast(updateIntent);

                    //Send to Watson

                    break;
                case BleConnectionService.GATT_DESCRIPTOR_WRITE:
                    break;
                case BleConnectionService.GATT_NOTIFICATION_TOGGLED:
                    break;
                case BleConnectionService.GATT_DEVICE_INFO_READ:
                    break;
            }
        }
    };

    public static void connect(Context context) {
        Intent intent = new Intent(context, VapeGateManagerService.class);
        intent.setAction(ACTION_CONNECT);
        context.startService(intent);
    }

    public static void disconnect(Context context) {
        Intent intent = new Intent(context, VapeGateManagerService.class);
        intent.setAction(ACTION_DISCONNECT);
        context.startService(intent);
    }

    public static void requestRead(Context context) {
        Intent intent = new Intent(context, VapeGateManagerService.class);
        intent.setAction(ACTION_REQUEST_READ);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mServiceConnection = new BleServiceConnection();

        registerReceiver(mBLEUpdateReceiver, new IntentFilter(BleConnectionService.INTENT_FILTER_STRING));
        bindService(new Intent(this, BleConnectionService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        if (intent == null) {
            return START_REDELIVER_INTENT;
        }

        switch (intent.getAction()) {
            case ACTION_CONNECT:
                connect();
                break;
            case ACTION_DISCONNECT:
                disconnect();
                break;
            case ACTION_REQUEST_READ:
                requestRead();
                break;
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        unbindService(mServiceConnection);
        unregisterReceiver(mBLEUpdateReceiver);

        super.onDestroy();
    }

    private void connect() {
        if (mServiceBound) {
            mService.connect();
        } else {
            log("Cannot Connect - BLE Connection Service is not bound yet!");
        }
    }

    private void disconnect() {
        if (mServiceBound) {
            mService.disconnect();
        } else {
            log("Could not Disconnect - BLE Connection Service is not bound!");
        }
    }

    private void requestRead() {
        if (mServiceBound) {
            BluetoothGattCharacteristic characteristic = mService.getCharacteristic(GattServices.VAPE_GATE_SERVICE, GattCharacteristics.VAPE_GATE_CHARACTERISTIC);
            byte[] readValue = characteristic.getValue();
            Intent intent = new Intent(VapeGateUpdateReceiver.VAPE_GATE_INTENT_FILTER.getAction(0));
            intent.putExtra(VapeGateUpdateReceiver.EXTRA_READ_UPDATE, readValue);
            sendBroadcast(intent);
        } else {
            log("Could not read from Vape - BLE Connection Service is not bound!");
        }
    }

    private void log(String message) {
        Log.d("VapeGateManager", message);
    }

    private class BleServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceBound = true;
            mService = ((BleConnectionService.BLEConnectionBinder) iBinder).getService();
            mService.connect();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBound = false;
        }
    }
}
