package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BuildConfigurationPage extends BasePage {

    private static final String BUILD_CONFIGURATION_URL = "/admin/editBuild.html?id=buildType:%s";
    public SelenideElement buildNameTitle = $("div[class*='buildType']");

    public static BuildConfigurationPage open(String buildTypeId) {
        return Selenide.open(BUILD_CONFIGURATION_URL.formatted(buildTypeId), BuildConfigurationPage.class);
    }
}
