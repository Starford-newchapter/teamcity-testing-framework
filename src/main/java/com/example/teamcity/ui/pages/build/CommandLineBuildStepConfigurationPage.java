package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CommandLineBuildStepConfigurationPage extends BuildStepConfigurationBasePage {

    private final SelenideElement scriptContainer = $(".CodeMirror");
    private final SelenideElement scriptInputArea = scriptContainer.$("textarea");


    public CommandLineBuildStepConfigurationPage inputScript(String script) {
        scriptInputArea.setValue(script);
        return this;
    }

    @Override
    public CommandLineBuildStepConfigurationPage clickSaveButton() {
        super.clickSaveButton();
        return this;
    }
}
