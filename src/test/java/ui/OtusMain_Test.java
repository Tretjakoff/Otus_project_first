package ui;

import com.google.inject.Inject;
import components.BlockEducation;
import components.BlockHeader;
import extensions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.MainPage;

@ExtendWith(UIExtension.class)
public class OtusMain_Test {

    @Inject
    private MainPage mainPage;

    @Inject
    private BlockHeader blockHeader;

    @Inject
    private BlockEducation blockEducation;

    @Test
    public void categoryFromEducation() {

        mainPage
                .open();

        blockHeader
                .clickSectionByName("Обучение");

        String name = blockEducation
                .setTitle("Все курсы")
                .getRandomCategoryName();
        blockEducation
                .setTitle("Все курсы")
                .clickRandomCategory(name)
                .directionIsSelected(name);

    }
}
