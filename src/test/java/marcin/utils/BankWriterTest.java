package marcin.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

import marcin.entity.Bank;

@PrepareForTest({ BankWriter.class })
@RunWith(PowerMockRunner.class)
public class BankWriterTest {

	private BankWriter writer;

	private File file;

	private ObjectMapper mapperMock;

	@Before
	public void setUp() throws Exception {
		writer = new BankWriter();
		file = PowerMockito.mock(File.class);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);

		mapperMock = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withAnyArguments().thenReturn(mapperMock);
	}

	@Test
	public void writeToFileShouldCallCreateNewFileAndWriteValue() throws Exception {
		PowerMockito.when(file.exists()).thenReturn(true);

		List<Bank> bankList = prepareBankList();
		writer.writeToFile(bankList);

		Mockito.verify(file, Mockito.times(1)).createNewFile();
		Mockito.verify(mapperMock, Mockito.times(1)).writeValue(file, bankList);
	}

	@Test
	public void writeToFileShouldDoNothingWhenTheListIsEmptyOrIsNull() throws Exception {
		writer.writeToFile(new ArrayList<>());

		PowerMockito.verifyZeroInteractions(file);
		PowerMockito.verifyZeroInteractions(mapperMock);

		writer.writeToFile(null);

		PowerMockito.verifyZeroInteractions(file);
		PowerMockito.verifyZeroInteractions(mapperMock);
	}

	@Test
	public void createPathShouldNotCallMkdirsMethodIfFileExists() throws Exception {
		PowerMockito.when(file.exists()).thenReturn(true);

		WhiteboxImpl.invokeMethod(writer, "createPath");

		Mockito.verify(file, Mockito.times(0)).mkdirs();
		Mockito.verify(file, Mockito.times(1)).exists();
	}

	@Test
	public void createPathShouldCreateTheDirsIfFileDoesNotExist() throws Exception {
		PowerMockito.when(file.exists()).thenReturn(false, true);

		WhiteboxImpl.invokeMethod(writer, "createPath");

		Mockito.verify(file, Mockito.times(1)).mkdirs();
		Mockito.verify(file, Mockito.times(2)).exists();
	}

	@Test(expected = IOException.class)
	public void createPathShouldThrowIOExceptionIfFileCouldNotBeCreated() throws Exception {
		PowerMockito.when(file.exists()).thenReturn(false, false);

		WhiteboxImpl.invokeMethod(writer, "createPath");

		Mockito.verify(file, Mockito.times(1)).mkdirs();
		Mockito.verify(file, Mockito.times(2)).exists();
	}

	private List<Bank> prepareBankList() {
		List<Bank> banks = new ArrayList<>();
		Bank bank = new Bank();

		bank.setBankName("Santander");
		bank.setPercentage(123.0d);
		bank.setMoney(99999.0d);
		bank.setAdditionalInfo("some info here");
		bank.setDueDate("17/10/2019");
		bank.setRequirements("no requirements here!");

		banks.add(bank);

		return banks;
	}
}
