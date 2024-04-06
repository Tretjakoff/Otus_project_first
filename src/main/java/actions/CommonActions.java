package actions;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import waiters.StandartWaiter;

public abstract class CommonActions<T> {

  protected WebDriver driver;
  protected StandartWaiter standartWaiter;
  protected Faker faker;

  public CommonActions(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);

    standartWaiter = new StandartWaiter(driver);
    faker = new Faker();
  }

  public WebElement $(By locator) {
    return driver.findElement(locator);
  }
}
