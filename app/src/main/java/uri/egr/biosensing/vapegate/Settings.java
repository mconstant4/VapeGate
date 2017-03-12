package uri.egr.biosensing.vapegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import uri.egr.biosensing.vapegate.services.BandConnectionService;
import uri.egr.biosensing.vapegate.services.VapeGateManagerService;

/**
 * Created by nickp on 3/11/2017.
 */

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        mContext = this;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "vapegate_enable":
                Log.d("SETTINGS", "Toggle");
                if (sharedPreferences.getBoolean("vapegate_enable", false)) {
                    Log.d("SETTINGS", "ENABLE");
                    BandConnectionService.connect(mContext);
                    VapeGateManagerService.connect(mContext);
                } else {
                    Log.d("SETTINGS", "DISABLE");
                    BandConnectionService.disconnect(mContext);
                    VapeGateManagerService.disconnect(mContext);
                }
                break;
        }
    }
}
