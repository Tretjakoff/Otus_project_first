package com.otus.driver.impl;

import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxWebDriver implements IDriver{

  @Override
  public FirefoxOptions getDriverOptions() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.addArguments("--no-sandbox");
    firefoxOptions.addArguments("--no-first-run");
    firefoxOptions.addArguments("--enable-extensions");
    firefoxOptions.addArguments("--homepage=about:blank");
    firefoxOptions.addArguments("--ignore-certificate-errors");
    firefoxOptions.addArguments("--start-maximized");

    return firefoxOptions;
  }
}
