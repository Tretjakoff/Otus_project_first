package driver.impl;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class ChromeWebDriver implements IDriver {

  @Override
  public ChromeOptions getDriverOptions() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--no-sandbox");
    chromeOptions.addArguments("--no-first-run");
    chromeOptions.addArguments("--enable-extensions");
    chromeOptions.addArguments("--homepage=about:blank");
    chromeOptions.addArguments("--ignore-certificate-errors");
    chromeOptions.setCapability("browserName", "chrome");
    chromeOptions.setCapability("browserVersion", "123.0");
    return chromeOptions;
  }
}
