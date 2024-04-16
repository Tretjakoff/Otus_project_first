package com.otus.components;

import com.google.inject.Inject;
import com.otus.annotations.Component;
import com.otus.support.GuiceScoped;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Component("xpath://div[contains(@class, 'sc-piuiz2-0') and contains(., '%s')]")
public class BlockEducation extends AnyComponentAbs<BlockEducation> {

  @Inject
  public BlockEducation(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public void clickRandomCategory(String name) {
    getComponentEntity()
        .findElement(By.xpath(String.format(".//div[./*[text()='Все курсы']]//a[text()='%s']", name)))
        .click();
  }

  public String getRandomCategoryName() {
    List<WebElement> listWebElements = getComponentEntity()
        .findElements(By.xpath(".//div[./*[text()='Все курсы']]//a[not(contains(@href, '/subscription')) and "
            + "not(contains(@href, '/spec'))]"));

    return faker.options().nextElement(listWebElements).getText().replaceAll("\\s*\\(.*$", "").trim();
  }
}
