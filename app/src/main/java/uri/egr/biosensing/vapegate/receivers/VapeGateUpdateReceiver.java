package uri.egr.biosensing.vapegate.receivers;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Created by mcons on 3/11/2017.
 */

abstract public class VapeGateUpdateReceiver extends BroadcastReceiver {
    public static final IntentFilter VAPE_GATE_INTENT_FILTER = new IntentFilter("uri.egr.vapegate.update");
    public static final String EXTRA_CONNECTION_UPDATE = "uri.egr.vapegate.connection_update";
    public static final String EXTRA_READ_UPDATE = "uri.egr.vapegate.read_update";
}
