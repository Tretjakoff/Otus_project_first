package pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import annotations.PageValidation;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@PageValidation("template://h1[text()='%s']")
public class CoursePage extends AnyPageAbs<CoursePage> {

  public CoursePage(WebDriver driver) {
    super(driver);
  }

  @Step("Проверяем отображение страницы")
  public CoursePage pageShouldBeOpened(String name) {
    String locator = String.format(markerLocator, name);

    assertThat(standartWaiter.waitForElementVisible($(By.xpath(locator))))
        .as("Error")
        .isTrue();

    return this;
  }
}
