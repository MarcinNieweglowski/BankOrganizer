package marcin.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXUtils {

	public static boolean isValidString(String text) {
		text = text.trim();
		return text.length() > 0 && text != "" && text.matches(REGEX);
	}

	public static boolean validateTextField(TextField field) {
		field.setOnKeyPressed(event -> {
			addTextStyle(FXUtils.isValidString(field.getText()), field);
		});
		return FXUtils.isValidString(field.getText());
	}

	public static boolean validateNumberField(TextField field) {
		field.setOnKeyPressed(event -> {
			addTextStyle(field.getText().matches(NUMBER_REGEX), field);
		});

		if (field.getText().length() == 0)
			return false;
		return field.getText().matches(NUMBER_REGEX);
	}

	public static void addTextStyle(boolean isValid, Control field) {
		if (isValid) {
			field.setStyle("-fx-text-fill:green;");
		} else {
			field.setStyle("-fx-text-fill:red;-fx-font-weight:bold");
		}
	}

	public static void organiseButton(Button button, String text) {
		button.setAlignment(Pos.CENTER);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setText(text);
	}

	public static void organiseLabel(Label label, String text) {
		label.setAlignment(Pos.CENTER);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setText(text);
	}

	public static final String REGEX = "[A-Za-z0-9\\s\\./]*";

	public static final String NUMBER_REGEX = "[0-9\\.]*";

	public static final Insets INSETS = new Insets(10);

}
