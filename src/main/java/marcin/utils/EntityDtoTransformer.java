package marcin.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import marcin.dto.BankDTO;
import marcin.entity.Bank;

public class EntityDtoTransformer {

	public static List<Bank> transformDtoToEntity(List<BankDTO> existingBanks) {
		List<Bank> banks = new ArrayList<>();
		for (BankDTO dto : existingBanks) {
			Bank entity = new Bank();
			entity.setBankName(dto.getBankName());
			entity.setAdditionalInfo(dto.getAdditionalInfo());
			entity.setDueDate(EntityDtoTransformer.parseDateToString(dto.getDueDate()));
			entity.setMoney(dto.getMoney());
			entity.setPercentage(dto.getPercentage());
			entity.setRequirements(dto.getRequirements());
			banks.add(entity);
		}
		return banks;
	}

	public static List<BankDTO> transformEntityToDto(List<Bank> banks) {
		List<BankDTO> dtos = new ArrayList<>();
		for (Bank entity : banks) {
			BankDTO dto = new BankDTO();
			dto.setBankName(entity.getBankName());
			dto.setAdditionalInfo(entity.getAdditionalInfo());
			dto.setDueDate(EntityDtoTransformer.parseStringToLocalDate(entity.getDueDate()));
			dto.setMoney(entity.getMoney());
			dto.setPercentage(entity.getPercentage());
			dto.setRequirements(entity.getRequirements());
			dto.setDaysLeft(ChronoUnit.DAYS.between(LocalDate.now(), dto.getDueDate()));

			dtos.add(dto);
		}
		return dtos;
	}

	private static String parseDateToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return formatter.format(date);
	}

	private static LocalDate parseStringToLocalDate(String str) {
		return LocalDate.parse(str.trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

}
