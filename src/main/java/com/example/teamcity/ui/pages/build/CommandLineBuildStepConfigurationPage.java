package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

public class CommandLineBuildStepConfigurationPage extends BuildStepConfigurationBasePage {

    private final SelenideElement scriptInputField = $(".CodeMirror-lines");

    @Step("Ввод скрипта {script}")
    public CommandLineBuildStepConfigurationPage sendScript(String script) {
        scriptInputField
                .should(Condition.appear, BASE_WAITING)
                .click();
        actions().sendKeys(script).perform();
        return this;
    }

    @Override
    public CommandLineBuildStepConfigurationPage clickSaveButton() {
        super.clickSaveButton();
        return this;
    }
}
