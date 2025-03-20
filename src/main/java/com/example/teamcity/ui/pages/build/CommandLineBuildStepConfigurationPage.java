package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

public class CommandLineBuildStepConfigurationPage extends BuildStepConfigurationBasePage {

    private final SelenideElement scriptInputField = $(".CodeMirror");


    public CommandLineBuildStepConfigurationPage sendScript(String script) {
        scriptInputField.click();
        actions().sendKeys(script).perform();
        return this;
    }

    @Override
    public CommandLineBuildStepConfigurationPage clickSaveButton() {
        super.clickSaveButton();
        return this;
    }
}
