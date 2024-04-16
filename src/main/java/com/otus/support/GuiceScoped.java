package com.otus.support;

import com.otus.driver.DriverFactory;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

@ScenarioScoped
public class GuiceScoped {
  public WebDriver driver = null;

  public void selectBrowser(String browserName) {
    System.setProperty("browser", browserName.toLowerCase(Locale.ROOT));
    this.driver = new DriverFactory().getDriver();
  }
}
