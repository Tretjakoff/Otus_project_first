package components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import annotations.Component;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Component("xpath://div[./a[@href='/']]")
public class BlockHeader extends AnyComponentAbs<BlockHeader> {

  public BlockHeader(WebDriver driver) {
    super(driver);
  }

  @Step("Выбираем секцию: {name}")
  public BlockEducation clickSectionByName(String name) {
    Actions actions = new Actions(driver);
    WebElement element = getComponentEntity().findElement(By.xpath(String.format(".//*[@title='%s']", name)));

    WebElement subMenuElement = getComponentEntity().findElement(By.xpath(".//nav/div[3]"));
    String classAttrValue = subMenuElement.getAttribute("class");
    actions.moveToElement(element).build().perform();
    boolean waitForCondition = standartWaiter.waitForCondition(driver1 ->
        !getComponentEntity().findElement(By.xpath("//nav/div[3]")).getAttribute("class")
            .equals(classAttrValue));
    assertThat(waitForCondition)
        .as("Error")
        .isTrue();
    return new BlockEducation(driver);
  }
}
