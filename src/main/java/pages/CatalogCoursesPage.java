package pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import annotations.UrlPrefix;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@UrlPrefix("/catalog/courses")
public class CatalogCoursesPage extends AnyPageAbs<CatalogCoursesPage> {

  public CatalogCoursesPage(WebDriver driver) {
    super(driver);
  }

  @Step("Проверяем, что категория выбрана")
  public CatalogCoursesPage directionIsSelected(String name) {
    WebElement locator = driver
        .findElement(By.xpath(String.format("//label[text()='%s']/parent::div//*[@type = 'checkbox']", name)));

    assertThat(locator.isSelected())
        .as("Error")
        .isTrue();

    return this;
  }
}
