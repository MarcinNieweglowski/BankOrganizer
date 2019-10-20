package marcin.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		System.out.println("App starting...");
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		stage.setTitle("Bank Organizer");
		stage.setScene(new Scene(root));
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		System.out.println("Closing app.");
	}

}
