package components;

import annotations.Component;
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

    public CatalogCoursesPage clickRandomCategory(String name) {
        getComponentEntity()
                .findElement(By.xpath(String.format(".//div[./*[text()='Все курсы']]//a[text()='%s']", name)))
                .click();

        return new CatalogCoursesPage(driver);
    }

    public String getRandomCategoryName() {
        List<WebElement> listWebElements = getComponentEntity()
                .findElements(By.xpath(".//div[./*[text()='Все курсы']]//a[not(contains(@href, '/subscription')) and " +
                        "not(contains(@href, '/spec'))]"));

        return faker.options().nextElement(listWebElements).getText().replaceAll("\\s*\\(.*$", "").trim();
    }
}
