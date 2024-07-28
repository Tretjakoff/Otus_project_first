package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory implements IDriverFactory {

  private String remoteUrl = System.getProperty("remoteUrl", "http://10.0.2.15:80/wd/hub");
  private String chromeVersion = System.getProperty("chromeVersion", "123.0");

  public WebDriver getDriver() {
    WebDriver driver = null;

    ChromeOptions options = new ChromeOptions();
    options.setCapability("browserName", "chrome");
    options.setCapability("browserVersion", chromeVersion);

    try {
      driver = new RemoteWebDriver(new URL(remoteUrl), options);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
    }

    return driver;
  }
}
