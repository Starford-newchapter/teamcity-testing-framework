package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;


public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";
    public SelenideElement title = $("span[class*='ProjectPageHeader']");

    @Step("Открытие страницы проекта  c projectId {projectId}")
    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

}
