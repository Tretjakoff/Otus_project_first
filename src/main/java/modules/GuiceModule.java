package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import components.BlockCatalogCourses;
import components.BlockEducation;
import components.BlockHeader;
import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import pages.CatalogCoursesPage;
import pages.MainPage;

public class GuiceModule extends AbstractModule {
  private final WebDriver driver = new DriverFactory().getDriver();

  @Provides
  public WebDriver getDriver() {
    return driver;
  }

  @Provides
  @Singleton
  public MainPage getMainPage() {
    return new MainPage(driver);
  }

  @Provides
  @Singleton
  public BlockCatalogCourses getBlockCatalogCourses() {
    return new BlockCatalogCourses(driver);
  }

  @Provides
  @Singleton
  public CatalogCoursesPage getCatalogCoursesPage() {
    return new CatalogCoursesPage(driver);
  }

  @Provides
  @Singleton
  public BlockHeader getBlockHeader() {
    return new BlockHeader(driver);
  }

  @Provides
  @Singleton
  public BlockEducation getBlockEducation() {
    return new BlockEducation(driver);
  }
}
