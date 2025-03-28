package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.build.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

public class CreateProjectTest extends BaseUiTest {

    @Test(description = "User should be able to create project", groups = {"Regression"})
    public void userCreatesProject() {
        //подготовка окружения
        loginAs(testData.getUser());

        //Взаимодействие с UI
        CreateProjectPage.open("_Root")
                .createForm(GIT_URL)
                .setUpProject(testData.getProject().getName(), testData.getBuildType().getName());

        // проверка состояния API
        //корректность отправки данных с UI на API
        var createdProject = superUserCheckedRequest.<Project>getRequest(Endpoint.PROJECTS).read("name:" + testData.getProject().getName());
        softAssert.assertNotNull(createdProject);

        //проверка состояния UI
        //корректность считывания данных и отображение данных на UI)
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var foundProjects = ProjectsPage.open()
                .getProjects().stream()
                .anyMatch(project -> project.getName().text().equals(testData.getProject().getName()));

        softAssert.assertTrue(foundProjects);

    }
}
