package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;

public class CreateBuildPage extends CreateBasePage {
    private static final String BUILD_SHOW_MODE = "createBuildTypeMenu";

    public static CreateBuildPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_SHOW_MODE), CreateBuildPage.class);
    }

    public CreateBuildPage createForm(String url) {
        createBaseForm(url);
        return this;
    }

    public void setUpBuildType(String buildName) {
        buildTypeNameInput.val(buildName);
        proceedButton.click();
    }


}
