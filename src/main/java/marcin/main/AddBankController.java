package marcin.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import marcin.dto.BankDTO;
import marcin.service.BankService;
import marcin.service.BankServiceImpl;
import marcin.utils.FXUtils;
import marcin.utils.ScreenChanger;

public class AddBankController implements IFXController {

	@FXML
	private BorderPane borderPane;

	@FXML
	private TextField bankName;

	@FXML
	private TextField additionalInfo;

	@FXML
	private TextField money;

	@FXML
	private TextField percentage;

	@FXML
	private TextField requirements;

	@FXML
	private DatePicker dueDate;

	@FXML
	private GridPane gridPane;

	@FXML
	private TextArea logs;

	@FXML
	private Label title;

	@FXML
	private Label bankNameLabel;

	@FXML
	private Label requirementsLabel;

	@FXML
	private Label moneyLabel;

	@FXML
	private Label percentageLabel;

	@FXML
	private Label dueDateLabel;

	@FXML
	private Label additionalInfoLabel;

	@FXML
	private VBox vBox;

	@FXML
	private Button mainWindowBtn;

	@FXML
	private Button addBankBtn;

	@FXML
	private Button showAllBtn;

	ObjectMapper objectMapper = new ObjectMapper();

	BankService bankService = new BankServiceImpl();

	@Override
	public void initialize() {
		organiseView();
		setPromptTexts();
		verifyFields();
	}

	@Override
	public void changeScreen(ActionEvent event) throws Exception {
		ScreenChanger changer = new ScreenChanger();
		changer.changeScreen(event, getClass(), ScreenChanger.MAIN_WINDOW);
	}

	@FXML
	public void showAll(ActionEvent event) {
		ScreenChanger changer = new ScreenChanger();
		changer.changeScreen(event, getClass(), ScreenChanger.SHOW_BANKS);
	}

	public void addBank() throws IOException {
		if (verifyFields()) {
			BankDTO bankDTO = createNewBank();
			List<BankDTO> banks = bankService.getAllBanks();
			banks.add(bankDTO);
			bankService.saveBanks(banks);
			clearInputFields();
		}
	}

	private void setPromptTexts() {
		bankName.setPromptText("Ex: Santander");
		additionalInfo.setPromptText("???");
		money.setPromptText("Ex: 100000");
		percentage.setPromptText("Ex: 5");
		dueDate.setPromptText("Choose date: dd.MM.yyyy");
		requirements.setPromptText("What to do...");
	}

	private boolean verifyFields() {
		boolean bankName = FXUtils.validateTextField(this.bankName);
		boolean money = FXUtils.validateNumberField(this.money);
		boolean percentage = FXUtils.validateNumberField(this.percentage);
		boolean dueDate = (this.dueDate.getValue() != null);

		logs.setText("");
		if (!bankName)
			logs.appendText("Bank name is required.\n");
		if (!money)
			logs.appendText("Money is required.\n");
		if (!percentage)
			logs.appendText("Percentage is required.\n");
		if (!dueDate)
			logs.appendText("Due date is required.\n");
		if (!bankName || !money || !percentage || !dueDate)
			logs.appendText("\n");

		return bankName && money && percentage && dueDate;
	}

	private void clearInputFields() {
		bankName.clear();
		additionalInfo.clear();
		requirements.clear();
		money.clear();
		percentage.clear();
		dueDate.getEditor().clear();
	}

	private BankDTO createNewBank() {
		BankDTO bankDTO = new BankDTO();
		bankDTO.setBankName(bankName.getText());
		bankDTO.setAdditionalInfo(additionalInfo.getText());
		bankDTO.setRequirements(requirements.getText());
		bankDTO.setMoney(Double.parseDouble(money.getText()));
		bankDTO.setPercentage(Double.parseDouble(percentage.getText()));
		bankDTO.setDueDate((LocalDate) dueDate.getValue());

		return bankDTO;
	}

	private void organiseView() {
		borderPane.getChildren().removeAll(gridPane, logs, title, bankNameLabel, bankName, requirements,
				requirementsLabel, money, moneyLabel, percentage, percentageLabel, dueDate, dueDateLabel,
				additionalInfo, additionalInfoLabel, vBox, addBankBtn, mainWindowBtn, showAllBtn);

		BorderPane.setMargin(gridPane, FXUtils.INSETS);
		BorderPane.setMargin(logs, FXUtils.INSETS);
		BorderPane.setMargin(vBox, FXUtils.INSETS);

		borderPane.setPadding(FXUtils.INSETS);
		borderPane.setPrefHeight(gridPane.getHeight() + title.getHeight());

		borderPane.setTop(title);
		borderPane.setLeft(vBox);
		borderPane.setRight(logs);

		title.setFont(Font.font(TIMES_NEW_ROMAN, FontWeight.BOLD, FONT_SIZE));
		title.setAlignment(Pos.CENTER);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setText("Add a new bank");

		organiseVBox();
		organiseGrid();

		logs.setPrefWidth(ScreenChanger.SCENE_WIDTH / 2);
		logs.setPrefHeight(Double.MAX_VALUE);
		logs.setWrapText(Boolean.TRUE);
	}

	private void organiseVBox() {
		vBox.setMaxHeight(Double.MAX_VALUE);
		VBox.setMargin(addBankBtn, FXUtils.INSETS);
		VBox.setMargin(mainWindowBtn, FXUtils.INSETS);
		VBox.setMargin(showAllBtn, FXUtils.INSETS);

		FXUtils.organiseButton(addBankBtn, "Add!");
		FXUtils.organiseButton(mainWindowBtn, "Main Window");
		FXUtils.organiseButton(showAllBtn, "Show all Banks");
	}

	private void organiseGrid() {
		GridPane.setConstraints(bankNameLabel, 0, 0);
		GridPane.setConstraints(bankName, 1, 0);
		GridPane.setConstraints(requirementsLabel, 0, 1);
		GridPane.setConstraints(requirements, 1, 1);
		GridPane.setConstraints(moneyLabel, 0, 2);
		GridPane.setConstraints(money, 1, 2);
		GridPane.setConstraints(percentageLabel, 0, 3);
		GridPane.setConstraints(percentage, 1, 3);
		GridPane.setConstraints(dueDateLabel, 0, 4);
		GridPane.setConstraints(dueDate, 1, 4);
		GridPane.setConstraints(additionalInfoLabel, 0, 5);
		GridPane.setConstraints(additionalInfo, 1, 5);

		FXUtils.organiseLabel(bankNameLabel, "Bank name:");
		FXUtils.organiseLabel(requirementsLabel, "Requirements:");
		FXUtils.organiseLabel(moneyLabel, "Money:");
		FXUtils.organiseLabel(percentageLabel, "Percentage:");
		FXUtils.organiseLabel(dueDateLabel, "Due date:");
		FXUtils.organiseLabel(additionalInfoLabel, "Additional info:");
	}

	private static final String TIMES_NEW_ROMAN = "Times New Roman";

	private static final int FONT_SIZE = 20;

}
