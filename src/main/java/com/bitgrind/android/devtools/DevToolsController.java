package com.bitgrind.android.devtools;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import se.vidstige.jadb.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DevToolsController extends AbstractController {

    private final JadbConnection adb;

    @FXML
    protected Label status;

    @FXML
    protected ListView<JadbDevice> devices;
    private AdbServerLauncher serverLauncher;

    @Inject
    public DevToolsController(AdbServerLauncher serverLauncher, JadbConnection adb) {
        this.serverLauncher = serverLauncher;
        this.adb = adb;
    }

    private void checkServer() {
        boolean connected = false;
        int attempts = 0;
        while (!connected && attempts < 3) {
            System.err.print("Connecting to ADB...");
            status.setText("Connecting to ADB...");
            try {
                attempts++;
                System.err.println("Connecting to ADB... (attempt " + attempts  +")");
                adb.getHostVersion();
                connected = true;
            } catch (IOException e) {
            } catch (JadbException e) {
            }
            if (!connected) {
                try {
                    System.err.println("Starting ADB server... (attempt " + attempts + ")");
                    status.setText("Starting ADB...");
                    serverLauncher.launch();
                } catch (IOException e) {
                } catch (InterruptedException e) {
                }
            }
        }
        if (connected) {
            System.err.println("Connected");
            //status.setText("Connected!");
            //TODO: fallback/error dialog?
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("location: " + location + " resources: " + resources);
        checkServer();

        try {
            adb.createDeviceWatcher(new DeviceDetectionListener() {
                @Override
                public void onDetect(List<JadbDevice> devices) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

                @Override
                public void onException(Exception e) {

                }
            });
        } catch (IOException e) {

        } catch (JadbException e) {

        }
    }

    @FXML
    protected void handleButtonAction(ActionEvent event) {
        status.setText("Hello world!");
    }

    @Override
    protected String getFxmlResource() {
        return "main.fxml";
    }

    @Override
    protected ResourceBundle getFxmlResourceBundle() {
        return null;
    }

    @FXML
    public void brew() {
        //heater.on();
        //pump.pump();
        //result.setText(" [_]P coffee! [_]P");
        //heater.off();
    }
}
