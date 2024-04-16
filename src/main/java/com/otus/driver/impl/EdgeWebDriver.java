package com.otus.driver.impl;

import org.openqa.selenium.edge.EdgeOptions;

public class EdgeWebDriver implements IDriver {

  @Override
  public EdgeOptions getDriverOptions() {
    EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.addArguments("--no-sandbox");
    edgeOptions.addArguments("--no-first-run");
    edgeOptions.addArguments("--enable-extensions");
    edgeOptions.addArguments("--homepage=about:blank");
    edgeOptions.addArguments("--ignore-certificate-errors");
    edgeOptions.addArguments("--start-maximized");

    return edgeOptions;
  }
}
