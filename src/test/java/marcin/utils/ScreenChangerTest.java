package marcin.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

@PrepareForTest({ FXUtils.class })
@RunWith(PowerMockRunner.class)
public class ScreenChangerTest {

	ScreenChanger changer;

	@Before
	public void setUp() {
		changer = new ScreenChanger();
	}

	@Test
	public void mainWindowStringShouldBeDefined() throws Exception {
		Assert.assertEquals("Expected the window to be defined", "MainWindow.fxml", ScreenChanger.MAIN_WINDOW);
	}

	@Test
	public void addBankStringShouldBeDefined() throws Exception {
		Assert.assertEquals("Expected the window to be defined", "AddBank.fxml", ScreenChanger.ADD_BANK);
	}

	@Test
	public void showBanksStringShouldBeDefined() throws Exception {
		Assert.assertEquals("Expected the window to be defined", "ShowBanks.fxml", ScreenChanger.SHOW_BANKS);
	}

	@Test
	public void checkResourceStringShouldCallIsValidString() throws Exception {
		PowerMockito.mockStatic(FXUtils.class);
		PowerMockito.when(FXUtils.isValidString(ArgumentMatchers.anyString())).thenReturn(true);
		String result = WhiteboxImpl.invokeMethod(changer, "checkResourceString", "dummyWindow.fxml");
		Assert.assertEquals("Expected the result to contain the .fxml ending", "dummyWindow.fxml", result);
		PowerMockito.verifyStatic(FXUtils.class);
		FXUtils.isValidString(ArgumentMatchers.anyString());
	}

	@Test
	public void checkResourceStringShouldReturnTheMainWindowIfResourceIsInvalid() throws Exception {
		PowerMockito.mockStatic(FXUtils.class);
		PowerMockito.when(FXUtils.isValidString(ArgumentMatchers.anyString())).thenReturn(false);
		String result = WhiteboxImpl.invokeMethod(changer, "checkResourceString", "qqqqqq");
		Assert.assertEquals("Expected the result to contain the .fxml ending", ScreenChanger.MAIN_WINDOW, result);
	}

	@Test
	public void checkResourceStringShouldAddFxmlEndingIfResourceDoesNotIncludeIt() throws Exception {
		PowerMockito.mockStatic(FXUtils.class);
		PowerMockito.when(FXUtils.isValidString(ArgumentMatchers.anyString())).thenReturn(true);
		String result = WhiteboxImpl.invokeMethod(changer, "checkResourceString", "dummyWindow");
		Assert.assertEquals("Expected the result to contain the .fxml ending", "dummyWindow.fxml", result);
	}

	@Test
	public void checkResourceStringShouldNotAddFxmlEndingIfResourceAlreadyIncludesIt() throws Exception {
		PowerMockito.mockStatic(FXUtils.class);
		PowerMockito.when(FXUtils.isValidString(ArgumentMatchers.anyString())).thenReturn(true);
		String result = WhiteboxImpl.invokeMethod(changer, "checkResourceString", "dummyWindow.fxml");
		Assert.assertEquals("Expected the result to contain the .fxml ending", "dummyWindow.fxml", result);
	}

	@Test
	public void checkResourceStringShouldReturnTheDefaultWindowIfTheResourceIsNotAValidString() throws Exception {
		String result = WhiteboxImpl.invokeMethod(changer, "checkResourceString", "dummyWindow");
		Assert.assertEquals("Expected the result to contain the .fxml ending", "dummyWindow.fxml", result);
	}

	@Test
	public void checkResourceStringShouldAppendTheExtensionIfOneIsNotPresent() throws Exception {
		String result = WhiteboxImpl.invokeMethod(changer, "checkResourceString", "dummyWindow");
		Assert.assertEquals("Expected the result to contain the .fxml ending", "dummyWindow.fxml", result);
	}

	@Test
	public void checkResourceStringShouldReturnTheCorrectFileName() throws Exception {
		String result = WhiteboxImpl.invokeMethod(changer, "checkResourceString", "dummyWindow.fxml");
		Assert.assertEquals("Expected the result to contain the .fxml ending", "dummyWindow.fxml", result);
	}

}
