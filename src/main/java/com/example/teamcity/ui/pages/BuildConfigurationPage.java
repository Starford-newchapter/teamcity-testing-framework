package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BuildConfigurationPage extends BasePage {

    private static final String BUILD_CONFIGURATION_URL = "/admin/editBuild.html?id=buildType:%s";
    public SelenideElement buildNameTitle = $(By.cssSelector("div.selected.buildType a"));

    public static BuildConfigurationPage open(String buildTypeId) {
        return Selenide.open(BUILD_CONFIGURATION_URL.formatted(buildTypeId), BuildConfigurationPage.class);
    }
}
