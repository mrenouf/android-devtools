package com.bitgrind.android.devtools;

import hugo.boss.DebugLog;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class DevToolsApp extends Application {

    DevToolsController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @DebugLog
    @Override
    public void init() throws Exception {
        super.init();
        // launcher thread
        DevTools dt = com.bitgrind.android.devtools.DaggerDevTools.create();
        controller = dt.devToolsController();
    }

    @DebugLog
    @Override
    public void start(Stage stage) throws IOException {
        // Application thread
        controller.initStage(stage);
        //stage.setResizable(false);
        stage.show();
    }


    @DebugLog
    @Override
    public void stop() throws Exception {
        super.stop();
    }
}