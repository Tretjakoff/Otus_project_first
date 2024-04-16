package com.otus.driver;

import com.otus.exceptions.DriverTypeNotSupported;
import org.openqa.selenium.WebDriver;

public interface IDriverFactory {
  WebDriver getDriver() throws DriverTypeNotSupported;
}
