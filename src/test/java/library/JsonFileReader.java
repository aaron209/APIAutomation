package library;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonFileReader {

	//this class is an alternative of reading credentials from json file
	private final String credentialFilePath = "src/test/testData/Credentials.json";
	private List<Credentials> credentialsList;

	public JsonFileReader() {
		credentialsList = getCredentialsData();
	}

	private List<Credentials> getCredentialsData() {
		Gson gson = new Gson();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(credentialFilePath));
			Credentials[] cred = gson.fromJson(reader, Credentials[].class);
			return Arrays.asList(cred);
		} catch (Exception e) {
			throw new RuntimeException("");
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ignore) {

			}
		}
	}

	public final Credentials getCredentials(String id) {
		return credentialsList.stream().filter(x -> x.id.equalsIgnoreCase(id)).findAny().get();
	}
}
