package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuildPage extends BasePage {

    private static final String BUILD_URL = "/buildConfiguration/%s";
    public SelenideElement buildNameTitle = $("h1[class*='BuildTypePageHeader']");
    private final SelenideElement buildRunButton = $("button[data-test='run-build']");
    private final SelenideElement buildLogButton = $("a[title='Build Log']");
    private final SelenideElement searchLogButton = $("button[data-hint-container-id='buildlog-search-button']");
    private final SelenideElement searchLogInput = $("input[class*='BuildLogSearch__input']");
    private final SelenideElement submitSearchButton = $("button[type='submit']");
    private final ElementsCollection buildStepLogMessage = $$("span[class*= 'LogMessageSearchHighlighting']");


    public BuildPage() {
        buildNameTitle.should(Condition.appear, BASE_WAITING);
    }

    @Step("Открытие страницы билда c id={buildTypeId}")
    public static BuildPage open(String buildTypeId) {
        return Selenide.open(BUILD_URL.formatted(buildTypeId), BuildPage.class);
    }

    @Step("Нажать на кнопку Run Build")
    public BuildPage clickRunBuild() {
        this.buildRunButton.click();
        return this;
    }

    @Step("Нажать на  билд {index}")
    public BuildPage clickBuildElement(int index) {
        SelenideElement buildElement = $(By.xpath("(//button[contains(@class, 'Details__button')])[" + index + "]"));
        buildElement.click();
        return this;
    }

    @Step("Нажать на  кнопку Build log")
    public BuildPage clickBuildLog() {
        buildLogButton.click();
        return this;
    }

    @Step("Поиск лога {logMessage} в билде ")
    public ElementsCollection searchLog(String logMessage) {
        searchLogButton.click();
        searchLogInput.val(logMessage);
        submitSearchButton.click();
        return buildStepLogMessage;
    }

}

