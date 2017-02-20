package com.bitgrind.android.devtools.adb;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import se.vidstige.jadb.DeviceDetectionListener;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DeviceList implements DeviceDetectionListener {
    Set<AndroidDevice> devices = new HashSet<>();
    Set<DeviceListener> listeners = new HashSet<>();


    @Override
    public void onDetect(List<JadbDevice> detectedDevices) {
        System.out.println("onDetect: " + detectedDevices);
         synchronized (devices) {
            Set<AndroidDevice> detected = new HashSet<>();
            for (JadbDevice jadbDevice : detectedDevices) {
                try {
                    if (jadbDevice.getState() != JadbDevice.State.Unknown) {
                        detected.add(new AndroidDevice(jadbDevice));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JadbException e) {
                    e.printStackTrace();
                }
            }
            Set<AndroidDevice> removed = ImmutableSet.copyOf(Sets.difference(devices, detected));
            Set<AndroidDevice> added = ImmutableSet.copyOf(Sets.difference(detected, devices));

            devices.clear();
            devices.addAll(detected);

            synchronized (listeners) {
                dispatchDevicesConnected(added);
                dispatchDevicesRemoved(removed);
            }
        }
    }

    public void addListener(DeviceListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(DeviceListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    protected void dispatchDevicesConnected(Collection<AndroidDevice> added) {
        for (AndroidDevice device : added) {
            for (DeviceListener listener : listeners) {
                listener.onDeviceConnected(device);
            }
        }
    }

    protected void dispatchDevicesRemoved(Collection<AndroidDevice> removed) {
        for (AndroidDevice device : removed) {
            for (DeviceListener listener : listeners) {
                listener.onDeviceRemoved(device);
            }
        }
    }

    @Override
    public void onException(Exception e) {

    }
}
