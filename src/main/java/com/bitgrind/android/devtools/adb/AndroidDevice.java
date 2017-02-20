package com.bitgrind.android.devtools.adb;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.managers.PropertyManager;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidDevice {

    private Map<String, String> properties = new HashMap<>();

    private long lastUpdated;
    private final JadbDevice jadbDevice;
    private final String serial;

    boolean present;

    AndroidDevice(@Nonnull JadbDevice device) {
        this.jadbDevice = Preconditions.checkNotNull(device, "device cannot be null");
        this.serial = Preconditions.checkNotNull(device.getSerial(), "device.serial cannot be null");

        PropertyManager propertyManager = new PropertyManager(device);
        try {
            properties = propertyManager.getprop();
        } catch (IOException e) {
        } catch (JadbException e) {
        }
        System.out.println(super.hashCode() + " " + properties.get(PropertyKeys.PRODUCT_DEVICE));
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isPresent() {
        return this.present = present;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidDevice that = (AndroidDevice) o;
        return Objects.equals(serial, that.serial);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serial);
    }

    @Override
    public String toString() {
        String device = Objects.toString(properties.get(PropertyKeys.PRODUCT_DEVICE), "(unknown)");
        String model =  Strings.nullToEmpty(properties.get(PropertyKeys.PRODUCT_MODEL));
        String manuf =  Strings.nullToEmpty(properties.get(PropertyKeys.PRODUCT_MANUF));
        StringBuilder sb = new StringBuilder(device);
        if (!model.isEmpty() || !manuf.isEmpty()) {
            sb.append(" (");
            if (!manuf.isEmpty()) {
                sb.append(manuf).append(" ");
            }
            sb.append(model);
            sb.append(")");
        }
        sb.append(" [" + serial + "]");
        return sb.toString();

    }
}
