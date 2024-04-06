package components;

import annotations.Component;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CoursePage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Component("xpath://section[.//*[text()='%s']]")
public class BlockCatalogCourses extends AnyComponentAbs<BlockCatalogCourses> {

    public BlockCatalogCourses(WebDriver driver) {
        super(driver);
    }

    public CoursePage clickCourseByName(String name) {
        Optional<WebElement> elementBlockCourse = getComponentEntity().findElements(By.xpath(".//a[./div]")).stream()
                .filter(webElement ->
                        webElement.getText().contains(name)).findFirst();

        if (elementBlockCourse.isPresent()) {
            WebElement webElement = elementBlockCourse.get();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
            webElement.click();
        } else {
            throw new RuntimeException("Элемент с текстом '%s' не найден".formatted(name));
        }

        return new CoursePage(driver);
    }

    public BlockCatalogCourses verifyCourses(boolean isEarliest) {

        LocalDate date = getDate(isEarliest);
        List<WebElement> listCardCourses = getListCardCourses(date);
        List<String> titles = getTitles(listCardCourses);

        Document document = null;

        for (int i = 0; i < listCardCourses.size(); i++) {
            String url = listCardCourses.get(i).getAttribute("href");
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String title = document.select("h1").text();
            String dateCourse = document.selectXpath("(//div[contains(@class, 'galmep')]//p)[1]").text();
            assertThat(title)
                    .as("Error")
                    .isEqualTo(titles.get(i));
            assertThat(dateCourse)
                    .as("Error")
                    .isEqualTo(date.format(DateTimeFormatter.ofPattern("dd MMMM")));
        }
        return this;
    }

    private List<WebElement> getListCardCourses(LocalDate date) {
        return getComponentEntity().findElements(By.xpath(".//a/div[last()]")).stream()
                .filter((WebElement element) -> getLocalDate(element).isEqual(date))
                .map(element -> element.findElement(By.xpath("./parent::a")))
                .toList();
    }

    private List<String> getTitles(List<WebElement> listCardCourses) {
        return listCardCourses.stream()
                .map(element -> element.findElement(By.xpath("./h6")).getText())
                .toList();
    }

    private LocalDate getDate(boolean isEarliest) {
        List<LocalDate> dates = getComponentEntity().findElements(By.xpath(".//a/div[last()]")).stream()
                .map(this::getLocalDate)
                .toList();

        return dates.stream()
                .reduce((LocalDate buff, LocalDate item) -> {
                    if(isEarliest) {
                        return buff.isBefore(item) ? buff: item;
                    } else {
                        return buff.isAfter(item) ? buff: item;
                    }
                }).orElseThrow();
    }

    private LocalDate getLocalDate(WebElement element) {
        return LocalDate.parse(element.getText()
                        .replaceAll("\\s*·.*$", "").trim(),
                DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.forLanguageTag("ru-RU")));
    }
}
