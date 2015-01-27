package hu.virgo.selenium.framework.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class Context {

	private static final String CONF_LOCATION = System.getProperty("baseconfigdir", "src/main/config");
	private String sutPropertiesFileName;
	private Properties properties;
	private Browser browser;

	public SystemUnderTest sut;

	public Context() {
		sutPropertiesFileName = System.getProperty("sut.properties", "selenium-default.properties");
		properties = openFile(sutPropertiesFileName);

		sut = initSut(properties);
		browser = initBrowser(properties);
	}

	public WebDriver getNewDriver() {
		return browser.getNewDriver();
	}

	private Properties openFile(String fileName) {
		Properties prop = new Properties();
		String fileUri = CONF_LOCATION + File.separator + fileName;

		try {
			prop.load(new FileInputStream(fileUri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	private SystemUnderTest initSut(Properties properties) {
		String t = properties.getProperty("sut.type");
		SutType type = SutType.valueOf(t.toUpperCase());

		String baseUrl = properties.getProperty("sut.baseUrl");
		String adminBaseUrl = properties.getProperty("sut.admin.baseUrl");

		return new SystemUnderTest(type, baseUrl, adminBaseUrl);
	}

	private Browser initBrowser(Properties properties) {
		String browserName = properties.getProperty("browser");
		String hubStr = properties.getProperty("browser.hub");

		if (hubStr != null) {
			try {
				URL hub = new URL(hubStr);
				return new Browser(browserName, hub);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			return new Browser(browserName);
		}
	}
}
