package com.bitgrind.android.devtools;

import hugo.boss.DebugLog;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mrenouf on 2/18/17.
 */
public abstract class AbstractController implements Initializable {
    private Parent fxmlRoot;
    /**
     * Gets the URL to the FXML file describing the view presented by this controller.<br/>
     *
     * A default implementation would look like this:<br/>
     * <code>
     * 	return getClass().getResource("/myView.fxml");
     * </code>
     *
     * @return FXML resource URL
     */
    protected URL getFxmlResourceUrl() {
        return getClass().getResource("/" + getFxmlResource());
    }

    protected abstract String getFxmlResource();

    /**
     * @return Localization bundle for the FXML labels or <code>null</code>.
     */
    protected abstract ResourceBundle getFxmlResourceBundle();

    /**
     * Creates a FXML loader used in {@link #loadFxml()}. This method can be overwritten for further loader customization.
     *
     * @return Configured loader ready to load.
     */
    @DebugLog
    protected FXMLLoader createFxmlLoader() {
        final URL fxmlUrl = getFxmlResourceUrl();
        final ResourceBundle rb = getFxmlResourceBundle();
        final FXMLLoader loader = new FXMLLoader(fxmlUrl, rb);
        loader.setController(this);
        return loader;
    }

    /**
     * Loads the view presented by this controller from the FXML file return by {@link #getFxmlResourceUrl()}. This method can only be invoked once.
     *
     * @return Parent view element.
     */
    @DebugLog
    protected final synchronized Parent loadFxml() {
        if (fxmlRoot == null) {
            final FXMLLoader loader = createFxmlLoader();
            try {
                System.err.println(loader.getLocation());
                fxmlRoot = loader.load();
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Could not load FXML file from location: " + loader.getLocation(), e);
            } catch (IOException e) {
                throw new IllegalStateException("Could not load FXML file from location: " + loader.getLocation(), e);
            }
        }
        return fxmlRoot;
    }

    /**
     * Init stage using a scene with the root node from the FXML file.
     */
    @DebugLog
    public void initStage(Stage stage) {
        final Parent root = loadFxml();
        stage.setScene(new Scene(root));
        stage.sizeToScene();
    }

}
