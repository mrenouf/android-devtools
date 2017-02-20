package com.bitgrind.android.devtools.adb;

import se.vidstige.jadb.*;
import se.vidstige.jadb.managers.PropertyManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public class AdbTest {
    private static final String PROP_PRODUCT_NAME = "ro.product.name"; // "marlin"
    private static final String PROP_PRODUCT_BOARD = "ro.product.board"; // "marlin"
    private static final String PROP_PRODUCT_DEVICE = "ro.product.device"; // "marlin"
    private static final String PROP_PRODUCT_MODEL = "ro.product.model"; // "Pixel XL"
    private static final String PROP_PRODUCT_MANUF = "ro.product.manufacturer"; // "Google"
    private static final String PROP_PRODUCT_BRAND = "ro.product.brand"; // "google"
    private static final String PROP_PRODUCT_LOCALE = "ro.product.locale"; // "en-US"
    private static final String PROP_PRODUCT_CPU_ABI = "ro.product.cpu.abi"; // "arm64-v8a"
    private static final String PROP_PRODUCT_CPU_ABILIST = "ro.product.cpu.abilist"; // "arm64-v8a,armeabi-v7a,armeabi"

    private static final String PROP_BLUETOOTH_NAME = "net.bt.name";
    private static final String PROP_BTADDR_PATH = "ro.bt.bdaddr_path";

    private static final String PROP_BOARD_PLATFORM = "ro.board.platform"; // "msm8996"


    private static final String PROP_LCD_DENSITY = "ro.sf.lcd_density";
    private static final String PROP_BUILD_VERSION_INC = "ro.build.version.incremental"; // Build ID, as in "3638325"

    private static final String PROP_BUILD_FLAVOR = "ro.build.flavor"; // ex: "marlin-user", or "marlin-userdebug"
    private static final String PROP_BUILD_TYPE = "ro.build.type"; // ex: "user", "userdebug", or "eng"
    private static final String PROP_BUILD_ID = "ro.build.id";
    private static final String PROP_BUILD_FINGERPRINT = "ro.build.fingerprint";
    private static final String PROP_BUILD_TAGS = "ro.build.tags"; // "release-keys" or "devtools-keys"
    private static final String PROP_BUILD_VERSION_RELEASE = "ro.build.version.release"; // "7.1.2"


    private static final String PROP_BUILD_PRODUCT = "ro.build.product";
    private static final String PROP_BUILD_CHARACTERISTICS = "ro.build.characteristics";

    static class RemoteProperties extends Properties {

        private final JadbDevice device;
        private final String path;

        public RemoteProperties(JadbDevice device, String path) {
            super();
            this.device = device;
            this.path = path;

        }

        private void load() throws IOException {
            try (InputStream path = device.executeShell("cat", this.path)) {
                super.load(path);
            } catch (JadbException e) {
                throw new IOException(e);
            }
        }


    }

    public static void main(String[] args) throws IOException, JadbException, InterruptedException {
        new AdbServerLauncher(new Subprocess(), Collections.emptyMap()).launch();

        JadbConnection jadb = new JadbConnection();
        DeviceList list = new DeviceList();
        DeviceWatcher deviceWatcher = jadb.createDeviceWatcher(list);
        list.addListener(new DeviceListener() {
            @Override
            public void onDeviceConnected(AndroidDevice device) {
                System.out.println(System.currentTimeMillis() + " Connected: " + device);
            }

            @Override
            public void onDeviceRemoved(AndroidDevice device) {
                System.out.println(System.currentTimeMillis() + " Removed: " + device);

            }
        });
        deviceWatcher.watch();
        try {
            List<JadbDevice> devices = jadb.getDevices();
            for (JadbDevice d : devices) {
                System.out.println(new PropertyManager(d).getprop());
                System.out.println("btaddr: " + Stream.readAll(d.executeShell("cat", "/sys/module/bdaddress/parameters/bdaddress"), StandardCharsets.UTF_8));

                RemoteProperties props = new RemoteProperties(d, "/system/build.prop");
                props.load();
                System.out.println(props.getProperty(PROP_PRODUCT_NAME));
                for (String k : props.stringPropertyNames()) {
                    if (k.startsWith("ro.bt")) {
                        System.out.println(k + " -> " + props.getProperty(k));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JadbException e) {
            e.printStackTrace();
        }
    }
}
