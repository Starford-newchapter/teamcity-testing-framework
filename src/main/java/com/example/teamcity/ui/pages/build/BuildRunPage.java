package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class BuildRunPage extends BasePage {

    private final SelenideElement buildStepLogMessage = $("span[class*= 'LogMessageSearchHighlighting']");


    public final SelenideElement buildLogTab = $("span[data-tab-title='Build Log']");
    private final SelenideElement searchLogButton = $("button[data-hint-container-id='buildlog-search-button']");
    private final SelenideElement searchLogInput = $("input[class*='BuildLogSearch__input']");
    private final SelenideElement submitSearchButton = $("button[type='submit']");

    @Step("Проверка наличия лога {logMessage} в билде")
    public BuildRunPage checkLogMessage(String logMessage) {
        buildStepLogMessage.shouldHave(Condition.exactText(logMessage));
        return this;
    }

    @Step("Поиск лога {logMessage} в билде ")
    public BuildRunPage searchLog(String logMessage) {
        searchLogButton.click();
        searchLogInput.val(logMessage);
        submitSearchButton.click();
        return this;
    }

}
