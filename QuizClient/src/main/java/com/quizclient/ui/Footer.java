package com.quizclient.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class Footer extends HBox {
    public Footer() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("footer.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
