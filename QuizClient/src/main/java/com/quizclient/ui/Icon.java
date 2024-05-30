package com.quizclient.ui;

import java.io.IOException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableDoubleProperty;
import javafx.css.StyleConverter;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.quizclient.enums.AwesomeIconEnum;

public class Icon extends VBox{
	public final static String DEFAULT_ICON_SIZE = "16.0";

	@FXML Label label;

	private final ObjectProperty<AwesomeIconEnum> valueProperty = new SimpleObjectProperty<AwesomeIconEnum>();

	private final StyleableDoubleProperty sizeProperty = new SimpleStyleableDoubleProperty(new CssMetaData<Icon, Number>("-fa-size", new StyleConverter<>()) {

		@Override
		public boolean isSettable(final Icon icon) {
			return true;
		}

		@Override
		public StyleableProperty<Number> getStyleableProperty(
				final Icon icon) {
			return sizeProperty;
		}
	});

	public Icon(AwesomeIconEnum icon) {
		this();
		setValue(icon);
	}

	public Icon() {
		final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("icon.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public AwesomeIconEnum getValue() {
		return valueProperty().get();
	}

	public void setValue(final AwesomeIconEnum value) {
		label.setText(value.toString());
		valueProperty().set(value);
	}

	public ObjectProperty<AwesomeIconEnum> valueProperty() {
		return valueProperty;
	}

	public double getSize() {
		return sizeProperty().get();
	}

	public void setSize(final double size) {
		label.setStyle("-fx-font-family: FontAwesome; -fx-font-size: " + size + ";");
		sizeProperty().set(size);
	}

	public DoubleProperty sizeProperty() {
		return sizeProperty;
	}
}
