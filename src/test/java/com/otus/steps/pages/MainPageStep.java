package com.otus.steps.pages;

import com.google.inject.Inject;
import com.otus.components.BlockEducation;
import com.otus.components.BlockHeader;
import com.otus.pages.CatalogCoursesPage;
import com.otus.pages.MainPage;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;

public class MainPageStep {

  @Inject
  private MainPage mainPage;
  @Inject
  private CatalogCoursesPage catalogCoursesPage;
  @Inject
  private BlockHeader blockHeader;
  @Inject
  private BlockEducation blockEducation;

  @Пусть("Открыта главная страница")
  public void openMainPage() {
    mainPage.open();
  }

  @Если("В заголовке страницы открыть меню \"Обучение\"")
  public void openMenuEducation() {
    blockHeader
        .clickSectionByName("Обучение");
  }

  @Тогда("Выбрать случайную категорию курсов и проверить, что открыт каталог курсов верной категории")
  public void selectCategoryFromEducation() {
    String name = blockEducation
        .setTitle("Все курсы")
        .getRandomCategoryName();
    blockEducation
        .setTitle("Все курсы")
        .clickRandomCategory(name);
    catalogCoursesPage
        .directionIsSelected(name);
  }
}
