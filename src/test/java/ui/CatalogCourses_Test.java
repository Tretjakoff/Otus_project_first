package ui;

import com.google.inject.Inject;
import components.BlockCatalogCourses;
import extensions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CatalogCoursesPage;

@ExtendWith(UIExtension.class)
public class CatalogCourses_Test {

    @Inject
    private CatalogCoursesPage catalogCoursesPage;

    @Inject
    private BlockCatalogCourses blockCatalogCourses;

    @Test
    public void changeCourseByName() {
        String nameCourse = "Delivery Manager";
        catalogCoursesPage
                .open();

        blockCatalogCourses
                .setTitle("Каталог")
                .clickCourseByName(nameCourse)
                .pageShouldBeOpened(nameCourse);

    }

    @Test
    public void verifyEarlyAndLateCourses() {

        catalogCoursesPage
                .open();

        blockCatalogCourses
                .setTitle("Каталог")
                .verifyCourses(true)
                .verifyCourses(false);

    }


}
