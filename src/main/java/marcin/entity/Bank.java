package marcin.entity;

import lombok.Data;

@Data
public class Bank {

	private String bankName;

	private Double money;

	private Double percentage;

	private String dueDate;

	private String requirements;

	private String additionalInfo;

}
