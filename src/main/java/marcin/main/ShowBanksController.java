package marcin.main;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import marcin.dto.BankDTO;
import marcin.service.BankService;
import marcin.service.BankServiceImpl;
import marcin.utils.FXUtils;
import marcin.utils.ScreenChanger;

public class ShowBanksController implements IFXController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private TableView<BankDTO> tableView;

	@FXML
	private TableColumn<BankDTO, String> bankName;

	@FXML
	private TableColumn<BankDTO, Double> money;

	@FXML
	private TableColumn<BankDTO, Double> percentage;

	@FXML
	private TableColumn<BankDTO, String> dueDate;

	@FXML
	private TableColumn<BankDTO, String> requirements;

	@FXML
	private TableColumn<BankDTO, String> additionalInfo;

	@FXML
	private TableColumn<BankDTO, Long> daysLeft;

	@FXML
	private TextField updateRequirements;

	@FXML
	private TextField updateBankName;

	@FXML
	private TextField updateAdditionalInfo;

	@FXML
	private TextField updateMoney;

	@FXML
	private TextField updatePercentage;

	@FXML
	private DatePicker updateDueDate;

	@FXML
	private VBox vBoxScreenChangingBtns;

	@FXML
	private VBox vBox;

	@FXML
	private Button deleteBtn;

	@FXML
	private Button editBtn;

	@FXML
	private Button addBankBtn;

	@FXML
	private Button changeScreenBtn;

	@FXML
	private HBox updateBox;

	@FXML
	private VBox updateFields;

	@FXML
	private TextArea logs;

	private BankDTO highlightedBank;

	private BankService bankService;

	@Override
	public void changeScreen(ActionEvent event) throws Exception {
		ScreenChanger changer = new ScreenChanger();
		changer.changeScreen(event, getClass(), ScreenChanger.MAIN_WINDOW);
	}

	@FXML
	public void addBank(ActionEvent event) {
		ScreenChanger changer = new ScreenChanger();
		changer.changeScreen(event, getClass(), ScreenChanger.ADD_BANK);
	}

	@Override
	public void initialize() {
		bankService = new BankServiceImpl();

		organiseView();
		setUpdatePromptTexts();
		populateTableWithData();

		tableView.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				int index = tableView.getSelectionModel().getSelectedIndex();
				try {
					highlightedBank = tableView.getItems().get(index);
					populateUpdateFields(highlightedBank);
				} catch (IndexOutOfBoundsException | NullPointerException ignored) {
				}
			}
		});
	}

	@FXML
	public void editBank(ActionEvent event) {
		if (highlightedBank != null) {
			List<BankDTO> banks = bankService.getAllBanks();
			if (banks.contains(highlightedBank) && areUpdateFieldsValid()) {
				banks.set(banks.indexOf(highlightedBank), getUpdatedBank());
				bankService.saveBanks(banks);

				populateTableWithData();
				clearUpdateFields();
			}
		}
	}

	@FXML
	public void deleteBank(ActionEvent event) {
		if (highlightedBank != null) {
			List<BankDTO> banks = bankService.getAllBanks();
			if (banks.contains(highlightedBank)) {
				banks.remove(highlightedBank);
				bankService.saveBanks(banks);

				populateTableWithData();
				clearUpdateFields();
			}
		}
	}

	private void organiseView() {
		tableView.setPrefWidth(ScreenChanger.SCENE_WIDTH);
		anchorPane.setPrefWidth(ScreenChanger.SCENE_WIDTH + 50);
		HBox.setMargin(logs, FXUtils.INSETS);
		
		VBox.setMargin(tableView, FXUtils.INSETS);
		VBox.setMargin(vBoxScreenChangingBtns, FXUtils.INSETS);
		
		FXUtils.organiseButton(editBtn, "Update");
		FXUtils.organiseButton(deleteBtn, "Delete");
		FXUtils.organiseButton(changeScreenBtn, "Main Window");
		FXUtils.organiseButton(addBankBtn, "Add new Bank");

		updateFields.getChildren().forEach(nodeElement -> {
			VBox.setVgrow(nodeElement, Priority.ALWAYS);
			VBox.setMargin(nodeElement, FXUtils.INSETS);
			nodeElement.maxWidth(Double.MAX_VALUE);
		});
		vBoxScreenChangingBtns.getChildren().forEach(nodeElement -> {
			VBox.setVgrow(nodeElement, Priority.ALWAYS);
			VBox.setMargin(nodeElement, new Insets(10, 0, 10, 0));
		});
	}

	private void setUpdatePromptTexts() {
		updateBankName.setPromptText("Bank name");
		updateAdditionalInfo.setPromptText("Info");
		updateMoney.setPromptText("PLN");
		updatePercentage.setPromptText("%");
		updateRequirements.setPromptText("???");
		updateDueDate.setPromptText("dd.MM.yyyy");
	}

	@SuppressWarnings("unchecked")
	private void populateTableWithData() {
		tableView.getColumns().clear();

		bankName.setText("Bank");
		bankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));

		money.setCellValueFactory(new PropertyValueFactory<>("money"));
		money.setText("PLN");

		percentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
		percentage.setText("%");

		daysLeft.setCellValueFactory(new PropertyValueFactory<>("daysLeft"));
		daysLeft.setText("Days left");

		dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		dueDate.setText("Date");

		additionalInfo.setCellValueFactory(new PropertyValueFactory<>("additionalInfo"));
		additionalInfo.setText("Info");

		requirements.setCellValueFactory(new PropertyValueFactory<>("requirements"));
		requirements.setText("Requirements");

		tableView.getColumns().addAll(bankName, money, percentage, dueDate, additionalInfo, requirements, daysLeft);

		tableView.getItems().setAll(bankService.getAllBanks());

	}

	private void clearUpdateFields() {
		updateBankName.clear();
		updateAdditionalInfo.clear();
		updateMoney.clear();
		updatePercentage.clear();
		updateDueDate.getEditor().clear();
		updateRequirements.clear();
	}

	private void populateUpdateFields(BankDTO bankDto) {
		updateBankName.setText(bankDto.getBankName());
		updateAdditionalInfo.setText(bankDto.getAdditionalInfo());
		updateMoney.setText(bankDto.getMoney().toString());
		updatePercentage.setText(bankDto.getPercentage().toString());
		updateDueDate.setValue(bankDto.getDueDate());
		updateRequirements.setText(bankDto.getRequirements());
	}

	private boolean areUpdateFieldsValid() {
		boolean bankName = FXUtils.validateTextField(this.updateBankName);
		boolean money = FXUtils.validateNumberField(this.updateMoney);
		boolean percentage = FXUtils.validateNumberField(this.updatePercentage);
		boolean dueDate = this.updateDueDate != null;

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

	private BankDTO getUpdatedBank() {
		BankDTO updated = new BankDTO();

		updated.setBankName(updateBankName.getText());
		updated.setAdditionalInfo(updateAdditionalInfo.getText());
		updated.setMoney(Double.parseDouble(updateMoney.getText()));
		updated.setPercentage(Double.parseDouble(updatePercentage.getText()));
		updated.setDueDate(updateDueDate.getValue());
		updated.setRequirements(updateRequirements.getText());

		return updated;
	}

}
