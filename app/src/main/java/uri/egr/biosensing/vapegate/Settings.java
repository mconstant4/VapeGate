package uri.egr.biosensing.vapegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import uri.egr.biosensing.vapegate.services.BandConnectionService;
import uri.egr.biosensing.vapegate.services.VapeGateManagerService;

/**
 * Created by nickp on 3/11/2017.
 */

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        mContext = this;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "vapegate_enable":
                if (sharedPreferences.getBoolean("vapegate_enable", false)) {
                    BandConnectionService.connect(mContext);
                    VapeGateManagerService.connect(mContext);
                } else {
                    BandConnectionService.disconnect(mContext);
                    VapeGateManagerService.disconnect(mContext);
                }
                break;
        }
    }
}
