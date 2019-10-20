package marcin.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import marcin.utils.FXUtils;
import marcin.utils.ScreenChanger;

public class MainController implements IFXController {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private HBox hBox;

	@FXML
	private Button exitBtn;

	@FXML
	private Button addBankBtn;

	@FXML
	private Button showAllBtn;

	@Override
	public void changeScreen(ActionEvent event) throws Exception {

	}

	@Override
	public void initialize() {
		rootPane.getChildren().addAll();

		HBox.setMargin(addBankBtn, FXUtils.INSETS);
		HBox.setMargin(showAllBtn, FXUtils.INSETS);
		HBox.setMargin(exitBtn, FXUtils.INSETS);

		FXUtils.organiseButton(addBankBtn, "Add new Bank");
		FXUtils.organiseButton(showAllBtn, "Show all Banks");
		FXUtils.organiseButton(exitBtn, "Exit");
	}

	@FXML
	public void exit(ActionEvent event) {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}

	public void showAll(ActionEvent event) {
		ScreenChanger changer = new ScreenChanger();
		changer.changeScreen(event, getClass(), ScreenChanger.SHOW_BANKS);
	}

	public void addBank(ActionEvent event) {
		ScreenChanger changer = new ScreenChanger();
		changer.changeScreen(event, getClass(), ScreenChanger.ADD_BANK);
	}

}
