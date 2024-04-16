package com.otus.pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.otus.annotations.UrlPrefix;

import com.otus.support.GuiceScoped;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@UrlPrefix("/catalog/courses")
public class CatalogCoursesPage extends AnyPageAbs<CatalogCoursesPage> {

  @Inject
  public CatalogCoursesPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
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
