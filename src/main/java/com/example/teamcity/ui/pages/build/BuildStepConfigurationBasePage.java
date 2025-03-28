package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public abstract class BuildStepConfigurationBasePage extends BasePage {

    protected SelenideElement stepNameInput = $(By.id("buildStepName"));
    protected SelenideElement stepIdInput = $(By.id("newRunnerId"));
    protected SelenideElement saveButton = $(By.name("submitButton"));

    @Step("Нажать на кнопку Save")
    protected void clickSaveButton() {
        saveButton.click();
    }


}
