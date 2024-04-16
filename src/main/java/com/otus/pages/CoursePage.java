package com.otus.pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.inject.Inject;
import com.otus.annotations.PageValidation;

import com.otus.support.GuiceScoped;
import org.openqa.selenium.By;

@PageValidation("template://h1[text()='%s']")
public class CoursePage extends AnyPageAbs<CoursePage> {

  @Inject
  public CoursePage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public CoursePage pageShouldBeOpened(String name) {
    String locator = String.format(markerLocator, name);

    assertThat(standartWaiter.waitForElementVisible($(By.xpath(locator))))
        .as("Error")
        .isTrue();

    return this;
  }
}
