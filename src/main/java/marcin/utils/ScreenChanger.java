package marcin.utils;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenChanger {

	public void changeScreen(ActionEvent event, Class<?> caller, String resource) {
		try {
			System.out.println("changeScreen clicked!");
			checkResourceString(resource);
			Parent parent = FXMLLoader.load(caller.getResource(resource));
			Scene scene = new Scene(parent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String checkResourceString(String resource) {
		if (!FXUtils.isValidString(resource)) {
			System.out.println("Invalid window name, defaulting to Main Window");
			resource = ScreenChanger.MAIN_WINDOW;
		}

		if (!resource.endsWith(ScreenChanger.FXML_ENDING))
			resource = resource + ScreenChanger.FXML_ENDING;

		return resource;
	}

	private static final String FXML_ENDING = ".fxml";

	public static final String MAIN_WINDOW = "MainWindow.fxml";

	public static final String ADD_BANK = "AddBank.fxml";

	public static final String SHOW_BANKS = "ShowBanks.fxml";

	public static final int SCENE_HEIGHT = 600;

	public static final int SCENE_WIDTH = 750;

}
