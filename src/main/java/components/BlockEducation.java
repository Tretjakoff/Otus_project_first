package components;

import annotations.Component;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CatalogCoursesPage;

import java.util.List;

@Component("xpath://div[contains(@class, 'sc-piuiz2-0') and contains(., '%s')]")
public class BlockEducation extends AnyComponentAbs<BlockEducation> {

  public BlockEducation(WebDriver driver) {
    super(driver);
  }

  @Step("Возвращаем категорию курсов: {name}")
  public CatalogCoursesPage clickRandomCategory(String name) {
    getComponentEntity()
        .findElement(By.xpath(String.format(".//div[./*[text()='Все курсы']]//a[text()='%s']", name)))
        .click();

    return new CatalogCoursesPage(driver);
  }

  @Step("Возвращаем рандомную категорию курсов")
  public String getRandomCategoryName() {
    List<WebElement> listWebElements = getComponentEntity()
        .findElements(By.xpath(".//div[./*[text()='Все курсы']]//a[not(contains(@href, '/subscription')) and "
            + "not(contains(@href, '/spec'))]"));

    return faker.options().nextElement(listWebElements).getText().replaceAll("\\s*\\(.*$", "").trim();
  }
}
