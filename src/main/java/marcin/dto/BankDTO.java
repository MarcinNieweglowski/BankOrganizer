package marcin.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BankDTO {

	private String bankName;

	private Double money;

	private Double percentage;

	private LocalDate dueDate;

	private String requirements;

	private String additionalInfo;

	private Long daysLeft;

}
