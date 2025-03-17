package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.pages.BasePage;

public class BuildPage extends BasePage {

    private static final String BUILD_URL = "/buildConfiguration/:%s";


    public static BuildPage open(String buildTypeId) {
        return Selenide.open(BUILD_URL.formatted(buildTypeId), BuildPage.class);
    }
}
