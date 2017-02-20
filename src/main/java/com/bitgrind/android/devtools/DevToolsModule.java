package com.bitgrind.android.devtools;

import com.bitgrind.android.devtools.adb.DeviceList;
import dagger.Module;
import dagger.Provides;
import se.vidstige.jadb.AdbServerLauncher;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.Subprocess;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Map;

@Module
public class DevToolsModule {

    @Provides
    DevToolsController getDevToolsController(AdbServerLauncher serverLauncher, JadbConnection connection) {
        return new DevToolsController(serverLauncher, connection);
    }

    @Provides
    Subprocess getSubprocess() {
        return new Subprocess();
    }

    @Provides
    @Named("environment")
    Map<String, String> getEnvironment() {
        return System.getenv();
    }

    @Provides
    AdbServerLauncher getAdbServerLauncher(Subprocess subprocess, @Named("environment") Map<String, String> environment) {
        return new AdbServerLauncher(subprocess, environment);
    }

    @Provides
    @Singleton
    DeviceList getDeviceList() {
        return new DeviceList();
    }

    @Provides
    JadbConnection getJadbConnection() {
        return new JadbConnection();
    }

}
