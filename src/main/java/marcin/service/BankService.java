package marcin.service;

import java.util.List;

import marcin.dto.BankDTO;

public interface BankService {

	public void saveBanks(List<BankDTO> banks);

	public List<BankDTO> getAllBanks();

}
