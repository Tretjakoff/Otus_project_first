package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.pages.CoursePage;
import io.cucumber.java.ru.Тогда;

public class CoursePageStep {

  @Inject
  private CoursePage coursePage;

  @Тогда("Откроется страница карточки курса {string}")
  public void headerShouldBeSameAs(String nameCourse) {
    coursePage.pageShouldBeOpened(nameCourse);
  }
}
