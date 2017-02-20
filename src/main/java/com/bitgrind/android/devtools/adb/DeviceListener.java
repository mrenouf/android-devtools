package com.bitgrind.android.devtools.adb;

import java.util.Collection;

/**
 * Created by mrenouf on 2/20/17.
 */
public interface DeviceListener {
    void onDeviceConnected(AndroidDevice device);

    void onDeviceRemoved(AndroidDevice device);
}
