package com.otus.components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.inject.Inject;
import com.otus.annotations.Component;
import com.otus.support.GuiceScoped;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Component("xpath://div[./a[@href='/']]")
public class BlockHeader extends AnyComponentAbs<BlockHeader> {

  @Inject
  public BlockHeader(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public void clickSectionByName(String name) {
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
  }
}
