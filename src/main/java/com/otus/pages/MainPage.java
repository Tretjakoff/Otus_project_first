package com.otus.pages;

import com.google.inject.Inject;
import com.otus.annotations.UrlPrefix;
import com.otus.support.GuiceScoped;

@UrlPrefix("/")
public class MainPage extends AnyPageAbs<MainPage> {

  @Inject
  public MainPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }
}
