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

    public static final String PREF_DAILY_DRAG= "daily_drags";

    public static final String PREF_BAND_WORN = "wbl.ear.band_worn";
    public static final String PREF_ENABLE_SENSORS = "pref_enable_sensors";
    public static final String PREF_ENABLE_AUDIO = "pref_enable_audio";
    public static final String PREF_AUDIO_DURATION = "pref_audio_duration";
    public static final String PREF_AUDIO_DELAY = "pref_audio_delay";
    public static final String PREF_IDENTIFIER = "pref_id";
    public static final String PREF_PATTERN = "pref_pattern";
    public static final String PREF_AUDIO_LM = "pref_audio_lm";
    public static final String PREF_HR_LM = "pref_hr_lm";
    public static final String PREF_GSR_LM = "pref_gsr_lm";
    public static final String PREF_ACC_LM = "pref_acc_lm";
    public static final String PREF_LIGHT_LM = "pref_light_lm";
    public static final String PREF_TEMP_LM = "pref_temp_lm";
    public static final String PREF_BLACKOUT_TOGGLE = "pref_blackout_toggle";

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defaultValue);
    }

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

//            case "daily_drags":
//                Log.d("SETTINGS", "Drag");
//                if (sharedPreferences.getString("daily_drags", mContext, "0")) {
//                    Log.d("SETTINGS", "ENABLE");
//                } else {
//                    Log.d("SETTINGS", "DISABLE");
//                }
//                break;
        }
    }
}
