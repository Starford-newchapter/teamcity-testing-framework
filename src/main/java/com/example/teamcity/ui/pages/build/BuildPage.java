package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class BuildPage extends BasePage {

    private static final String BUILD_URL = "/buildConfiguration/%s";
    public SelenideElement buildNameTitle = $("h1[class*='BuildTypePageHeader']");
    public SelenideElement buildStatus = $("div[class*='Build__status']");

    public static BuildPage open(String buildTypeId) {
        return Selenide.open(BUILD_URL.formatted(buildTypeId), BuildPage.class);
    }

}

