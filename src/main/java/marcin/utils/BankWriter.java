package marcin.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import marcin.entity.Bank;

public class BankWriter {

	public void writeToFile(List<Bank> banks) {
		if(banks == null || banks.size() == 0)
			return;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			createPath();
			File file = new File(String.format("%s%s%s.txt", BankReader.LOCATION, File.separator, LocalDate.now()));
			file.createNewFile();
			objectMapper.writeValue(file, banks);
			System.out.println("New file created: " + file.getAbsolutePath() + "\n" + banks.toString() + "\n");
		} catch (IOException e) {
			System.out.println("Error when writing to file");
			e.printStackTrace();
		}
	}

	private void createPath() throws IOException {
		File locationFile = new File(BankReader.LOCATION);
		if (!locationFile.exists()) {
			System.out.println("Location does not not exist. Creating...");
			locationFile.mkdirs();
			if (!locationFile.exists())
				throw new IOException("Cannot creat the Directory Location folder");
		}
	}

}
