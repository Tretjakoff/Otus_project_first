package driver;

import driver.impl.ChromeWebDriver;
import driver.impl.IDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class DriverFactory implements IDriverFactory {

  private String remoteUrl = System.getProperty("remoteUrl", "http://localhost:4444/wd/hub");
  private String chromeVersion = System.getProperty("chromeVersion", "123.0");

  public WebDriver getDriver() {
    WebDriver driver = null;

    ChromeOptions options = new ChromeOptions();
    options.setCapability("browserVersion", chromeVersion); // передача версии драйвера

    try {
      driver = new RemoteWebDriver(new URL(remoteUrl), options);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
    }

    return driver;
  }
}
