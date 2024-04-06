package listeners;

import org.openqa.selenium.*;

public class WebDriverListener {

  public void beforeAlertAccept(WebDriver webDriver) {

  }

  public void afterAlertAccept(WebDriver webDriver) {

  }

  public void afterAlertDismiss(WebDriver webDriver) {

  }

  public void beforeAlertDismiss(WebDriver webDriver) {

  }

  public void beforeNavigateTo(String s, WebDriver webDriver) {

  }

  public void afterNavigateTo(String s, WebDriver webDriver) {

  }

  public void beforeNavigateBack(WebDriver webDriver) {

  }

  public void afterNavigateBack(WebDriver webDriver) {

  }

  public void beforeNavigateForward(WebDriver webDriver) {

  }

  public void afterNavigateForward(WebDriver webDriver) {

  }

  public void beforeNavigateRefresh(WebDriver webDriver) {

  }

  public void afterNavigateRefresh(WebDriver webDriver) {

  }

  public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {

  }

  public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {

  }

  public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].style.border = '5px ridge rgba(100, 220, 50, .6)'", webElement);

  }

  public void afterClickOn(WebElement webElement, WebDriver webDriver) {
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].style.border = 'none'", webElement);
  }

  public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

  }

  public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

  }

  public void beforeScript(String s, WebDriver webDriver) {

  }

  public void afterScript(String s, WebDriver webDriver) {

  }

  public void beforeSwitchToWindow(String s, WebDriver webDriver) {

  }

  public void afterSwitchToWindow(String s, WebDriver webDriver) {

  }

  public void onException(Throwable throwable, WebDriver webDriver) {

  }

  public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {

  }

  public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {

  }

  public void beforeGetText(WebElement webElement, WebDriver webDriver) {

  }

  public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {

  }
}
