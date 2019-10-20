package marcin.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import marcin.entity.Bank;

@PrepareForTest({ FileUtils.class })
@RunWith(PowerMockRunner.class)
public class BankReaderTest {

	private File file;

	@Before
	public void setUp() throws Exception {
		file = PowerMockito.mock(File.class);

		ObjectMapper mapperMock = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withAnyArguments().thenReturn(mapperMock);
		PowerMockito.mockStatic(FileUtils.class);
	}

	@Test
	public void readBanksFromFileShouldReturnTheListOfBanksStoredInFile() throws Exception {
		String jsonString = "[{\"bankName\":\"Santander\",\"money\":99999.0,\"percentage\":123.0,\"dueDate\":\"17/10/2019\",\"requirements\":\"no requirements here!\",\"additionalInfo\":\"some info here\"}]";

		PowerMockito.when(FileUtils.readFileToString(file)).thenReturn(jsonString);

		List<Bank> banks = BankReader.readBanksFromFile(file);
		Assert.assertEquals("Expected the return list size to equal", banks.size(), 1);
		Assert.assertEquals("Expected the value to equal", banks, prepareBankList());

	}

	@Test
	public void readBanksFromFileShouldReturnEmptyListIfNoBanksAreStoredInFile() throws Exception {
		String jsonString = "[]";

		PowerMockito.when(FileUtils.readFileToString(file)).thenReturn(jsonString);

		List<Bank> banks = BankReader.readBanksFromFile(file);
		Assert.assertNotNull("Expected the return value to not be null", banks);
		Assert.assertEquals("Expected the return list size to equal", banks.size(), 0);
		Assert.assertEquals("Expected the value to equal", banks, new ArrayList<>());

	}

	@Test
	public void readBanksFromFileShouldReturnEmptyListWhenIOExceptionWasThrown() throws Exception {
		PowerMockito.when(FileUtils.readFileToString(file)).thenThrow(IOException.class);

		List<Bank> banks = BankReader.readBanksFromFile(file);
		Assert.assertNotNull("Expected the return value to not be null", banks);
		Assert.assertEquals("Expected the return list size to equal", banks.size(), 0);
		Assert.assertEquals("Expected the value to equal", banks, new ArrayList<>());

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
