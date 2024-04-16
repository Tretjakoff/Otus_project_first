package com.otus.driver;

import com.otus.driver.impl.ChromeWebDriver;
import com.otus.driver.impl.EdgeWebDriver;
import com.otus.driver.impl.FirefoxWebDriver;
import com.otus.listeners.ActionsListener;
import com.otus.exceptions.DriverTypeNotSupported;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.Locale;

public class DriverFactory implements IDriverFactory {

  private String browserType = System.getProperty("browser", "chrome").toLowerCase(Locale.ROOT);

  @Override
  public WebDriver getDriver() {

    switch (this.browserType) {
      case "chrome": {

        WebDriverManager.chromiumdriver().setup();
        ChromeWebDriver browserSettings = new ChromeWebDriver();

        return new EventFiringDecorator<>(new ActionsListener())
                .decorate(new ChromeDriver(browserSettings.getDriverOptions()));
      }
      case "firefox": {

        WebDriverManager.firefoxdriver().setup();
        FirefoxWebDriver browserSettings = new FirefoxWebDriver();

        return new EventFiringDecorator<>(new ActionsListener())
            .decorate(new FirefoxDriver(browserSettings.getDriverOptions()));
      }
      case "edge": {

        WebDriverManager.edgedriver().setup();
        EdgeWebDriver browserSettings = new EdgeWebDriver();

        return new EventFiringDecorator<>(new ActionsListener())
            .decorate(new EdgeDriver(browserSettings.getDriverOptions()));
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
