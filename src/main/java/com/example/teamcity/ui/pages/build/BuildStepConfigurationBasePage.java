package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public abstract class BuildStepConfigurationBasePage extends BasePage {

    protected SelenideElement stepNameInput = $(By.id("buildStepName"));
    protected SelenideElement stepIdInput = $(By.id("newRunnerId"));
    protected SelenideElement saveButton = $(By.name("submitButton"));

    protected BuildStepConfigurationBasePage clickSaveButton() {
        saveButton.click();
        return  this;
    }


}
