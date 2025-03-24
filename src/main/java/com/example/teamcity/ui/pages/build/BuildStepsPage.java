package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildStepElement;
import com.example.teamcity.ui.pages.BasePage;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuildStepsPage extends BasePage {

    private static final String BUILD_STEPS_URL = "/admin/editBuildRunners.html?id=buildType:%s";
    private final ElementsCollection buildSteps = $$(By.className("editBuildStepRow"));
    private final SelenideElement runBuildButton = $("span[class*='btn-group_run']");
    private final SelenideElement title = $(By.className("noBorder"));

    public List<BuildStepElement> getBuildStepElements() {
        return generatePageElements(buildSteps, BuildStepElement::new);
    }


    public static BuildStepsPage open(String buildTypeId) {
        return Selenide.open(BUILD_STEPS_URL.formatted(buildTypeId), BuildStepsPage.class);
    }

    public void runBuild() {
        title.should(Condition.appear, BASE_WAITING);
        this.runBuildButton.click();
    }
}
