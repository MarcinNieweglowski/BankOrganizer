package marcin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import marcin.dto.BankDTO;
import marcin.entity.Bank;
import marcin.utils.BankReader;
import marcin.utils.BankWriter;
import marcin.utils.EntityDtoTransformer;

public class BankServiceImpl implements BankService {

	public void saveBanks(List<BankDTO> existingBanks) {
		List<Bank> banks = EntityDtoTransformer.transformDtoToEntity(existingBanks);
		BankWriter writer = new BankWriter();
		writer.writeToFile(banks);
	}

	public List<BankDTO> getAllBanks() {
		File newestFile = BankReader.getLastModifiedFile();
		if (newestFile == null)
			return new ArrayList<>();

		List<Bank> banks = BankReader.readBanksFromFile(newestFile);
		List<BankDTO> dtos = EntityDtoTransformer.transformEntityToDto(banks);
		return dtos;
	}

}
