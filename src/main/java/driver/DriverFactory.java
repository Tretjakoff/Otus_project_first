package driver;

import driver.impl.ChromeWebDriver;
import driver.impl.IDriver;
import exceptions.DriverTypeNotSupported;
import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.ActionsListener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.Locale;

public class DriverFactory implements IDriverFactory {

  private String browserType = System.getProperty("browser", "chrome").toLowerCase(Locale.ROOT);

  @Override
  public WebDriver getDriver() {

    switch (this.browserType) {
      case "chrome": {

        WebDriverManager.chromiumdriver().setup();

        IDriver<ChromeOptions> browserSettings = new ChromeWebDriver();

        WebDriver driver = new EventFiringDecorator<>(new ActionsListener())
                .decorate(new ChromeDriver(browserSettings.getDriverOptions()));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        return driver;
      }
      default:
        try {
          throw new DriverTypeNotSupported(this.browserType);
        } catch (DriverTypeNotSupported ex) {
          ex.printStackTrace();
          return null;
        }
    }
  }
}
