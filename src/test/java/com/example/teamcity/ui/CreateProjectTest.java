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
    private static final String GIT_URL = "https://github.com/Starford-newchapter/AVK_Double_2.git";


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

    @Test(description = "User should not be able to craete project without name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        // подготовка окружения
        step("Login as user");
        step("Check number of projects");

        // взаимодействие с UI
        step("Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL)");
        step("Click `Proceed`");
        step("Set Project Name");
        step("Click `Proceed`");

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        step("Check that number of projects did not change");

        // проверка состояния UI
        // (корректность считывания данных и отображение данных на UI)
        step("Check that error appears `Project name must not be empty`");
    }
}
