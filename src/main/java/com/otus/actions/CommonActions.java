package com.otus.actions;

import com.github.javafaker.Faker;
import com.google.inject.Inject;
import com.otus.support.GuiceScoped;
import com.otus.waiters.StandartWaiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class CommonActions<T> {

  protected WebDriver driver;
  protected GuiceScoped guiceScoped;
  protected StandartWaiter standartWaiter;
  protected Faker faker;

  @Inject
  public CommonActions(GuiceScoped guiceScoped) {
    this.driver = guiceScoped.driver;
    PageFactory.initElements(driver, this);

    standartWaiter = new StandartWaiter(driver);
    faker = new Faker();
  }

  public WebElement $(By locator) {
    return driver.findElement(locator);
  }
}
