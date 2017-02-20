package com.bitgrind.android.devtools.adb;

/**
 * Created by mrenouf on 2/20/17.
 */
public class PropertyKeys {
    public static final String PRODUCT_NAME = "ro.product.name"; // "marlin"
    public static final String PRODUCT_BOARD = "ro.product.board"; // "marlin"
    public static final String PRODUCT_DEVICE = "ro.product.device"; // "marlin"
    public static final String PRODUCT_MODEL = "ro.product.model"; // "Pixel XL"
    public static final String PRODUCT_MANUF = "ro.product.manufacturer"; // "Google"
    public static final String PRODUCT_BRAND = "ro.product.brand"; // "google"
    public static final String PRODUCT_LOCALE = "ro.product.locale"; // "en-US"
    public static final String PRODUCT_CPU_ABI = "ro.product.cpu.abi"; // "arm64-v8a"
    public static final String PRODUCT_CPU_ABILIST = "ro.product.cpu.abilist"; // "arm64-v8a,armeabi-v7a,armeabi"

    public static final String BLUETOOTH_NAME = "net.bt.name";
    public static final String BTADDR_PATH = "ro.bt.bdaddr_path";

    public static final String BOARD_PLATFORM = "ro.board.platform"; // "msm8996"


    public static final String LCD_DENSITY = "ro.sf.lcd_density";
    public static final String BUILD_VERSION_INC = "ro.build.version.incremental"; // Build ID, as in "3638325"

    public static final String BUILD_FLAVOR = "ro.build.flavor"; // ex: "marlin-user", or "marlin-userdebug"
    public static final String BUILD_TYPE = "ro.build.type"; // ex: "user", "userdebug", or "eng"
    public static final String BUILD_ID = "ro.build.id";
    public static final String BUILD_FINGERPRINT = "ro.build.fingerprint";
    public static final String BUILD_TAGS = "ro.build.tags"; // "release-keys" or "devtools-keys"
    public static final String BUILD_VERSION_RELEASE = "ro.build.version.release"; // "7.1.2"


    public static final String BUILD_PRODUCT = "ro.build.product";
    public static final String BUILD_CHARACTERISTICS = "ro.build.characteristics";
}
