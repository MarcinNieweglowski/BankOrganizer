package marcin.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import marcin.entity.Bank;

public class BankReaderTest {

	BankReader reader;

	@Before
	public void setUp() {
		reader = new BankReader();
	}

	@Test
	public void readBanksFromFileShouldReturnTheListOfBanksStoredInFile() throws Exception {

	}

	@Test
	public void readBanksFromFileShouldReturnEmptyListIfNoBanksAreStoredInFile() throws Exception {

	}

	@Test
	public void getLastModifiedFileShouldReturnTheLastModifiedFile() throws Exception {

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
