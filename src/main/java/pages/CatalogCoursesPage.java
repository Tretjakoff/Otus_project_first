package pages;

import annotations.UrlPrefix;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@UrlPrefix("/catalog/courses")
public class CatalogCoursesPage extends AnyPageAbs<CatalogCoursesPage> {

    public CatalogCoursesPage(WebDriver driver) {
        super(driver);
    }

    public CatalogCoursesPage directionIsSelected(String name) {
        WebElement locator = driver
                .findElement(By.xpath(String.format("//label[text()='%s']/parent::div//*[@type = 'checkbox']", name)));

        assertThat(locator.isSelected())
                .as("Error")
                .isTrue();

        return this;
    }
}
