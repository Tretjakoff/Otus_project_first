package com.otus.steps.common;

import com.google.inject.Inject;
import com.otus.support.GuiceScoped;
import io.cucumber.java.ru.Дано;

public class CommonSteps {

  @Inject
  private GuiceScoped guiceScoped;

  @Дано("Открыт браузер {string}")
  public void openBrowser(String browserName) {
    guiceScoped.selectBrowser(browserName);
  }
}
