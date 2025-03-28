package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildPage extends CreateBasePage {
    private static final String BUILD_SHOW_MODE = "createBuildTypeMenu";
    public SelenideElement buildNameErrorMessage = $("#error_buildTypeName");

    @Step("Открытие страницы создания билда")
    public static CreateBuildPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_SHOW_MODE), CreateBuildPage.class);
    }

    public CreateBuildPage createForm(String url) {
        createBaseForm(url);
        return this;
    }

    @Step("Создание билда с name {buildName}")
    public void setUpBuildType(String buildName) {
        buildTypeNameInput.val(buildName);
        proceedButton.click();
    }


}
