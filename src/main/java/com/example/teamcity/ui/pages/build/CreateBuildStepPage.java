package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.RunnerElement;
import com.example.teamcity.ui.enums.RunnerType;
import com.example.teamcity.ui.pages.BasePage;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreateBuildStepPage extends BasePage {

    private static final String CREATE_BUILD_STEP_URL = "/admin/editRunType.html?id=buildType:%s";
    private final SelenideElement runnerTypesSearchInput = $("input[data-test='runner-item-filter']");
    private final ElementsCollection runnerElements = $$("div[class*='SelectBuildRunners__container']");


    public static CreateBuildStepPage open(String buildTypeId) {
        return Selenide.open(CREATE_BUILD_STEP_URL.formatted(buildTypeId), CreateBuildStepPage.class);
    }

    public List<RunnerElement> getRunnerElements() {
        return generatePageElements(runnerElements, RunnerElement::new);
    }

    public void selectRunnerType(RunnerType runnerType) {
        runnerTypesSearchInput.val(runnerType.getType());
        getRunnerElements().stream()
                .filter(runner -> runner.getRole().getText().contains(runnerType.getType()))
                .findFirst()
                .ifPresent(runner -> runner.getRole().click());
    }

}
