package marcin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import marcin.dto.BankDTO;
import marcin.entity.Bank;
import marcin.utils.BankReader;
import marcin.utils.EntityDtoTransformer;

@PrepareForTest({ EntityDtoTransformer.class, BankReader.class })
@RunWith(PowerMockRunner.class)
public class BankServiceImplTest {

	private BankService bankService;

	@Before
	public void setUp() {
		bankService = new BankServiceImpl();
	}

	@Test
	public void saveBanksShouldCallTransformDtoToEntity() throws Exception {
		List<BankDTO> list = new ArrayList<>();
		PowerMockito.mockStatic(EntityDtoTransformer.class);
		PowerMockito.when(EntityDtoTransformer.transformDtoToEntity(list)).thenReturn(new ArrayList<>());

		bankService.saveBanks(list);

		PowerMockito.verifyStatic(EntityDtoTransformer.class, Mockito.times(1));
		EntityDtoTransformer.transformDtoToEntity(list);
	}

	@Test
	public void getAllBanksShouldCallGetLastModifiedFile() throws Exception {
		PowerMockito.mockStatic(BankReader.class);
		PowerMockito.when(BankReader.getLastModifiedFile()).thenReturn(null);

		bankService.getAllBanks();

		PowerMockito.verifyStatic(BankReader.class, Mockito.times(1));
		BankReader.getLastModifiedFile();
	}

	@Test
	public void getAllBanksShouldCallReadBanksFromFile() throws Exception {
		File file = PowerMockito.mock(File.class);

		PowerMockito.mockStatic(BankReader.class);
		PowerMockito.when(BankReader.getLastModifiedFile()).thenReturn(file);
		PowerMockito.when(BankReader.readBanksFromFile(file)).thenReturn(new ArrayList<>());

		bankService.getAllBanks();

		PowerMockito.verifyStatic(BankReader.class, Mockito.times(1));
		BankReader.readBanksFromFile(file);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getAllBanksShouldCallTransformEntityToDto() throws Exception {
		File file = PowerMockito.mock(File.class);
		List<Bank> entityList = PowerMockito.mock(ArrayList.class);
		List<BankDTO> dtoList = PowerMockito.mock(ArrayList.class);

		PowerMockito.mockStatic(BankReader.class);
		PowerMockito.when(BankReader.getLastModifiedFile()).thenReturn(file);
		PowerMockito.when(BankReader.readBanksFromFile(file)).thenReturn(entityList);
		PowerMockito.mockStatic(EntityDtoTransformer.class);
		PowerMockito.when(EntityDtoTransformer.transformEntityToDto(entityList)).thenReturn(dtoList);

		List<BankDTO> banks = bankService.getAllBanks();

		Assert.assertEquals("Expected the return value to be", banks, dtoList);
	}

	@Test
	public void getAllBanksShouldReturnEmptyListIfNoFileWasFoundInDirectory() throws Exception {
		PowerMockito.mockStatic(BankReader.class);
		PowerMockito.when(BankReader.getLastModifiedFile()).thenReturn(null);

		List<BankDTO> banks = bankService.getAllBanks();

		Assert.assertEquals("Expected the list to be empty", banks.size(), 0);
		Assert.assertNotNull("Expected a list to be returned", banks);
	}

}
