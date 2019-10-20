package marcin.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import marcin.entity.Bank;

public class BankReader {

	public static List<Bank> readBanksFromFile(File file) {
		List<Bank> banks = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

		try {
			String json = FileUtils.readFileToString(file);
			if (!json.startsWith("[")) {
				banks.add(objectMapper.readValue(json, Bank.class));
			} else {
				banks = objectMapper.readValue(json, new TypeReference<List<Bank>>() {
				});
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
		return (List<Bank>) banks;
	}

	public static File getLastModifiedFile() {
		File lastFile = null;
		if (BankReader.FOLDER.exists()) {
			File[] arr = FOLDER.listFiles(); 
			Arrays.sort(arr, Comparator.comparingLong(File::lastModified).reversed());
			lastFile = arr[0];
		}

		return lastFile;
	}

	public static final String LOCATION = System.getProperty("user.dir") + File.separator + "Banks";

	public static final File FOLDER = new File(BankReader.LOCATION);

}
