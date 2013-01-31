package third.MovieSearcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class SettingsDefaultImpl implements Settings{

	private static SettingsDefaultImpl instance;

	public static SettingsDefaultImpl getInstance() {
		if (instance == null) {
			instance = new SettingsDefaultImpl();
		}
		return instance;
	}

	private Properties properties;

	private SettingsDefaultImpl() {
		try {
			properties = new Properties();
			InputStreamReader fis = new InputStreamReader(new FileInputStream("./src/main/resources/properties/movies.properties"), "UTF-8");
			properties.load(fis);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getSettings(String key) {
		return properties.getProperty(key);
	}
}
