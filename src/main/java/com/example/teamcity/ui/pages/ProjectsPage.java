package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectAndBuildElement;
import com.example.teamcity.ui.elements.ProjectElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class ProjectsPage extends BasePage {
    private static final String PROJECTS_URL = "/favorite/projects";

    private ElementsCollection projectElements = $$("div[class*='Subproject__container']");
    private ElementsCollection projectsAndBuildsElements = $$("div[class*='ProjectsTreeItem__content']");

    private SelenideElement header = $(".MainPanel__router--gF > div");
    private SelenideElement searchProjectInput = $(By.id("search-projects"));

    public ProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    // ElementCollection -> List<ProjectElement>
    // UI elements -> List<Object>
    // ElementCollection -> List<BasePageElement>

    @Step("Открытие страницы проектов")
    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    @Step("Получение всех проектов")
    public List<ProjectElement> getProjects() {
        return generatePageElements(projectElements, ProjectElement::new);
    }

    @Step("Получение всех проектов и билдов")
    private List<ProjectAndBuildElement> getProjectsAndBuilds() {
        return generatePageElements(projectsAndBuildsElements, ProjectAndBuildElement::new);
    }


    @Step("Поиск и выбор проекта с названием {projectName}")
    public ProjectPage selectProject(String projectName) {
        searchProjectInput.val(projectName);
        getProjectsAndBuilds().stream()
                .filter(project -> project.getName().getText().contains(projectName))
                .findFirst()
                .ifPresent(project -> project.getName().click());

        return page(ProjectPage.class);
    }


}
