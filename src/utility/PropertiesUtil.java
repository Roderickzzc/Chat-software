package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static PropertiesUtil p = null;
	private Properties properties;

	private PropertiesUtil() {
		properties = new Properties();
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PropertiesUtil getPropertiesUtil() {
		if (p == null) {
			p = new PropertiesUtil();
		}
		return p;
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}

}
