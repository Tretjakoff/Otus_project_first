package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.components.BlockCatalogCourses;
import com.otus.pages.CatalogCoursesPage;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;

public class CatalogCoursesPageSteps {

  @Inject
  private CatalogCoursesPage catalogCoursesPage;
  @Inject
  private BlockCatalogCourses blockCatalogCourses;

  @Пусть("Открыта страница каталога курсов")
  public void openCatalogCoursesPage() {
    catalogCoursesPage.open();
  }

  @Если("Кликнуть на плитку курса {string}")
  public void clickCatalogCoursesPage(String nameCourse) {
    blockCatalogCourses
        .setTitle("Каталог")
        .clickCourseByName(nameCourse);
  }

  @Тогда("Найти курсы, которые стартуют раньше и позже всех, и проверить название и дату начала")
  public void searchCoursesIsBeforeAndIsAfter() {
    blockCatalogCourses
        .setTitle("Каталог")
        .verifyCourses(true)
        .verifyCourses(false);
  }
}
