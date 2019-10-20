package marcin.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import junit.framework.Assert;
import marcin.entity.Bank;

//@PrepareForTest({BankWriter.class})
@RunWith(PowerMockRunner.class)
public class BankWriterTest {

	BankWriter writer;

	@Before
	public void setUp() {
		writer = new BankWriter();
	}

//	@Test
//	public void writeToFileShouldWriteToFile() throws Exception {
//		List<Bank> list = prepareBankList();
//		writer.writeToFile(list);
//		Assert.assertTrue("Expected the file to have been created", BankReader.LOCATION);
//	}
	
	@Test
	public void createPathShouldCheckIfTheLocationExists() throws Exception {
		File file = PowerMockito.mock(File.class);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
		PowerMockito.when(file.exists()).thenReturn(true);
		
		WhiteboxImpl.invokeMethod(writer, "createPath");
		
		Mockito.verify(file, Mockito.times(1)).exists();
	}

	private List<Bank> prepareBankList() {
		List<Bank> banks = new ArrayList<>();
		Bank bank = new Bank();

		bank.setBankName("dummy name");
		bank.setPercentage(12.34d);
		bank.setMoney(99999.99d);
		bank.setAdditionalInfo("some info here");
		bank.setDueDate("23/10/2019");
		bank.setRequirements("no requirements");

		Bank bank2 = new Bank();

		bank2.setBankName("xxx");
		bank2.setPercentage(33d);
		bank2.setMoney(111111.1d);
		bank2.setAdditionalInfo("some info here");
		bank2.setDueDate("11/12/2024");
		bank2.setRequirements("no requirements");

		banks.add(bank);
		banks.add(bank2);

		return banks;
	}
}
