package com.example.teamcity.ui;

import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

public class CreateProjectTest extends BaseUiTest {

    @Test(description = "User should be able to create project", groups = {"Regression"})
    public void userCreatesProject() {
        //подготовка окружения
        step("Login as User");


        //Взаимодействие с UI
        step("Open 'Create Project Page'");
        step("Sent all project parameters (project url)");
        step(("Click Proceed"));
        step("Fix Project name and Build Type name values");
        step(("Click Proceed"));

        // проверка состояния API
        //корректность отправки данных с UI на API
        step("Check that all entities was created with correct Data on API level");

        //проверка состояния UI
        //корректность считывания данных и отображение данных на UI)
        step("Check that project is visible on project page");

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
